package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.staticScreen;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.contactUs.ContactUs;
import com.reda.yehia.bloodbankv2.data.model.setting.Setting;
import com.reda.yehia.bloodbankv2.utils.network.InternetState;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.disappearKeypad;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.dismissProgressDialog;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.showProgressDialog;
import static com.reda.yehia.mirtoast.ToastCreator.onCreateErrorToast;
import static com.reda.yehia.mirtoast.ToastCreator.onCreateSuccessToast;

public class ContactUsFragment extends BaseFragment {


    @BindView(R.id.contact_us_fragment_tv_phone_number)
    TextView contactUsFragmentTvPhoneNumber;
    @BindView(R.id.contact_us_fragment_tv_email_address)
    TextView contactUsFragmentTvEmailAddress;
    @BindView(R.id.contact_us_fragment_til_message_title)
    TextInputLayout contactUsFragmentTilMessageTitle;
    @BindView(R.id.contact_us_fragment_til_message_content)
    TextInputLayout contactUsFragmentTilMessageContent;
    Unbinder unbinder;
    private ClientData clientData;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientData = loadUserData(getActivity());
        settings();
        setUpActivity();

        homeCycleActivity.setNavigation(View.GONE, R.id.home_cycle_activity_rb_home);
        homeCycleActivity.setToolBar(View.VISIBLE,
                getString(R.string.contact_us), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBack();
                    }
                });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.contact_us_fragment_rel_instagram, R.id.contact_us_fragment_rel_facebook, R.id.contact_us_fragment_rel_youtube, R.id.contact_us_fragment_rel_twitter, R.id.contact_us_fragment_btn_send, R.id.contact_us_fragment_rel_sub_view})
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(), getView());
        switch (view.getId()) {
            case R.id.contact_us_fragment_rel_instagram:
                break;
            case R.id.contact_us_fragment_rel_facebook:
                break;
            case R.id.contact_us_fragment_rel_youtube:
                break;
            case R.id.contact_us_fragment_rel_twitter:
                break;
            case R.id.contact_us_fragment_btn_send:
                onContactUs();
                break;
            case R.id.contact_us_fragment_rel_sub_view:
                break;
        }
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

                            contactUsFragmentTvPhoneNumber.setText(response.body().getData().getPhone());
                            contactUsFragmentTvEmailAddress.setText(response.body().getData().getEmail());
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

    private void onContactUs() {
        String title = contactUsFragmentTilMessageTitle.getEditText().getText().toString().trim();
        String message = contactUsFragmentTilMessageContent.getEditText().getText().toString().trim();

        if (message.isEmpty() && title.isEmpty()) {
            return;
        }
        if (title.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_message_title));

            return;
        }

        if (message.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_message_content));

            return;
        }
        if (InternetState.isConnected(getActivity())) {
            showProgressDialog(getActivity(), getString(R.string.wait));

            Call<ContactUs> call;


            call = getClient().contactUs(title, message, clientData.getApiToken());


            call.enqueue(new Callback<ContactUs>() {
                @Override
                public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {

                    dismissProgressDialog();
                    try {
                        if (response.body().getStatus() == 1) {

                            onCreateSuccessToast(getActivity(), response.body().getMsg());


                        } else {
                            onCreateErrorToast(getActivity(), response.body().getMsg());
                        }


                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<ContactUs> call, Throwable t) {
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
