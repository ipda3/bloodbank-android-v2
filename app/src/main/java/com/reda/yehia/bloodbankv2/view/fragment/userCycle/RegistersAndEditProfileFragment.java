package com.reda.yehia.bloodbankv2.view.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.SpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.DateTxt;
import com.reda.yehia.bloodbankv2.data.model.client.Client;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.mirtoast.ToastCreator;
import com.reda.yehia.mirvalidation.Validation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.LoadData;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.getSpinnerData;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.userData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.showCalender;
import static com.reda.yehia.mirvalidation.Validation.validationConfirmPassword;
import static com.reda.yehia.mirvalidation.Validation.validationEmail;
import static com.reda.yehia.mirvalidation.Validation.validationLength;
import static com.reda.yehia.mirvalidation.Validation.validationPassword;
import static com.reda.yehia.mirvalidation.Validation.validationPhone;

public class RegistersAndEditProfileFragment extends BaseFragment {

    @BindView(R.id.registers_and_edit_profile_fragment_tv_title)
    TextView registersAndEditProfileFragmentTvTitle;
    @BindView(R.id.registers_and_edit_profile_fragment_til_user_name)
    TextInputLayout registersAndEditProfileFragmentTilUserName;
    @BindView(R.id.registers_and_edit_profile_fragment_til_email)
    TextInputLayout registersAndEditProfileFragmentTilEmail;
    @BindView(R.id.registers_and_edit_profile_fragment_til_phone)
    TextInputLayout registersAndEditProfileFragmentTilPhone;
    @BindView(R.id.registers_and_edit_profile_fragment_til_password)
    TextInputLayout registersAndEditProfileFragmentTilPassword;
    @BindView(R.id.registers_and_edit_profile_fragment_til_confirm_password)
    TextInputLayout registersAndEditProfileFragmentTilConfirmPassword;
    @BindView(R.id.registers_and_edit_profile_fragment_tv_brd)
    TextView registersAndEditProfileFragmentTvBrd;
    @BindView(R.id.registers_and_edit_profile_fragment_tv_last_donation_date)
    TextView registersAndEditProfileFragmentTvLastDonationDate;
    @BindView(R.id.registers_and_edit_profile_fragment_sp_blood_types)
    Spinner registersAndEditProfileFragmentSpBloodTypes;
    @BindView(R.id.registers_and_edit_profile_fragment_sp_governments)
    Spinner registersAndEditProfileFragmentSpGovernments;
    @BindView(R.id.registers_and_edit_profile_fragment_sp_city)
    Spinner registersAndEditProfileFragmentSpCity;
    @BindView(R.id.registers_and_edit_profile_fragment_ll_container_city)
    LinearLayout registersAndEditProfileFragmentLlContainerCity;
    @BindView(R.id.registers_and_edit_profile_fragment_btn_start_call)
    Button registersAndEditProfileFragmentBtnStartCall;
    Unbinder unbinder;

    private SpinnerAdapter bloodTypesAdapter, governmentsAdapter, citiesAdapter;
    private DateTxt birthdayDate, lastDonationDate;
    private int bloodTypesSelectedId = 0, governmentSelectedId = 0, citiesSelectedId = 0;

    private ClientData clientData;
    public boolean PROFILE = false;

    public RegistersAndEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registers_and_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        StatusBarUtil.setTranslucent(getActivity());

        if (PROFILE) {
            clientData = loadUserData(getActivity());
            setUserData();
        }

        bloodTypesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), registersAndEditProfileFragmentSpBloodTypes, bloodTypesAdapter, getString(R.string.select_blood_type),
                getClient().getBloodTypes(), null, bloodTypesSelectedId);

        governmentsAdapter = new SpinnerAdapter(getActivity());
        citiesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), registersAndEditProfileFragmentSpGovernments, governmentsAdapter, getString(R.string.select_blood_type),
                getClient().getGovernorates(), governmentSelectedId, registersAndEditProfileFragmentSpCity, citiesAdapter, getString(R.string.select_blood_type),
                getClient().getCities(governmentsAdapter.selectedId), registersAndEditProfileFragmentLlContainerCity, citiesSelectedId);

        setDates();

        return view;
    }

    private void setUserData() {
        bloodTypesSelectedId = clientData.getClient().getBloodType().getId();
        governmentSelectedId = clientData.getClient().getCity().getGovernorate().getId();
        citiesSelectedId = clientData.getClient().getCity().getId();

        registersAndEditProfileFragmentTilUserName.getEditText().setText(clientData.getClient().getName());
        registersAndEditProfileFragmentTilEmail.getEditText().setText(clientData.getClient().getEmail());
        registersAndEditProfileFragmentTilPhone.getEditText().setText(clientData.getClient().getPhone());
        registersAndEditProfileFragmentTilPassword.getEditText().setText(LoadData(getActivity(), USER_PASSWORD));
        registersAndEditProfileFragmentTilConfirmPassword.getEditText().setText(LoadData(getActivity(), USER_PASSWORD));

        registersAndEditProfileFragmentTvBrd.setText(clientData.getClient().getBirthDate());
        registersAndEditProfileFragmentTvLastDonationDate.setText(clientData.getClient().getDonationLastDate());

        registersAndEditProfileFragmentTvTitle.setText(getString(R.string.profile));
        registersAndEditProfileFragmentBtnStartCall.setText(getString(R.string.save));

    }

    private void setDates() {
        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));

        lastDonationDate = new DateTxt(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        birthdayDate = new DateTxt("01", "01", "1990", "01-01-1990");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.registers_and_edit_profile_fragment_tv_brd, R.id.registers_and_edit_profile_fragment_tv_last_donation_date, R.id.registers_and_edit_profile_fragment_btn_start_call, R.id.registers_and_edit_profile_fragment_ll_sub_view})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.registers_and_edit_profile_fragment_tv_brd:
                showCalender(getActivity(), getString(R.string.select_date), registersAndEditProfileFragmentTvBrd, birthdayDate);
                break;
            case R.id.registers_and_edit_profile_fragment_tv_last_donation_date:
                showCalender(getActivity(), getString(R.string.select_date), registersAndEditProfileFragmentTvLastDonationDate, lastDonationDate);
                break;
            case R.id.registers_and_edit_profile_fragment_btn_start_call:
                onValidation();
                break;
            case R.id.registers_and_edit_profile_fragment_ll_sub_view:
                break;
        }
    }

    private void onValidation() {

        List<EditText> editTexts = new ArrayList<>();
        List<TextInputLayout> textInputLayouts = new ArrayList<>();
        List<Spinner> spinners = new ArrayList<>();

        textInputLayouts.add(registersAndEditProfileFragmentTilUserName);
        textInputLayouts.add(registersAndEditProfileFragmentTilEmail);
        textInputLayouts.add(registersAndEditProfileFragmentTilPhone);
        textInputLayouts.add(registersAndEditProfileFragmentTilPassword);
        textInputLayouts.add(registersAndEditProfileFragmentTilConfirmPassword);

        spinners.add(registersAndEditProfileFragmentSpBloodTypes);
        spinners.add(registersAndEditProfileFragmentSpGovernments);
        spinners.add(registersAndEditProfileFragmentSpCity);

        if (!Validation.validationAllEmpty(editTexts, textInputLayouts, spinners)) {

            return;
        }

        if (!validationLength(registersAndEditProfileFragmentTilUserName, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationEmail(getActivity(), registersAndEditProfileFragmentTilEmail)) {

            return;
        }

        onCall();
    }

    private void onCall() {
        String name = registersAndEditProfileFragmentTilUserName.getEditText().getText().toString();
        String email = registersAndEditProfileFragmentTilEmail.getEditText().getText().toString();
        String phone = registersAndEditProfileFragmentTilPhone.getEditText().getText().toString();
        String password = registersAndEditProfileFragmentTilPassword.getEditText().getText().toString();
        String passwordConfirmation = registersAndEditProfileFragmentTilConfirmPassword.getEditText().getText().toString();

        String birth_date = registersAndEditProfileFragmentTvBrd.getText().toString();
        String donationLastDate = registersAndEditProfileFragmentTvLastDonationDate.getText().toString();

        int cityId = citiesAdapter.selectedId;
        int bloodTypeId = bloodTypesAdapter.selectedId;

        Call<Client> clientCall;
        boolean auth;

        if (PROFILE) {
            auth = false;
            clientCall = getClient().onSignUp(name, email, birth_date, cityId, phone, donationLastDate, password, passwordConfirmation, bloodTypeId);
        } else {
            auth = true;
            clientCall = getClient().editClientData(name, email, birth_date, cityId, phone, donationLastDate, password
                    , passwordConfirmation, bloodTypeId, clientData.getApiToken());
        }

        userData(getActivity(), clientCall, password, true, auth);
    }
}
