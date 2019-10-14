package com.reda.yehia.mirslider;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SliderCreator extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;

    private List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private View.OnClickListener action;
    private int btnIcon, activeIcon, anActiveIcon;
    public List<ImageView> steps = new ArrayList<>();
    public int position = 0;

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
        createImageView();

    }


    public void createImageView() {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.dim_20dp)
                , (int) context.getResources().getDimension(R.dimen.dim_20dp));
        lparams.setMargins(4, 4, 4, 4); // left, top, right, bottom

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lparams);
        steps.add(imageView);
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

            LinearLayout sliderAdapterLlSliderSteps = itemView.findViewById(R.id.slider_adapter_ll_slider_steps);
            Button sliderAdapterBtnSliderAction = itemView.findViewById(R.id.slider_adapter_btn_slider_next);
            ImageView sliderAdapterIvSliderImage = itemView.findViewById(R.id.slider_adapter_iv_slider_image);
            TextView sliderAdapterTvSliderTitle = itemView.findViewById(R.id.slider_adapter_tv_slider_title);

            sliderAdapterBtnSliderAction.setBackgroundResource(btnIcon);

            sliderAdapterIvSliderImage.setImageResource(images.get(position));

            sliderAdapterTvSliderTitle.setText(titles.get(position));

            sliderAdapterLlSliderSteps.removeAllViews();
            for (int i = 0; i < steps.size(); i++) {
                if (i == position) {
                    steps.get(i).setImageResource(activeIcon);
                } else {
                    steps.get(i).setImageResource(anActiveIcon);
                }
                sliderAdapterLlSliderSteps.addView(steps.get(i));
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
