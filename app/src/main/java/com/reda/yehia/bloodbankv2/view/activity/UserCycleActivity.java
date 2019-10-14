package com.reda.yehia.bloodbankv2.view.activity;

import android.os.Bundle;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.view.fragment.userCycle.LoginFragment;

import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class UserCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);

        replaceFragment(getSupportFragmentManager(), R.id.frame, new LoginFragment());
    }
}
