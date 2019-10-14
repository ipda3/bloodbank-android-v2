package com.reda.yehia.bloodbankv2.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;

import java.util.ArrayList;
import java.util.List;

public class SliderCreator extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;

    public List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private View.OnClickListener action;
    private int btnIcon, activeIcon, anActiveIcon;
    public List<ImageView> steps = new ArrayList<>();
    public List<LinearLayout> stepsLayout = new ArrayList<>();
    public List<Boolean> done = new ArrayList<>();
    public int position = 1;

    public SliderCreator(Context context, View.OnClickListener action, int btnIcon, int activeIcon, int anActiveIcon) {
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.action = action;
        this.btnIcon = btnIcon;
        this.activeIcon = activeIcon;
        this.anActiveIcon = anActiveIcon;
    }

    public void addPage(Integer image, String title) {
        images.add(image);
        titles.add(title);
        done.add(false);
    }


    public ImageView createImageView() {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.dim_10dp)
                , (int) context.getResources().getDimension(R.dimen.dim_10dp));
        lparams.setMargins(4, 4, 4, 4); // left, top, right, bottom

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lparams);
        return (imageView);
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
            this.position = position;

            View itemView = mLayoutInflater.inflate(R.layout.item_slider_pager, container, false);

            LinearLayout sliderAdapterLlSliderSteps = itemView.findViewById(R.id.slider_adapter_ll_slider_steps);
            Button sliderAdapterBtnSliderAction = itemView.findViewById(R.id.slider_adapter_btn_slider_next);
            ImageView sliderAdapterIvSliderImage = itemView.findViewById(R.id.slider_adapter_iv_slider_image);
            TextView sliderAdapterTvSliderTitle = itemView.findViewById(R.id.slider_adapter_tv_slider_title);

            sliderAdapterBtnSliderAction.setBackgroundResource(btnIcon);
            sliderAdapterBtnSliderAction.setOnClickListener(action);

            sliderAdapterIvSliderImage.setImageResource(images.get(position));

            sliderAdapterTvSliderTitle.setText(titles.get(position));

            for (int i = 0; i < images.size(); i++) {
                ImageView imageView = createImageView();
                if (i == position) {
                    imageView.setImageResource(activeIcon);
                } else {
                    imageView.setImageResource(anActiveIcon);
                }
                sliderAdapterLlSliderSteps.addView(imageView);
            }

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
