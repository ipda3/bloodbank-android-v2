package com.reda.yehia.bloodbankv2.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.SpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.client.Client;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponse;
import com.reda.yehia.bloodbankv2.view.activity.HomeCycleActivity;
import com.reda.yehia.mirtoast.ToastCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.REMEMBER;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.SaveData;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.saveUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.progressDialog;

public class GeneralRequest {

    public static void getSpinnerData(Activity activity, final Spinner spinner, final SpinnerAdapter adapter, final String hint,
                                      Call<GeneralResponse> method, final View view, final int selectedId) {

        if (progressDialog == null) {
            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            }
        }

        method.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    HelperMethod.dismissProgressDialog();

                    if (response.body().getStatus() == 1) {
                        if (view != null) {
                            view.setVisibility(View.VISIBLE);
                        }
                        adapter.setData(response.body().getData(), hint);

                        spinner.setAdapter(adapter);

                        spinner.setSelection(selectedId);


                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                HelperMethod.dismissProgressDialog();
            }
        });
    }

    public static void getSpinnerData(final Activity activity, final Spinner spinner, final SpinnerAdapter adapter, final String hint
            , final Call<GeneralResponse> method, final int selectedId1, final Spinner spinner2, final SpinnerAdapter adapter2, final String hint2
            , final Call<GeneralResponse> method2, View view, final int selectedId2) {

        if (progressDialog == null) {
            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
            }
        }

        method.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {

                    HelperMethod.dismissProgressDialog();
                    if (response.body().getStatus() == 1) {

                        adapter.setData(response.body().getData(), hint);

                        spinner.setAdapter(adapter);

                        spinner.setSelection(selectedId1);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    getSpinnerData(activity, spinner2, adapter2, hint2, method2, view, selectedId2);
                                } else {
                                    if (view != null) {
                                        view.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                HelperMethod.dismissProgressDialog();
            }
        });
    }

    public static void userData(final Activity activity, Call<Client> method, final String password, final boolean remember, final boolean auth) {

        if (!progressDialog.isShowing()) {
            HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));
        }

        method.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                try {
                    HelperMethod.dismissProgressDialog();

                    if (response.body().getStatus() == 1) {

                        SaveData(activity, USER_PASSWORD, password);
                        saveUserData(activity, response.body().getData());

                        if (auth) {
                            SaveData(activity, REMEMBER, remember);
                            Intent intent = new Intent(activity, HomeCycleActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }

                    }

                    ToastCreator.onCreateErrorToast(activity, response.body().getMsg());

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                HelperMethod.dismissProgressDialog();
                ToastCreator.onCreateErrorToast(activity, activity.getString(R.string.error));
            }
        });
    }

}
