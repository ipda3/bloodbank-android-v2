package com.reda.yehia.bloodbankv2.view.fragment.splashCycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.view.activity.HomeCycleActivity;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.LoadBoolean;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.REMEMBER;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class SplashFragment extends BaseFragment {


    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_splash, container, false);
        StatusBarUtil.setTranslucent(getActivity());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LoadBoolean(getActivity(), REMEMBER) && loadUserData(getActivity()) != null) {
                    Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.frame, new SliderFragment());
                }

            }
        }, 3000);


        return inflate;
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
