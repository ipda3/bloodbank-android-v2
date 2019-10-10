package com.reda.yehia.bloodbankv2.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.view.fragment.splashCycle.SplashFragment;

import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class SplashCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);

        replaceFragment(getSupportFragmentManager(), R.id.frame, new SplashFragment());
    }
}
