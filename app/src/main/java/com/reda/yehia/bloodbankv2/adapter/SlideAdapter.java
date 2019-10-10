package com.reda.yehia.bloodbankv2.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.reda.yehia.bloodbankv2.R;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends PagerAdapter {
    Context context;

    LayoutInflater mLayoutInflater;
    private List<Integer> images = new ArrayList<>();


    public SlideAdapter(Context context, List<Integer> images) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {

            View itemView = mLayoutInflater.inflate(R.layout.item_slider_pager, container, false);

            ImageView sliderAdapterIvSliderImage = itemView.findViewById(R.id.slider_adapter_iv_slider);
            sliderAdapterIvSliderImage.setImageResource(images.get(position));

            container.addView(itemView);

            return itemView;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
