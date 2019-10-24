package com.reda.yehia.bloodbankv2.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.notification.getNotificationCount.NotificationCount;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.HomeContainerFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.MoreFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation.DonationsListFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.notification.NotificationsFragment;
import com.reda.yehia.bloodbankv2.view.fragment.userCycle.RegistersAndEditProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class HomeCycleActivity extends BaseActivity {

    @BindView(R.id.toolbar_back)
    ImageButton toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_sub_view)
    RelativeLayout toolbarSubView;

    @BindView(R.id.home_cycle_activity_iv_home)
    ImageView homeCycleActivityIvHome;
    @BindView(R.id.home_cycle_activity_iv_profile)
    ImageView homeCycleActivityIvProfile;
    @BindView(R.id.home_cycle_activity_iv_notification)
    ImageView homeCycleActivityIvNotification;
    @BindView(R.id.home_cycle_activity_iv_more)
    ImageView homeCycleActivityIvMore;

    @BindView(R.id.home_cycle_activity_rb_home)
    RadioButton homeCycleActivityRbHome;
    @BindView(R.id.home_cycle_activity_rb_profile)
    RadioButton homeCycleActivityRbProfile;
    @BindView(R.id.home_cycle_activity_rb_notification)
    RadioButton homeCycleActivityRbNotification;
    @BindView(R.id.home_cycle_activity_rb_more)
    RadioButton homeCycleActivityRbMore;
    @BindView(R.id.home_cycle_activity_rg_navigation)
    RadioGroup homeCycleActivityRgNavigation;
    @BindView(R.id.home_cycle_activity_tv_notification_count)
    TextView homeCycleActivityTvNotificationCount;

    public HomeContainerFragment homeContainerFragment;
    public RegistersAndEditProfileFragment profileFragment;
    public NotificationsFragment notificationsFragment;
    public MoreFragment moreFragment;
    public Integer notificationsCount;

    private List<ImageView> imageViews = new ArrayList<>();
    public ClientData clientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);

        imageViews.add(homeCycleActivityIvHome);
        imageViews.add(homeCycleActivityIvProfile);
        imageViews.add(homeCycleActivityIvNotification);
        imageViews.add(homeCycleActivityIvMore);

        homeContainerFragment = new HomeContainerFragment();
        profileFragment = new RegistersAndEditProfileFragment();
        profileFragment.PROFILE = true;
        notificationsFragment = new NotificationsFragment();
        moreFragment = new MoreFragment();

        replaceFragment(getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame, homeContainerFragment);

        OnCheckedChangeListener(homeCycleActivityRbHome, homeContainerFragment, 0);
        OnCheckedChangeListener(homeCycleActivityRbProfile, profileFragment, 1);
        OnCheckedChangeListener(homeCycleActivityRbNotification, notificationsFragment, 2);
        OnCheckedChangeListener(homeCycleActivityRbMore, moreFragment, 3);

        clientData = loadUserData(this);

        getNotificationCount();

        setNavigation(View.VISIBLE, R.id.home_cycle_activity_rb_home);

    }

    private void getNotificationCount() {
        getClient().getNotificationsCounter(clientData.getApiToken()).enqueue(new Callback<NotificationCount>() {
            @Override
            public void onResponse(Call<NotificationCount> call, Response<NotificationCount> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        notificationsCount = response.body().getData().getNotificationsCount();
                        setNotificationsCounter(notificationsCount);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NotificationCount> call, Throwable t) {

            }
        });
    }

    private void setNotificationsCounter(Integer notificationsCount) {
        if (notificationsCount == 0) {
            homeCycleActivityTvNotificationCount.setVisibility(View.GONE);
        } else if (notificationsCount <= 0) {
            homeCycleActivityTvNotificationCount.setVisibility(View.VISIBLE);
            homeCycleActivityTvNotificationCount.setText(notificationsCount);
        } else {
            homeCycleActivityTvNotificationCount.setVisibility(View.VISIBLE);
            homeCycleActivityTvNotificationCount.setText(R.string.max_notification);
        }
    }

    public void setToolBar(int visibility, String title, View.OnClickListener backActionBtn) {
        toolbarSubView.setVisibility(visibility);

        if (visibility == View.VISIBLE) {
            toolbarTitle.setText(title);
            toolbarBack.setOnClickListener(backActionBtn);
        }

    }

    public void setNavigation(int visibility, int id) {

        homeCycleActivityRgNavigation.setVisibility(visibility);

        if (id!= 0) {
            homeCycleActivityRgNavigation.check(id);

            switch (id) {
                case R.id.home_cycle_activity_rb_home:
                    setSelection(0);
                    break;
                case R.id.home_cycle_activity_rb_profile:
                    setSelection(1);
                    break;
                case R.id.home_cycle_activity_rb_notification:
                    setSelection(2);
                    break;
                case R.id.home_cycle_activity_rb_more:
                    setSelection(3);
                    break;
            }
        }


    }

    private void setSelection(int i) {
        for (int j = 0; j < imageViews.size(); j++) {
            if (i == j) {
                imageViews.get(i).setVisibility(View.VISIBLE);
            } else {

                imageViews.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void OnCheckedChangeListener(final RadioButton radioButton, final Fragment fragment, final int i) {
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButton.isChecked()) {
                    replaceFragment(getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame, fragment);
                    setSelection(i);
                }
            }
        });
    }
}
