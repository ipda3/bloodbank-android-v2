package com.reda.yehia.bloodbankv2.view.fragment.splashCycle;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.bloodbankv2.view.fragment.userCycle.RegistersAndEditProfileFragment;

import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class SplashFragment extends BaseFragment {


    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, new RegistersAndEditProfileFragment());

            }
        }, 3000);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

}
