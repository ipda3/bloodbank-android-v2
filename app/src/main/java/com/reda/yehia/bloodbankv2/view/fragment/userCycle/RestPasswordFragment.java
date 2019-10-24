package com.reda.yehia.bloodbankv2.view.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponse;
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
import static com.reda.yehia.mirvalidation.Validation.validationConfirmPassword;
import static com.reda.yehia.mirvalidation.Validation.validationLength;

public class RestPasswordFragment extends BaseFragment {

    public String phone;
    @BindView(R.id.reset_password_fragment_til_pin_code)
    TextInputLayout resetPasswordFragmentTilPinCode;
    @BindView(R.id.reset_password_fragment_til_password)
    TextInputLayout resetPasswordFragmentTilPassword;
    @BindView(R.id.reset_password_fragment_til_password_confirmation)
    TextInputLayout resetPasswordFragmentTilPasswordConfirmation;
    Unbinder unbinder;

    public RestPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rest_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.reset_password_fragment_btn_change, R.id.reset_password_fragment_rel_sub_view})
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(), getView());
        switch (view.getId()) {
            case R.id.reset_password_fragment_btn_change:
                onCall();
                break;
            case R.id.reset_password_fragment_rel_sub_view:
                break;
        }
    }

    private void onCall() {
        String pin_code = resetPasswordFragmentTilPinCode.getEditText().getText().toString();
        String password = resetPasswordFragmentTilPassword.getEditText().getText().toString();
        String password_confirmation = resetPasswordFragmentTilPasswordConfirmation.getEditText().getText().toString();


        if (pin_code.isEmpty() && password.isEmpty() && password_confirmation.isEmpty()) {

            return;
        }
        if (pin_code.isEmpty()) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.enter_pin_code));

            return;
        }
        if (password.isEmpty()) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.enter_password));

            return;
        }
        if (!validationLength(getActivity(), password, getString(R.string.weak_password), 3)) {

            return;
        }
        if (password_confirmation.isEmpty()) {
            onCreateErrorToast(getActivity(), getString(R.string.enter_password_confirm));

            return;
        }


        if (!validationConfirmPassword(getActivity(), password, password_confirmation)) {

            return;
        }

        showProgressDialog(getActivity(), getString(R.string.wait));
        Call<GeneralResponse> call = getClient().newPassword(pin_code, password, password_confirmation, phone);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        LoginFragment loginFragment = new LoginFragment();
                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, loginFragment);
                        ToastCreator.onCreateSuccessToast(getActivity(), response.body().getMsg());

                    } else {
                        onCreateErrorToast(getActivity(), response.body().getMsg());
                    }

                    dismissProgressDialog();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                dismissProgressDialog();
                onCreateErrorToast(getActivity(), getString(R.string.error));
            }
        });

    }


}
