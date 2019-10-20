package com.reda.yehia.bloodbankv2.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.notification.getAllNotification.NotificationData;
import com.reda.yehia.bloodbankv2.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.reda.yehia.bloodbankv2.utils.GetTimeAgo.getDate;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private BaseActivity activity;
    private List<NotificationData> notificationDataList = new ArrayList<>();
    private String lang = "en";

    public NotificationAdapter(Activity activity, List<NotificationData> notificationDataList) {
        this.activity = (BaseActivity) activity;
        this.notificationDataList = notificationDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_notification,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        if (notificationDataList.get(position).getPivot().getIsRead().equals("0")) {
            holder.itemNotificationIvIcon.setImageResource(R.drawable.ic_notifications);
        } else {
            holder.itemNotificationIvIcon.setImageResource(R.drawable.ic_notifications_none);
        }

        holder.itemNotificationTvTitle.setText(notificationDataList.get(position).getTitle());

        holder.itemNotificationTvTime.setText(getDate(notificationDataList.get(position).getUpdatedAt(), new Locale(lang)));

    }

    private void setAction(ViewHolder holder, int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_notification_iv_icon)
        ImageView itemNotificationIvIcon;
        @BindView(R.id.item_notification_tv_title)
        TextView itemNotificationTvTitle;
        @BindView(R.id.item_notification_tv_time)
        TextView itemNotificationTvTime;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
