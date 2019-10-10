package com.reda.yehia.mirtoast;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToastCreator {

    /**
     * To make custom toast in success state
     *
     * @param activity   : opened activity
     * @param toastTitle :toast text
     */
    public static void onCreateSuccessToast(Activity activity, String toastTitle) {
        LayoutInflater inflater = activity.getLayoutInflater();

        int layout_id = R.layout.toast_success;

        View layout = inflater.inflate(layout_id,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.toast_tv_text);
        text.setText(toastTitle);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * To make custom toast with custom icon  in success state
     *
     * @param activity   : opened activity
     * @param toastTitle :toast text
     * @param toastIcon  :toast icon
     */
    public static void onCreateSuccessToast(Activity activity, String toastTitle, int toastIcon) {
        LayoutInflater inflater = activity.getLayoutInflater();

        int layout_id = R.layout.toast_success;

        View layout = inflater.inflate(layout_id,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.toast_tv_text);
        text.setText(toastTitle);

        CircleImageView icon = layout.findViewById(R.id.toast_iv_icon);
        icon.setImageResource(toastIcon);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * To make custom toast in error state
     *
     * @param activity   : opened activity
     * @param toastTitle :toast text
     */
    public static void onCreateErrorToast(Activity activity, String toastTitle) {
        LayoutInflater inflater = activity.getLayoutInflater();

        int layout_id = R.layout.toast_error;

        View layout = inflater.inflate(layout_id,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.toast_tv_text);
        text.setText(toastTitle);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * To make custom toast with custom icon in error state
     *
     * @param activity   : opened activity
     * @param toastTitle :toast text
     * @param toastIcon  :toast icon
     */
    public static void onCreateErrorToast(Activity activity, String toastTitle, int toastIcon) {
        LayoutInflater inflater = activity.getLayoutInflater();

        int layout_id = R.layout.toast_error;

        View layout = inflater.inflate(layout_id,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.toast_tv_text);
        text.setText(toastTitle);

        CircleImageView icon = layout.findViewById(R.id.toast_iv_icon);
        icon.setImageResource(toastIcon);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
