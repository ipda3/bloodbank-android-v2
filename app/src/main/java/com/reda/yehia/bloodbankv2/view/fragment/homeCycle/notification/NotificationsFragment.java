package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.notification;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.NotificationAdapter;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.notification.getAllNotification.Notification;
import com.reda.yehia.bloodbankv2.data.model.notification.getAllNotification.NotificationData;
import com.reda.yehia.bloodbankv2.utils.OnEndLess;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;
import static com.reda.yehia.bloodbankv2.utils.RecycleTool.setRecycleTool;
import static com.reda.yehia.bloodbankv2.utils.network.InternetState.isConnected;

public class NotificationsFragment extends BaseFragment {


    @BindView(R.id.notifications_fragment_rv_notification_list)
    RecyclerView notificationsFragmentRvNotificationList;
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
    @BindView(R.id.donations_list_Fragment_sr_refresh_donations)
    SwipeRefreshLayout donationsListFragmentSrRefreshDonations;
    @BindView(R.id.notifications_fragment_s_fl_shimmer_donations)
    ShimmerFrameLayout notificationsFragmentSFlShimmerDonations;
    Unbinder unbinder;

    private LinearLayoutManager linearLayoutManager;
    private List<NotificationData> notificationsDataList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private int maxPage = 0;
    private OnEndLess onEndLess;
    private ClientData clientData;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientData = loadUserData(getActivity());

        init();

        setUpActivity();

        homeCycleActivity.setNavigation(View.VISIBLE, R.id.home_cycle_activity_rb_home);
        homeCycleActivity.setToolBar(View.VISIBLE, getString(R.string.notification), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });

        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationsFragmentRvNotificationList.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);

                        getNotification(current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }

            }
        };
        notificationsFragmentRvNotificationList.addOnScrollListener(onEndLess);

        notificationAdapter = new NotificationAdapter(getActivity(), notificationsDataList);
        notificationsFragmentRvNotificationList.setAdapter(notificationAdapter);

        getNotification(1);

        donationsListFragmentSrRefreshDonations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reInit();
                getNotification(1);
            }
        });
    }

    private void getNotification(int page) {

        if (page == 1) {
            errorSubView.setVisibility(View.GONE);
            notificationsFragmentSFlShimmerDonations.startShimmer();
            notificationsFragmentSFlShimmerDonations.setVisibility(View.VISIBLE);
        }

        if (isConnected(getActivity())) {

            getClient().getNotifications(clientData.getApiToken(), page).enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    try {
                        notificationsFragmentSFlShimmerDonations.stopShimmer();
                        notificationsFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);

                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();

                            if (response.body().getData().getTotal() != 0) {
                                notificationsDataList.addAll(response.body().getData().getData());
                                notificationAdapter.notifyDataSetChanged();
                            } else {
                                setError(getString(R.string.no_notification));
                            }

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    try {
                        notificationsFragmentSFlShimmerDonations.stopShimmer();
                        notificationsFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);
                        setError(getString(R.string.error_list));
                    } catch (Exception e) {

                    }
                }
            });

        } else {
            try {
                notificationsFragmentSFlShimmerDonations.stopShimmer();
                notificationsFragmentSFlShimmerDonations.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                donationsListFragmentSrRefreshDonations.setRefreshing(false);
                setError(getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
    }

    private void setError(String errorTitleTxt) {
        if (notificationsDataList.size() == 0) {
            View.OnClickListener action = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reInit();
                    getNotification(1);

                }
            };
            setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.ic_no_notification
                    , errorTitleTxt, getString(R.string.reload), action);
        }
    }

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        notificationsDataList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getActivity(), notificationsDataList);
        notificationsFragmentRvNotificationList.setAdapter(notificationAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame
                , homeCycleActivity.homeContainerFragment);
    }
}
