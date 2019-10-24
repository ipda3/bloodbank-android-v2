package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.donation.createNewDonation.CreateNewDonation;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.mirtoast.ToastCreator;

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
import static com.reda.yehia.bloodbankv2.view.activity.MapsActivity.hospital_address;
import static com.reda.yehia.bloodbankv2.view.activity.MapsActivity.latitude;
import static com.reda.yehia.bloodbankv2.view.activity.MapsActivity.longitude;
import static com.reda.yehia.mirvalidation.Validation.validationLength;
import static com.reda.yehia.mirvalidation.Validation.validationPhone;

public class CreateDonationFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.create_donation_fragment_til_name)
    TextInputLayout createDonationFragmentTilName;
    @BindView(R.id.create_donation_fragment_til_age)
    TextInputLayout createDonationFragmentTilAge;
    @BindView(R.id.create_donation_fragment_til_bags_number)
    TextInputLayout createDonationFragmentTilBagsNumber;
    @BindView(R.id.create_donation_fragment_til_hospital_name)
    TextInputLayout createDonationFragmentTilHospitalName;
    @BindView(R.id.create_donation_fragment_til_hospital_address)
    TextInputLayout createDonationFragmentTilHospitalAddress;
    @BindView(R.id.create_donation_fragment_til_phone)
    TextInputLayout createDonationFragmentTilPhone;
    @BindView(R.id.create_donation_fragment_til_note)
    TextInputLayout createDonationFragmentTilNote;
    @BindView(R.id.create_donation_fragment_sp_blood_types)
    Spinner createDonationFragmentSpBloodTypes;
    @BindView(R.id.create_donation_fragment_sp_government)
    Spinner createDonationFragmentSpGovernment;
    @BindView(R.id.create_donation_fragment_sp_city)
    Spinner createDonationFragmentSpCity;
    Unbinder unbinder;

    public DonationsListFragment donationsListFragment;
    private ClientData clientData;

    public CreateDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_donation, container, false);
        unbinder = ButterKnife.bind(this, view);

        clientData = loadUserData(getActivity());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.toolbar_back, R.id.create_donation_fragment_btn_send, R.id.create_donation_fragment_ll_sub_view})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), view);

        switch (view.getId()) {
            case R.id.toolbar_back:
                onBack();
                break;
            case R.id.create_donation_fragment_btn_send:
                onValidation();
                break;
            case R.id.create_donation_fragment_ll_sub_view:
                break;
        }
    }

    private void onValidation() {

        List<EditText> editTexts = new ArrayList<>();
        List<TextInputLayout> textInputLayouts = new ArrayList<>();
        List<Spinner> spinners = new ArrayList<>();

        textInputLayouts.add(createDonationFragmentTilName);
        textInputLayouts.add(createDonationFragmentTilAge);
        textInputLayouts.add(createDonationFragmentTilBagsNumber);
        textInputLayouts.add(createDonationFragmentTilHospitalAddress);
        textInputLayouts.add(createDonationFragmentTilPhone);
        textInputLayouts.add(createDonationFragmentTilNote);

        spinners.add(createDonationFragmentSpBloodTypes);
        spinners.add(createDonationFragmentSpGovernment);
        spinners.add(createDonationFragmentSpCity);

        if (!validationLength(createDonationFragmentTilName, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationLength(createDonationFragmentTilAge, getString(R.string.invalid_age), 1)) {
            return;
        }

        if (createDonationFragmentSpBloodTypes.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_blood_type));
            return;
        }

        if (!validationLength(createDonationFragmentTilBagsNumber, getString(R.string.invalid_bags_number), 1)) {
            return;
        }

        if (!validationLength(createDonationFragmentTilHospitalName, getString(R.string.invalid_hospital_name), 1)) {
            return;
        }

        if (!validationLength(createDonationFragmentTilHospitalAddress, getString(R.string.invalid_hospital_address), 1)) {
            return;
        }

        if (createDonationFragmentSpGovernment.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_government));
            return;
        }

        if (createDonationFragmentSpCity.getSelectedItemPosition() == 0) {
            ToastCreator.onCreateErrorToast(getActivity(), getString(R.string.select_city));
            return;
        }

        if (!validationPhone(getActivity(), createDonationFragmentTilPhone)) {
            return;
        }

        startCall();

    }

    private void startCall() {

        String apiToken = clientData.getApiToken();
        String patientName = createDonationFragmentTilName.getEditText().getText().toString().trim();
        String patientAge = createDonationFragmentTilAge.getEditText().getText().toString().trim();
        String bagsNum = createDonationFragmentTilBagsNumber.getEditText().getText().toString().trim();
        String hospitalName = createDonationFragmentTilHospitalName.getEditText().getText().toString().trim();
        String hospitalAddress = createDonationFragmentTilHospitalAddress.getEditText().getText().toString().trim();
        String phone = createDonationFragmentTilPhone.getEditText().getText().toString().trim();
        String notes = createDonationFragmentTilNote.getEditText().getText().toString().trim();
        int bloodTypeId = createDonationFragmentSpBloodTypes.getSelectedItemPosition();
        int cityId = createDonationFragmentSpCity.getSelectedItemPosition();

        getClient().createNewDonation(apiToken, patientName, patientAge, bloodTypeId, bagsNum, hospitalName, hospitalAddress,
                cityId, phone, notes, latitude, longitude).enqueue(new Callback<CreateNewDonation>() {
            @Override
            public void onResponse(Call<CreateNewDonation> call, Response<CreateNewDonation> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        donationsListFragment.donationDataList.add(0, response.body().getData());
                        donationsListFragment.donationAdapter.notifyDataSetChanged();
                    }

                    ToastCreator.onCreateSuccessToast(getActivity(), response.body().getMsg());
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<CreateNewDonation> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (hospital_address == null) {
            createDonationFragmentTilHospitalAddress.getEditText().setText(hospital_address);
        }
    }
}
