package com.reda.yehia.bloodbankv2.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.utils.GPSTracker;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.reda.yehia.bloodbankv2.utils.HelperMethod.bitmapDescriptorFromVector;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int geocoderMaxResults = 1;
    public static double latitude, longitude;
    public static String hospital_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        try {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            } else {

                GPSTracker gpsTracker = new GPSTracker(MapsActivity.this, MapsActivity.this);
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, geocoderMaxResults);
                Address address = addresses.get(0);
                hospital_address = address.getAddressLine(0);

                LatLng you = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(you, 17f));

            }


        } catch (Exception e) {
            //e.printStackTrace();


        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                markerOptions.icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_map_marker_alt_solid));

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                latitude = latLng.latitude;
                longitude = latLng.longitude;

                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);

                try {
                    /**
                     * Geocoder.getFromLocation - Returns an array of Addresses
                     * that are known to describe the area immediately surrounding the given latitude and longitude.
                     */
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, geocoderMaxResults);
                    Address address = addresses.get(0);
                    hospital_address = address.getAddressLine(0);

                } catch (Exception e) {
                    //e.printStackTrace();

                }

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

            }
        });
    }

    @OnClick(R.id.donation_details_fragment_btn_call)
    public void onViewClicked() {
        finish();
    }
}
