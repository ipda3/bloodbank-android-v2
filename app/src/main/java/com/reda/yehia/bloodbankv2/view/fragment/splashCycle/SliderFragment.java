package com.reda.yehia.bloodbankv2.view.fragment.splashCycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.SliderCreator;
import com.reda.yehia.bloodbankv2.view.activity.UserCycleActivity;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SliderFragment extends BaseFragment {


    @BindView(R.id.slider_fragment_vp_slider)
    ViewPager sliderFragmentVpSlider;
    Unbinder unbinder;
    private SliderCreator sliderCreator;

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);
        StatusBarUtil.setTranslucent(getActivity());

        View.OnClickListener action = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = sliderFragmentVpSlider.getCurrentItem();
                if (currentItem == sliderCreator.images.size() - 1) {
                    Intent intent = new Intent(getActivity(), UserCycleActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    sliderFragmentVpSlider.setCurrentItem(currentItem + 1);
                }

            }
        };

        sliderCreator = new SliderCreator(getActivity(), action, R.drawable.ic_next, R.drawable.shape_active
                , R.drawable.shape_an_active);

        sliderCreator.addPage(R.drawable.ic_slide2, getString(R.string.slider1));
        sliderCreator.addPage(R.drawable.ic_slide2, getString(R.string.slider1));
        sliderCreator.addPage(R.drawable.ic_slide3, getString(R.string.slider1));

        sliderFragmentVpSlider.setAdapter(sliderCreator);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
