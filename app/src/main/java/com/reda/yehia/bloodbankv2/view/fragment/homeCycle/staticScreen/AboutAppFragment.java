package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.staticScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.setting.Setting;
import com.reda.yehia.bloodbankv2.utils.network.InternetState;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.dismissProgressDialog;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.htmlReader;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.showProgressDialog;
import static com.reda.yehia.mirtoast.ToastCreator.onCreateErrorToast;

public class AboutAppFragment extends BaseFragment {


    @BindView(R.id.about_app_fragment_iv_about_app)
    TextView aboutAppFragmentIvAboutApp;
    Unbinder unbinder;
    private ClientData clientData;

    public AboutAppFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientData = loadUserData(getActivity());

        setUpActivity();


            homeCycleActivity.setNavigation(R.id.home_cycle_activity_rb_home);
            homeCycleActivity.setToolBar(View.VISIBLE, getString(R.string.about_app), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBack();
                }
            });
            settings();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void settings() {
        if (InternetState.isConnected(getActivity())) {
            showProgressDialog(getActivity(), getString(R.string.wait));
            Call<Setting> call;

            call = getClient().getSettings(clientData.getApiToken());
            call.enqueue(new Callback<Setting>() {
                @Override
                public void onResponse(Call<Setting> call, Response<Setting> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            htmlReader(aboutAppFragmentIvAboutApp, response.body().getData().getAboutApp());

                        } else {
                            onCreateErrorToast(getActivity(), response.body().getMsg());

                        }

                    } catch (Exception e) {

                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<Setting> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });


        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }
    }

}
