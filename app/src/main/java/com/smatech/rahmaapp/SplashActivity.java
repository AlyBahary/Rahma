package com.smatech.rahmaapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smatech.rahmaapp.Models.SocialModel.SocialModel;
import com.smatech.rahmaapp.Models.SocialModel.SocialModelExample;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends Activity {

    private ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hawk.init(this).build();
        setContentView(R.layout.activity_splash);
        if (Hawk.contains(Constants.Language)) {
            Log.d("TTTTTTT", "onCreate: Contain");

            if (Hawk.get(Constants.Language).equals("ar")) {
                Log.d("TTTTTTT", "onCreate: AR");

                Hawk.put(Constants.Language, "ar");
                Constants.languageChange("ar", this, "");
            } else {
                Hawk.put(Constants.Language, "en");
                Constants.languageChange("en", this, "");
                Log.d("TTTTTTT", "onCreate: english");

            }
        }
        Date currentTime = Calendar.getInstance().getTime();
        Log.d("TTTT", "onCreate:DateTimeOnly "+(currentTime.getTime()-currentTime.getTime()));
       // Log.d("TTTT", "onCreate:DateString "+(currentTime-currentTime));


        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
        };
        String rationale = "Please provide some permissions so that you can smoothly use the Rahma";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.

                statusCheck();


            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                finish();

            }
        });

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else {
            getSocial();
            mCountDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                    i++;
                    mProgressBar.setProgress((int) i * 100 / (4000 / 1000));

                }

                @Override
                public void onFinish() {
                    //Do what you want
                    i++;
                    mProgressBar.setProgress(100);

                }
            };
            mCountDownTimer.start();


        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("State", "onResume: Restart");
        statusCheck();


    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        SplashActivity.this.finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getSocial() {
        final Locale CurrentLang = getResources().getConfiguration().locale;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.get_social().enqueue(new Callback<SocialModelExample>() {
            @Override
            public void onResponse(Call<SocialModelExample> call, Response<SocialModelExample> response) {
                SocialModelExample example = response.body();
                Log.d("TTTT", "onResponse: 111" + CurrentLang);
                if (example.getStatus()) {
                    SocialModel socialModel = example.getSetting();


                    Hawk.put(Constants.Slider_txt_EN, Html.fromHtml(socialModel.getSlideText()));

                    Hawk.put(Constants.Slider_txt_AR, Html.fromHtml(socialModel.getSlideTextAr()));

                    Hawk.put(Constants.bantext_Ar, Html.fromHtml(socialModel.getBantext_ar()));
                    Hawk.put(Constants.bantext_EN, Html.fromHtml(socialModel.getBantext()));
                    if (Hawk.contains(Constants.User_Exist)) {
                        if (Hawk.get(Constants.User_Exist).equals("1")) {
                            Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                }

            }

            @Override
            public void onFailure(Call<SocialModelExample> call, Throwable t) {
                Log.d("TTTT", "onFailure: Exception: "+t.getMessage());

                Toast.makeText(SplashActivity.this, "There is no internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
