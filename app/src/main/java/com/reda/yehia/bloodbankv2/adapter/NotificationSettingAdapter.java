package com.reda.yehia.bloodbankv2.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponseData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationSettingAdapter extends RecyclerView.Adapter<NotificationSettingAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<String> oldBloodTypes = new ArrayList<>();
    private List<GeneralResponseData> bloods = new ArrayList<>();
    public List<Integer> ids = new ArrayList<>();

    public NotificationSettingAdapter(Context context, Activity activity, List<GeneralResponseData> bloods, List<String> oldBloodTypes) {
        this.context = context;
        this.activity = activity;
        this.bloods = bloods;
        this.oldBloodTypes = oldBloodTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_box,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        try {

            holder.checkBoxItemCb.setChecked(false);
            for (int i = 0; i < oldBloodTypes.size(); i++) {
                if (oldBloodTypes.get(i).equals(String.valueOf(bloods.get(position).getId()))) {
                    holder.checkBoxItemCb.setChecked(true);
                    ids.add(bloods.get(position).getId());
                    break;
                }
            }

            holder.checkBoxItemCb.setText(bloods.get(position).getName());

        } catch (Exception e) {

        }
    }

    private void setAction(ViewHolder holder, final int position) {

        try {

            holder.checkBoxItemCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ids.add(bloods.get(position).getId());
                    } else {
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).equals(bloods.get(position).getId())) {
                                ids.remove(i);
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return bloods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.check_box_item_cb)
        CheckBox checkBoxItemCb;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
