package com.reda.yehia.bloodbankv2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationData;
import com.reda.yehia.bloodbankv2.view.activity.BaseActivity;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation.DonationDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.onPermission;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<DonationData> donationDataList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ClientData clientData;
    private String lang;

    public DonationAdapter(Activity activity, List<DonationData> restaurantDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.donationDataList = restaurantDataList;
        viewBinderHelper.setOpenOnlyOne(true);
        clientData = loadUserData(activity);
        lang = "eg";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setSwipe(holder, position);
    }

    @SuppressLint("SetTextI18n")
    private void setData(ViewHolder holder, int position) {

        holder.position = position;
        holder.donationAdapterTvName.setText(activity.getString(R.string.patient_name) + " : " +
                donationDataList.get(position).getClient().getName());

        holder.donationAdapterTvAddress.setText(activity.getString(R.string.hospital) + " : " +
                donationDataList.get(position).getHospitalAddress());

        holder.donationAdapterTvCity.setText(activity.getString(R.string.city) + " : " +
                donationDataList.get(position).getCity().getName());

        holder.donationAdapterTvBloodType.setText(donationDataList.get(position).getBloodType().getName());

    }

    private void setSwipe(final ViewHolder holder, final int position) {
        holder.donationAdapterPsIvDonationSwipeLayout.computeScroll();
        if (lang.equals("ar")) {
            holder.donationAdapterPsIvDonationSwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
        } else {
            holder.donationAdapterPsIvDonationSwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
        }

        viewBinderHelper.bind(holder.donationAdapterPsIvDonationSwipeLayout, String.valueOf(donationDataList.get(position).getId()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinderHelper.openLayout(String.valueOf(donationDataList.get(position).getId()));
                holder.donationAdapterPsIvDonationSwipeLayout.computeScroll();
            }
        });

    }

    @Override
    public int getItemCount() {
        return donationDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.donation_adapter_tv_name)
        TextView donationAdapterTvName;
        @BindView(R.id.donation_adapter_tv_address)
        TextView donationAdapterTvAddress;
        @BindView(R.id.donation_adapter_tv_city)
        TextView donationAdapterTvCity;
        @BindView(R.id.donation_adapter_tv_blood_type)
        TextView donationAdapterTvBloodType;
        @BindView(R.id.donation_adapter_ps_iv_donation_swipe_layout)
        SwipeRevealLayout donationAdapterPsIvDonationSwipeLayout;

        private View view;
        private int position;

        private ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.donation_adapter_tv_info, R.id.donation_adapter_tv_call})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.donation_adapter_tv_info:

                    DonationDetailsFragment donationDetailsFragment = new DonationDetailsFragment();
                    donationDetailsFragment.donationId = donationDataList.get(position).getId();
                    replaceFragment(activity.getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame, donationDetailsFragment);

                    break;
                case R.id.donation_adapter_tv_call:
                    onPermission(activity);

                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", donationDataList.get(position).getPhone(), null)));
                    break;
            }
        }
    }
}
