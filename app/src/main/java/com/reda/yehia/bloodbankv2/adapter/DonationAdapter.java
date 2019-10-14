package com.reda.yehia.bloodbankv2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.donationRequests.DonationData;
import com.reda.yehia.bloodbankv2.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class

DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<DonationData> donationDataList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public DonationAdapter(Activity activity, List<DonationData> restaurantDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.donationDataList = restaurantDataList;
        viewBinderHelper.setOpenOnlyOne(true);
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

    private void setSwipe(ViewHolder holder, int position) {
//        if (lang.equals("ar")) {
//            holder.donationAdapterPsIvDonationSwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
//        } else {
//            holder.donationAdapterPsIvDonationSwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
//        }

        viewBinderHelper.bind(holder.donationAdapterPsIvDonationSwipeLayout, String.valueOf(donationDataList.get(position).getId()));

//        if (!client.getId().equals(activityDataList.get(position).getClient().getId())) {
//            viewBinderHelper.lockSwipe(String.valueOf(activityDataList.get(position).getId()));
//        }
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
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.donation_adapter_tv_info, R.id.donation_adapter_tv_call})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.donation_adapter_tv_info:

                    break;
                case R.id.donation_adapter_tv_call:

                    break;
            }
        }
    }
}
