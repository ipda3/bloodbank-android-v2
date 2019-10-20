package com.reda.yehia.bloodbankv2.view.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.utils.GPSTracker;

import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int geocoderMaxResults = 1;
    public static double latitude, longitude;
    public static String hospital_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        try {
            /**
             * Geocoder.getFromLocation - Returns an array of Addresses
             * that are known to describe the area immediately surrounding the given latitude and longitude.
             */
            GPSTracker gpsTracker = new GPSTracker(MapsActivity.this, MapsActivity.this);
            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, geocoderMaxResults);
            Address address = addresses.get(0);
            hospital_address = address.getAddressLine(0);

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

                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_alt_solid));

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
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

            }
        });
    }
}
