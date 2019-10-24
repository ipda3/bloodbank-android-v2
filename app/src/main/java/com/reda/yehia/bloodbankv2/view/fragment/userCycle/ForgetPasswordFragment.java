package com.reda.yehia.bloodbankv2.view.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.api.ApiService;
import com.reda.yehia.bloodbankv2.data.model.resetpassword.ResetPassword;
import com.reda.yehia.bloodbankv2.utils.network.InternetState;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.mirtoast.ToastCreator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.disappearKeypad;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.dismissProgressDialog;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.showProgressDialog;
import static com.reda.yehia.mirtoast.ToastCreator.onCreateErrorToast;

public class ForgetPasswordFragment extends BaseFragment {


    @BindView(R.id.forget_password_fragment_til_phone)
    TextInputLayout forgetPasswordFragmentTilPhone;
    @BindView(R.id.forget_password_fragment_btn_send)
    Button forgetPasswordFragmentBtnLogin;
    Unbinder unbinder;
    private ApiService apiServices;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.forget_password_fragment_btn_send, R.id.forget_password_fragment_rel_sub_view})
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(), getView());
        switch (view.getId()) {
            case R.id.forget_password_fragment_btn_send:
                onCall();
                break;
            case R.id.forget_password_fragment_rel_sub_view:
                break;
        }
    }

    private void onCall() {
        final String phone = forgetPasswordFragmentTilPhone.getEditText().getText().toString();

        if (phone.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_phone_number));
            return;
        }

        if (phone.length() < 11) {
            onCreateErrorToast(getActivity(), getString(R.string.phone3));
            return;
        }
        if (InternetState.isConnected(getActivity())) {

        showProgressDialog(getActivity(), getString(R.string.wait));
        Call<ResetPassword> call = getClient().forgetPassword(phone);
        call.enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        RestPasswordFragment restPasswordFragment = new RestPasswordFragment();
                        restPasswordFragment.phone = phone;
                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, restPasswordFragment);
                        ToastCreator.onCreateSuccessToast(getActivity(), response.body().getMsg());

                    } else {
                        onCreateErrorToast(getActivity(), response.body().getMsg());
                    }

                    dismissProgressDialog();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
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
