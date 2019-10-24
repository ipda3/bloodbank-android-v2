package com.reda.yehia.mirvalidation;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.telephony.TelephonyManager;
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

    public static void cleanError(List<TextInputLayout> textInputLayoutList) {

        for (int i = 0; i < textInputLayoutList.size(); i++) {
            textInputLayoutList.get(i).setErrorEnabled(false);
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

    public static boolean validationLength(Activity activity, String text, String errorText, int length) {

        if (text.length() <= length) {
            ToastCreator.onCreateErrorToast(activity, errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(EditText text, String errorText, int length) {
        if (text.length() <= length) {
            text.setError(errorText);
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationLength(TextInputLayout text, String errorText, int length) {
        if (text.getEditText().length() <= length) {
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
            text.getEditText().setError(errorText);
            return false;
        }
    }

    public static boolean validationEditTextsEmpty(List<EditText> editTexts, String errorText) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < editTexts.size(); i++) {
            if (!validationLength(editTexts.get(i), errorText)) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false) && !booleans.contains(true)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationTextInputLayoutListEmpty(List<TextInputLayout> textInputLayoutList, String errorText) {

        List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < textInputLayoutList.size(); i++) {
            if (!validationLength(textInputLayoutList.get(i), errorText)) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }

        if (booleans.contains(false) && !booleans.contains(true)) {
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

        if (booleans.contains(false) && !booleans.contains(true)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationAllEmpty(List<EditText> editTexts, List<TextInputLayout> textInputLayouts, List<Spinner> spinners, String errorText) {

        if (validationEditTextsEmpty(editTexts, errorText) && validationTextInputLayoutListEmpty(textInputLayouts, errorText)
                && validationSpinnersEmpty(spinners)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validationPhone(Activity activity, String phone) {

        TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String locale = manager.getSimCountryIso().toUpperCase();

        Country country = new Country();

        Country country1 = country.getCountry(locale);

        String phone1 = phone.replace("+2", "");

        if (phone1.length() >= country1.getLength_min() && phone.length() <= country1.getLength_max()) {
            return true;
        } else {
            ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.invalid_phone1) + " " + country1.getLength_min()
                    + " " + activity.getString(R.string.invalid_phone2));
            return false;
        }
    }

    public static boolean validationPhone(Activity activity, EditText phone) {

        TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String locale = manager.getSimCountryIso().toUpperCase();

        Country country = new Country();

        Country country1 = country.getCountry(locale);

        String phone1 = phone.getText().toString().replace("+2", "");
        if (phone1.length() >= country1.getLength_min() && phone.getText().length() <= country1.getLength_max()) {
            return true;
        } else {
            phone.setError(activity.getString(R.string.invalid_phone1) + " " + country1.getLength_min()
                    + " " + activity.getString(R.string.invalid_phone2));
            return false;
        }
    }

    public static boolean validationPhone(Activity activity, TextInputLayout phone) {

        TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String locale = manager.getSimCountryIso().toUpperCase();

        Country country = new Country();

        Country country1 = country.getCountry(locale);

        String phone1 = phone.getEditText().getText().toString().replace("+2", "");

        if (phone1.length() >= country1.getLength_min() && phone.getEditText().getText().length() <= country1.getLength_max()) {
            return true;
        } else {
            phone.setError(activity.getString(R.string.invalid_phone1) + " " + country1.getLength_min()
                    + " " + activity.getString(R.string.invalid_phone2));
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
            email.getEditText().setError(activity.getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }
    }

    public static boolean validationPassword(Activity activity, String password, int length, String errorText) {

        validationLength(activity, password, errorText, length);
        validationStringIsCharAndNumber(activity, password, errorText);

        return true;
    }

    public static boolean validationPassword(EditText password, int length, String errorText) {


        validationLength(password, errorText, length);
        validationStringIsCharAndNumber(password, errorText);

        return true;
    }

    public static boolean validationPassword(TextInputLayout password, int length, String errorText) {

        if (validationLength(password, errorText, length)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean validationConfirmPassword(Activity activity, String password, String confirmPassword) {

        if (password.equals(confirmPassword)) {
            return true;
        } else {
            ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.invalid_confirm_password));
            return false;
        }
    }

    public static boolean validationConfirmPassword(Activity activity, EditText password, EditText confirmPassword) {

        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            return true;
        } else {
            confirmPassword.setError(activity.getString(R.string.invalid_confirm_password));
            return false;
        }

    }

    public static boolean validationConfirmPassword(Activity activity, TextInputLayout password, TextInputLayout confirmPassword) {

        if (password.getEditText().getText().toString().equals(confirmPassword.getEditText().getText().toString())) {
            return true;
        } else {
            confirmPassword.getEditText().setError(activity.getString(R.string.invalid_confirm_password));
            return false;
        }

    }

}
