package com.reda.yehia.bloodbankv2.view.fragment;

import android.support.v4.app.Fragment;

import com.reda.yehia.bloodbankv2.view.activity.BaseActivity;

public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpActivity();
    }
}
