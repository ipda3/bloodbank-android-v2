package com.reda.yehia.mirvalidation;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Spinner;

import com.reda.yehia.mirtoast.ToastCreator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yehia on 08/04/2018.
 */

public class Validation {

    private static String STRING_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$";
    private static String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validationEditTextsEmpty(List<EditText> editTexts) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < editTexts.size(); i++) {
            if (editTexts.get(i).length() <= 0) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationTextInputLayoutListEmpty(List<TextInputLayout> textInputLayoutList) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < textInputLayoutList.size(); i++) {
            if (textInputLayoutList.get(i).getEditText().length() <= 0) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationSpinnersEmpty(List<Spinner> spinners) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < spinners.size(); i++) {
            if (spinners.get(i).getSelectedItemPosition() == 0) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationAllEmpty(List<EditText> editTexts, List<TextInputLayout> textInputLayouts, List<Spinner> spinners) {

        if (validationEditTextsEmpty(editTexts) && validationTextInputLayoutListEmpty(textInputLayouts) && validationSpinnersEmpty(spinners)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validationLength(Activity activity, String text, String errorText) {

        if (text.length() <= 0) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(EditText text, String errorText) {
        if (text.length() <= 0) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(TextInputLayout text, String errorText) {
        if (text.getEditText().length() <= 0) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationStringIsCharAndNumber(Activity activity, String text, String errorText) {

        if (!text.matches(STRING_PATTERN)) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationStringIsCharAndNumber(EditText text, String errorText) {

        if (!text.getText().toString().matches(STRING_PATTERN)) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationStringIsCharAndNumber(TextInputLayout text, String errorText) {

        if (!text.getEditText().getText().toString().matches(STRING_PATTERN)) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationStringIsNumber(Activity activity, String text, String errorText) {

        try {
            int convert = Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        }

    }

    public static boolean validationStringIsNumber(EditText text, String errorText) {

        try {
            int convert = Integer.parseInt(text.getText().toString());
            return true;
        } catch (Exception e) {
            text.setError(errorText);
            return false;
        }

    }

    public static boolean validationStringIsNumber(TextInputLayout text, String errorText) {

        try {
            int convert = Integer.parseInt(text.getEditText().getText().toString());
            return true;
        } catch (Exception e) {
            text.setError(errorText);
            return false;
        }
    }

    public static boolean validationEmail(Activity activity, String email) {

        if (!email.matches(EMAIL_PATTERN)) {
            ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationEmail(Activity activity, EditText email) {

        if (!email.getText().toString().matches(EMAIL_PATTERN)) {
            email.setError(activity.getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }

    }

    public static boolean validationEmail(Activity activity, TextInputLayout email) {

        if (!email.getEditText().getText().toString().matches(EMAIL_PATTERN)) {
            email.setError(activity.getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationPassword(Activity activity, String password, String errorText) {

        validationLength(activity, password, errorText);
        validationStringIsCharAndNumber(activity, password, errorText);

        return true;
    }

    public static boolean validationPassword(EditText password, String errorText) {


        validationLength(password, errorText);
        validationStringIsCharAndNumber(password, errorText);

        return true;
    }

    public static boolean validationPassword(TextInputLayout password, String errorText) {

        validationLength(password, errorText);
        validationStringIsCharAndNumber(password, errorText);

        return true;
    }

}
