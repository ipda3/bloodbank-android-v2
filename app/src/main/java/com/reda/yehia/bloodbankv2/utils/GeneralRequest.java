package com.reda.yehia.bloodbankv2.utils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.EmptySpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralRequest {

    public static void getSpinnerData(Activity activity, final Spinner spinner, final EmptySpinnerAdapter adapter, final String hint,
                                      Call<GeneralResponse> method, boolean show) {

        HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));

        method.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    HelperMethod.dismissProgressDialog();

                    if (response.body().getStatus() == 1) {
                        spinner.setVisibility(View.VISIBLE);
                        adapter.setData(response.body().getData(), hint);

                        spinner.setAdapter(adapter);


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

    public static void getSpinnerData(final Activity activity, final Spinner spinner, final EmptySpinnerAdapter adapter, final String hint
            , final Call<GeneralResponse> method, final Spinner spinner2, final EmptySpinnerAdapter adapter2, final String hint2
            , final Call<GeneralResponse> method2) {

        HelperMethod.showProgressDialog(activity, activity.getString(R.string.wait));

        method.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {

                    HelperMethod.dismissProgressDialog();
                    if (response.body().getStatus() == 1) {

                        adapter.setData(response.body().getData(), hint);

                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    getSpinnerData(activity, spinner2, adapter2, hint2,
                                            method2, false);
                                } else {
                                    spinner2.setVisibility(View.GONE);
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

}
