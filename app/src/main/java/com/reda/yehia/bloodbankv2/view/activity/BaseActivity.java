package com.reda.yehia.bloodbankv2.view.activity;

import android.support.v7.app.AppCompatActivity;

import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

public class BaseActivity extends AppCompatActivity {

    public BaseFragment baseFragment;

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }

    public void superBackPressed() {
        super.onBackPressed();
    }

}
