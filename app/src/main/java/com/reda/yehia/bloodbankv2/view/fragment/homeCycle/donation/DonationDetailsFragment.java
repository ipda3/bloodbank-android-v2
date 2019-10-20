package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationData;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationRequests;
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
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.onPermission;
import static com.reda.yehia.bloodbankv2.utils.network.InternetState.isConnected;

public class DonationDetailsFragment extends BaseFragment {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.donation_details_fragment_tv_name)
    TextView donationDetailsFragmentTvName;
    @BindView(R.id.donation_details_fragment_tv_age)
    TextView donationDetailsFragmentTvAge;
    @BindView(R.id.donation_details_fragment_tv_blood_type)
    TextView donationDetailsFragmentTvBloodType;
    @BindView(R.id.donation_details_fragment_tv_count)
    TextView donationDetailsFragmentTvCount;
    @BindView(R.id.donation_details_fragment_tv_hospital)
    TextView donationDetailsFragmentTvHospital;
    @BindView(R.id.donation_details_fragment_tv_address)
    TextView donationDetailsFragmentTvAddress;
    @BindView(R.id.donation_details_fragment_tv_phone)
    TextView donationDetailsFragmentTvPhone;
    @BindView(R.id.donation_details_fragment_tv_notes)
    TextView donationDetailsFragmentTvNotes;
    @BindView(R.id.donation_details_fragment_mv_map)
    MapView donationDetailsFragmentMvMap;
    @BindView(R.id.donation_details_fragment_s_fl_shimmer_donations)
    ShimmerFrameLayout donationDetailsFragmentSFlShimmerDonations;
    Unbinder unbinder;

    public int donationId;
    private ClientData clientData;
    private DonationData donationData;

    public DonationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientData = loadUserData(getActivity());

        donationDetailsFragmentMvMap.onCreate(savedInstanceState);

        getDonation();
        setData();

        return view;
    }

    private void getDonation() {
        donationDetailsFragmentSFlShimmerDonations.startShimmer();
        donationDetailsFragmentSFlShimmerDonations.setVisibility(View.VISIBLE);

        if (isConnected(getActivity())) {

            getClient().getDonationRequestData(clientData.getApiToken(), donationId).enqueue(new Callback<DonationRequests>() {
                @Override
                public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                    try {

                        donationDetailsFragmentSFlShimmerDonations.stopShimmer();
                        donationDetailsFragmentSFlShimmerDonations.setVisibility(View.GONE);

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<DonationRequests> call, Throwable t) {
                    ToastCreator.onCreateErrorToast(getActivity(), getActivity().getString(R.string.error));
                }
            });
        } else {
            ToastCreator.onCreateErrorToast(getActivity(), getActivity().getString(R.string.error_inter_net));
        }

    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        toolbarTitle.setText(getString(R.string.donation) + " :- " + donationData.getPatientName());
        donationDetailsFragmentTvName.setText(getString(R.string.name) + " :- " + donationData.getPatientName());
        donationDetailsFragmentTvAge.setText(getString(R.string.age) + " :- " + donationData.getPatientAge());
        donationDetailsFragmentTvBloodType.setText(getString(R.string.blood_type) + " :- " + donationData.getBloodType().getName());
        donationDetailsFragmentTvCount.setText(getString(R.string.bags_number) + " :- " + donationData.getBagsNum());
        donationDetailsFragmentTvHospital.setText(getString(R.string.hospital) + " :- " + donationData.getHospitalName());
        donationDetailsFragmentTvAddress.setText(getString(R.string.address) + " :- " + donationData.getHospitalAddress());
        donationDetailsFragmentTvPhone.setText(getString(R.string.phone) + " :- " + donationData.getPhone());
        donationDetailsFragmentTvNotes.setText(donationData.getNotes());

        donationDetailsFragmentMvMap.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        donationDetailsFragmentMvMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkerOptions currentUserLocation = new MarkerOptions();
                currentUserLocation.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_alt_solid));

                LatLng currentUserLatLang = new LatLng(Double.parseDouble(donationData.getLatitude()), Double.parseDouble(donationData.getLongitude()));
                currentUserLocation.position(currentUserLatLang);
                googleMap.addMarker(currentUserLocation);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, 16));

                float zoom = 10;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, zoom));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.toolbar_back, R.id.donation_details_fragment_btn_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                onBack();
                break;
            case R.id.donation_details_fragment_btn_call:
                onPermission(getActivity());

                getActivity().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "01277778921", null)));
                break;
        }
    }

}
