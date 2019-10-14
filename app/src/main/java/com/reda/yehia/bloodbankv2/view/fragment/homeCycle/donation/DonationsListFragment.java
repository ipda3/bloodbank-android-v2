package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.DonationAdapter;
import com.reda.yehia.bloodbankv2.adapter.SpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.donationRequests.DonationData;
import com.reda.yehia.bloodbankv2.data.model.donationRequests.DonationRequests;
import com.reda.yehia.bloodbankv2.utils.OnEndLess;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.getSpinnerData;

public class DonationsListFragment extends BaseFragment {

    @BindView(R.id.donations_list_Fragment_sp_blood_types)
    Spinner donationsListFragmentSpBloodTypes;
    @BindView(R.id.donations_list_Fragment_sp_government)
    Spinner donationsListFragmentSpGovernment;
    @BindView(R.id.donations_list_Fragment_rv_donations)
    RecyclerView donationsListFragmentRvDonations;
    Unbinder unbinder;

    private SpinnerAdapter bloodTypesAdapter, gaviermentAdapter;
    private LinearLayoutManager linearLayout;
    private List<DonationData> donationDataList = new ArrayList<>();
    private DonationAdapter donationAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;

    public DonationsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycle_layout, container, false);
        unbinder = ButterKnife.bind(this, view);


        bloodTypesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), donationsListFragmentSpBloodTypes, bloodTypesAdapter, getString(R.string.select_blood_type),
                getClient().getBloodTypes(), null, 0);

        gaviermentAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), donationsListFragmentSpGovernment, gaviermentAdapter, getString(R.string.select_government),
                getClient().getGovernorates(), null, 0);

        init();

        return view;
    }

    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        donationsListFragmentRvDonations.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (Filter) {
                            onFilter(current_page);
                        } else {
                            getDonations(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        donationsListFragmentRvDonations.addOnScrollListener(onEndLess);

        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        donationsListFragmentRvDonations.setAdapter(donationAdapter);

        getDonations(1);
    }

    private void getDonations(int page) {
        Call<DonationRequests> call = getClient().getDonationRequests("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page);

        startCall(call, page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.donations_list_Fragment_i_ben_filter)
    public void onViewClicked() {

        if (bloodTypesAdapter.selectedId == 0 && gaviermentAdapter.selectedId == 0 && Filter) {
            Filter = false;

            reInit();

            getDonations(1);
        } else {
            onFilter(1);
        }


    }

    private void onFilter(int page) {

        Filter = true;

        reInit();

        Call<DonationRequests> call = getClient().getDonationRequests(
                "W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page, bloodTypesAdapter.selectedId, gaviermentAdapter.selectedId);

        startCall(call, page);
    }

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        donationDataList = new ArrayList<>();
        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        donationsListFragmentRvDonations.setAdapter(donationAdapter);
    }

    private void startCall(Call<DonationRequests> call, int page) {
        call.enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        maxPage = response.body().getData().getLastPage();
                        donationDataList.addAll(response.body().getData().getData());
                        donationAdapter.notifyDataSetChanged();

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<DonationRequests> call, Throwable t) {

            }
        });
    }


}
