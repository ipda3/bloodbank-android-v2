package com.reda.yehia.bloodbankv2.view.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.Client;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.rw.keyboardlistener.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.userData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.login_fragment_til_phone)
    TextInputLayout loginFragmentTilPhone;
    @BindView(R.id.login_fragment_til_password)
    TextInputLayout loginFragmentTilPassword;
    @BindView(R.id.login_fragment_cb_remember)
    CheckBox loginFragmentCbRemember;
    @BindView(R.id.login_fragment_tv_register)
    TextView loginFragmentTvRegister;
    Unbinder unbinder;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        StatusBarUtil.setTranslucent(getActivity());

        addKeyboardToggleListener();

        return view;
    }

    private void addKeyboardToggleListener() {
        KeyboardUtils.addKeyboardToggleListener(getActivity(), new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                try {
                    if (!isVisible) {
                        loginFragmentTvRegister.setVisibility(View.VISIBLE);
                    } else {
                        loginFragmentTvRegister.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_fragment_tv_forget_password, R.id.login_fragment_btn_login, R.id.login_fragment_tv_register, R.id.login_fragment_rl_sub_view})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.login_fragment_tv_forget_password:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, new ForgetPasswordFragment());
                break;
            case R.id.login_fragment_btn_login:
                onValidData();
                break;
            case R.id.login_fragment_tv_register:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, new RegistersAndEditProfileFragment());
                break;
            case R.id.login_fragment_rl_sub_view:
                break;
        }
    }

    private void onValidData() {

        onCall();
    }

    private void onCall() {
        String phone = loginFragmentTilPhone.getEditText().getText().toString();
        String password = loginFragmentTilPassword.getEditText().getText().toString();

        boolean remember = loginFragmentCbRemember.isChecked();

        Call<Client> clientCall = getClient().onLogin(phone, password);


        userData(getActivity(), clientCall, password, remember, true);
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
