package com.reda.yehia.bloodbankv2.view.fragment.userCycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.EmptySpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.DateTxt;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.mirvalidation.Validation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.getSpinnerData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.showCalender;
import static com.reda.yehia.mirvalidation.Validation.validationEmail;
import static com.reda.yehia.mirvalidation.Validation.validationLength;

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
    Unbinder unbinder;

    private EmptySpinnerAdapter bloodTypesAdapter, gaviermentAdapter, CitiesAdapter;
    private DateTxt birthdayDate, lastDonationDate;

    public RegistersAndEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registers_and_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        bloodTypesAdapter = new EmptySpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), registersAndEditProfileFragmentSpBloodTypes, bloodTypesAdapter, getString(R.string.select_blood_type),
                getClient().getBloodTypes(), true);


        gaviermentAdapter = new EmptySpinnerAdapter(getActivity());
        CitiesAdapter = new EmptySpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), registersAndEditProfileFragmentSpGovernments, gaviermentAdapter, getString(R.string.select_blood_type),
                getClient().getGovernorates(), registersAndEditProfileFragmentSpCity, CitiesAdapter, getString(R.string.select_blood_type),
                getClient().getCities(gaviermentAdapter.selectedId));

        setDates();

        return view;
    }

    private void setDates() {
        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));

        lastDonationDate = new DateTxt(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        birthdayDate = new DateTxt("01", "01", "1990", "01-01-1990");

        registersAndEditProfileFragmentTvBrd.setText(birthdayDate.getDate_txt());
        registersAndEditProfileFragmentTvLastDonationDate.setText(lastDonationDate.getDate_txt());
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

        Validation.validationAllEmpty(editTexts, textInputLayouts, spinners);

        validationLength(registersAndEditProfileFragmentTilUserName, "");
        validationEmail(getActivity(), registersAndEditProfileFragmentTilEmail);


        onCall();
    }

    private void onCall() {

    }
}
