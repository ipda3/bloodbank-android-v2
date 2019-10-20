package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.DonationAdapter;
import com.reda.yehia.bloodbankv2.adapter.SpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationData;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationRequests;
import com.reda.yehia.bloodbankv2.utils.CustomViewPager;
import com.reda.yehia.bloodbankv2.utils.OnEndLess;
import com.reda.yehia.bloodbankv2.utils.OnSwipeTouchListener;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.HomeContainerFragment;

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
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.getSpinnerData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;
import static com.reda.yehia.bloodbankv2.utils.RecycleTool.setRecycleTool;
import static com.reda.yehia.bloodbankv2.utils.network.InternetState.isConnected;

public class DonationsListFragment extends BaseFragment {

    @BindView(R.id.donations_list_Fragment_sp_blood_types)
    Spinner donationsListFragmentSpBloodTypes;
    @BindView(R.id.donations_list_Fragment_sp_government)
    Spinner donationsListFragmentSpGovernment;
    @BindView(R.id.donations_list_Fragment_rv_donations)
    RecyclerView donationsListFragmentRvDonations;
    @BindView(R.id.donations_list_Fragment_s_fl_shimmer_donations)
    ShimmerFrameLayout donationsListFragmentSFlShimmerDonations;
    @BindView(R.id.donations_list_Fragment_sr_refresh_donations)
    SwipeRefreshLayout donationsListFragmentSrRefreshDonations;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_action)
    TextView errorAction;
    @BindView(R.id.error_sub_view)
    View errorSubView;
    @BindView(R.id.donations_list_Fragment_ll_sub_view)
    LinearLayout donationsListFragmentLlSubView;
    Unbinder unbinder;

    private SpinnerAdapter bloodTypesAdapter, gaviermentAdapter;
    private LinearLayoutManager linearLayout;
    public List<DonationData> donationDataList = new ArrayList<>();
    public DonationAdapter donationAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;
    private ClientData clientData;
    public CustomViewPager homeContainerFragmentVpViewPager;
    public HomeContainerFragment homeContainerFragment;
    private String lang = "en";


    public DonationsListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_list, container, false);
        unbinder = ButterKnife.bind(this, view);

//        homeContainerFragmentVpViewPager.setPagingEnabled(false);

        clientData = loadUserData(getActivity());

        bloodTypesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), donationsListFragmentSpBloodTypes, bloodTypesAdapter, getString(R.string.select_blood_type),
                getClient().getBloodTypes(), null, 0, false);

        gaviermentAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), donationsListFragmentSpGovernment, gaviermentAdapter, getString(R.string.select_government),
                getClient().getGovernorates(), null, 0, false);

        init();

        donationsListFragmentRvDonations.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (lang.equals("en")) {
                    homeContainerFragmentVpViewPager.setPagingEnabled(true);
                    homeContainerFragmentVpViewPager.setCurrentItem(0);
                }
            }

            public void onSwipeLeft() {
                if (lang.equals("ar")) {
                    homeContainerFragmentVpViewPager.setPagingEnabled(true);
                    homeContainerFragmentVpViewPager.setCurrentItem(0);
                }
            }

            public void onSwipeBottom() {
            }
        });

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
                        loadMore.setVisibility(View.VISIBLE);
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

        donationsListFragmentSrRefreshDonations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Filter) {
                    onFilter(1);
                } else {
                    getDonations(1);
                }

            }
        });
    }

    private void getDonations(int page) {
        Call<DonationRequests> call = getClient().getDonationRequests(clientData.getApiToken(), page);

        startCall(call, page);
    }

    @Override
    public void onDestroyView() {
        homeContainerFragmentVpViewPager.setHorizontalScrollBarEnabled(true);
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.donations_list_Fragment_rl_filter)
    public void onViewClicked() {

        if (bloodTypesAdapter.selectedId == 0 && gaviermentAdapter.selectedId == 0 && Filter) {
            Filter = false;

            getDonations(1);
        } else {
            onFilter(1);
        }


    }

    private void onFilter(int page) {

        Filter = true;

        Call<DonationRequests> call = getClient().getDonationRequests(
                clientData.getApiToken(), page, bloodTypesAdapter.selectedId, gaviermentAdapter.selectedId);

        startCall(call, page);
    }

    private void startCall(Call<DonationRequests> call, int page) {
        errorSubView.setVisibility(View.GONE);
        if (page == 1) {
            reInit();
            donationsListFragmentSFlShimmerDonations.startShimmer();
            donationsListFragmentSFlShimmerDonations.setVisibility(View.VISIBLE);
        }

        if (isConnected(getActivity())) {

            call.enqueue(new Callback<DonationRequests>() {
                @Override
                public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                    try {
                        donationsListFragmentSFlShimmerDonations.stopShimmer();
                        donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);

                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();

                            if (response.body().getData().getTotal() != 0) {
                                donationDataList.addAll(response.body().getData().getData());
                                donationAdapter.notifyDataSetChanged();
                            } else {
                                View.OnClickListener action = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        CreateDonationFragment createDonationFragment = new CreateDonationFragment();
                                        createDonationFragment.donationsListFragment = DonationsListFragment.this;
                                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, createDonationFragment);
                                    }
                                };
                                setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.ic_transfusion
                                        , getString(R.string.no_donations), getString(R.string.add_new_donation), action);
                            }


                        } else {
                            setError(getString(R.string.error_list));
                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<DonationRequests> call, Throwable t) {
                    try {
                        donationsListFragmentSFlShimmerDonations.stopShimmer();
                        donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);
                        setError(getString(R.string.error_list));
                    } catch (Exception e) {

                    }

                }
            });

        } else {
            try {
                donationsListFragmentSFlShimmerDonations.onDetachedFromWindow();
                donationsListFragmentSFlShimmerDonations.stopShimmer();
                donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                donationsListFragmentSrRefreshDonations.setRefreshing(false);
                setError(getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }

    }


    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        donationDataList = new ArrayList<>();
        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        donationsListFragmentRvDonations.setAdapter(donationAdapter);
    }

    private void setError(String errorTitleTxt) {
        View.OnClickListener action = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Filter) {
                    onFilter(1);
                } else {
                    getDonations(1);
                }

            }
        };
        setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.ic_transfusion
                , errorTitleTxt, getString(R.string.reload), action);
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

}
