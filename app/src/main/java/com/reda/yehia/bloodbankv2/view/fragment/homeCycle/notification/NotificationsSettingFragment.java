package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.notification;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.NotificationSettingAdapter;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponse;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponseData;
import com.reda.yehia.bloodbankv2.data.model.notificationSettings.NotificationSettings;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.utils.network.InternetState;
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
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.dismissProgressDialog;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.setInitRecyclerViewAsGridLayoutManager;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.showProgressDialog;
import static com.reda.yehia.mirtoast.ToastCreator.onCreateErrorToast;
import static com.reda.yehia.mirtoast.ToastCreator.onCreateSuccessToast;

public class NotificationsSettingFragment extends BaseFragment {

    @BindView(R.id.notification_settings_fragment_rv_blood_types)
    RecyclerView notificationSettingsFragmentRvBloodTypes;
    @BindView(R.id.notification_settings_fragment_rel_bloods_gone)
    RelativeLayout notificationSettingsFragmentRelBloodsGone;
    @BindView(R.id.notification_settings_fragment_iv)
    ImageView notificationSettingsFragmentIv;
    @BindView(R.id.notification_settings_fragment_rv_governorates)
    RecyclerView notificationSettingsFragmentRvGovernorates;
    @BindView(R.id.notification_settings_fragment_rel_governorates_gone)
    RelativeLayout notificationSettingsFragmentRelGovernoratesGone;
    @BindView(R.id.notification_settings_fragment_iv2)
    ImageView notificationSettingsFragmentIv2;

    Unbinder unbinder;
    private GridLayoutManager gridLayoutManager;
    private NotificationSettingAdapter bloodsAdapter, GovernAdapter;

    private List<GeneralResponseData> governoratesList = new ArrayList<>();
    private List<GeneralResponseData> bloodsList = new ArrayList<>();
    private List<String> oldBloodTypes = new ArrayList<>();
    private List<String> oldGovernorates = new ArrayList<>();
    private ClientData clientData;

    public NotificationsSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientData = loadUserData(getActivity());
        setUpActivity();

        homeCycleActivity.setNavigation(R.id.home_cycle_activity_rb_home);
        homeCycleActivity.setToolBar(View.VISIBLE, getString(R.string.notifications_settings), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });

        onCall(true);

        return view;
    }

    private void onCall(final boolean state) {

        if (InternetState.isConnected(getActivity())) {
            showProgressDialog(getActivity(), getString(R.string.wait));

            Call<NotificationSettings> call;

            if (state) {
                call = getClient().getNotificationSettings(clientData.getApiToken());
            } else {
                call = getClient().setNotificationSettings(clientData.getApiToken(), GovernAdapter.ids, bloodsAdapter.ids);
            }

            call.enqueue(new Callback<NotificationSettings>() {
                @Override
                public void onResponse(Call<NotificationSettings> call, Response<NotificationSettings> response) {
                    try {

                        dismissProgressDialog();
                        if (state) {

                            if (response.body().getStatus() == 1) {

                                oldBloodTypes = response.body().getData().getBloodTypes();
                                oldGovernorates = response.body().getData().getGovernorates();
                                getBloodTypes();
                                getGovernorates();

                            } else {
                                onCreateErrorToast(getActivity(), response.body().getMsg());
                            }

                        } else {
                            onCreateSuccessToast(getActivity(), response.body().getMsg());
                        }

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<NotificationSettings> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });

        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }

    }

    private void getBloodTypes() {

        setInitRecyclerViewAsGridLayoutManager(getActivity(), notificationSettingsFragmentRvBloodTypes, gridLayoutManager, 3);

        if (InternetState.isConnected(getActivity())) {
//            showProgressDialog(getActivity(), getString(R.string.wait));
            Call<GeneralResponse> call;

            call = getClient().getBloodTypes();
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    try {
                        dismissProgressDialog();
                        if (response.body().getStatus() == 1) {
                            if (bloodsList.size() == 0) {
                                visible(notificationSettingsFragmentRelBloodsGone, notificationSettingsFragmentIv);
                                bloodsList = new ArrayList<>();
                                bloodsList.addAll(response.body().getData());
                                bloodsAdapter = new NotificationSettingAdapter(getActivity(), getActivity(), bloodsList, oldBloodTypes);
                                notificationSettingsFragmentRvBloodTypes.setAdapter(bloodsAdapter);
                            }
                        } else {
                            dismissProgressDialog();
                            onCreateErrorToast(getActivity(), response.body().getMsg());

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });
        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }

    }

    private void getGovernorates() {

        setInitRecyclerViewAsGridLayoutManager(getActivity(), notificationSettingsFragmentRvGovernorates, gridLayoutManager, 3);

        if (InternetState.isConnected(getActivity())) {
//            showProgressDialog(getActivity(), getString(R.string.wait));
            Call<GeneralResponse> call;

            call = getClient().getGovernorates();
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    try {
                        dismissProgressDialog();
                        if (response.body().getStatus() == 1) {
                            if (governoratesList.size() == 0) {
                                visible(notificationSettingsFragmentRelGovernoratesGone, notificationSettingsFragmentIv2);
                                bloodsList = new ArrayList<>();
                                governoratesList.addAll(response.body().getData());
                                GovernAdapter = new NotificationSettingAdapter(getActivity(), getActivity(), governoratesList, oldGovernorates);
                                notificationSettingsFragmentRvGovernorates.setAdapter(GovernAdapter);
                            }
                        } else {
                            dismissProgressDialog();
                            onCreateErrorToast(getActivity(), response.body().getMsg());

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });
        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.notification_settings_fragment_iv, R.id.notification_settings_fragment_iv2, R.id.notification_settings_fragment_btn_save, R.id.notification_settings_fragment_rel_sub_view})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), getView());
        switch (view.getId()) {
            case R.id.notification_settings_fragment_iv2:
                visible(notificationSettingsFragmentRelGovernoratesGone, notificationSettingsFragmentIv2);
                break;
            case R.id.notification_settings_fragment_iv:
                visible(notificationSettingsFragmentRelBloodsGone, notificationSettingsFragmentIv);
                break;
            case R.id.notification_settings_fragment_btn_save:
                onCall(false);

                break;
            case R.id.notification_settings_fragment_rel_sub_view:

                break;
        }
    }

    private void visible(View view, ImageView imageView) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_minus_solid);

        } else {
            imageView.setImageResource(R.drawable.ic_plus_solid);
            view.setVisibility(View.GONE);
        }
    }

}
