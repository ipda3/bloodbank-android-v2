package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

public class DonationsListFragment extends BaseFragment {


    public DonationsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycle_layout, container, false);
    }

}
