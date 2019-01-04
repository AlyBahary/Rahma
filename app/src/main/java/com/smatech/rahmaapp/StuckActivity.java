package com.smatech.rahmaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.HomeActivity;
import com.smatech.rahmaapp.Models.Donation.DonationItmemModel;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.smatech.rahmaapp.R;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StuckActivity extends AppCompatActivity {
    DonationItmemModel x;
    TextView catgryType, QuantityType, place_no_txt, place_no_, floor_no_txt, floor_no_, house_no_txt, house_no_, Location_Description, Donation_Description, place_no_txt1, Contact_no_;
    ImageView Image;
    Button Done, Driveme, didntwant;
    long T;
    LinearLayout bglayout;
    CountdownView mCvCountdownView;
    TextView BAN_TEXT;
     Locale CurrentLang;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hawk.put(Constants.Time, mCvCountdownView.getRemainTime());

    }

    @Override
    protected void onStop() {
        super.onStop();
        Hawk.put(Constants.Time, mCvCountdownView.getRemainTime());
    }

    //5400000
    //60000
    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuck);
        CurrentLang = getResources().getConfiguration().locale;

        bglayout = findViewById(R.id.bglayout);

        Date currentTime = Calendar.getInstance().getTime();

        if (Hawk.contains(Constants.ENROL_TIME)) {
            long endtime = Long.parseLong(Hawk.get(Constants.ENROL_TIME) + "") + 5400000;
            Log.d("TTTT", "onCreate: there is a enrol time " + "---" + endtime);

            if (currentTime.getTime() > endtime) {
                CancelResrvation(Hawk.get(Constants.USerID) + ""
                        , "" + Hawk.get(Constants.USerFromID), "" + Hawk.get(Constants.DontionID));

            }

        } else {

            Hawk.put(Constants.ENROL_TIME, currentTime.getTime());
            Log.d("TTTT", "onCreate: i will add a time " + currentTime.getTime());

        }

        //bglayout.setBackgroundColor(getColor(R.color.white));
        if (Hawk.contains(Constants.Time)) {
            T = Hawk.get(Constants.Time);
            long endtime = Long.parseLong(Hawk.get(Constants.ENROL_TIME) + "") + 5400000;
            T = endtime - currentTime.getTime();

        } else {
            T = 5400000;
        }
        if (!Hawk.contains(Constants.AlertMSGFlag)) {

            final AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }

            if ((CurrentLang).getLanguage().equals("ar")) {
                builder.setMessage(Hawk.get(Constants.bantext_Ar) + "");
            } else {
                builder.setMessage(Hawk.get(Constants.bantext_EN) + "");
            }
            builder.setTitle(getString(R.string.Alert))
                    .setPositiveButton(getString(R.string.Confirm), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            Hawk.put(Constants.AlertMSGFlag, "1");
        }


        x = Hawk.get("X");
        mCvCountdownView = (CountdownView) findViewById(R.id.Cout_Down_view);
        mCvCountdownView.start(T); // Millisecond
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                CancelResrvation(Hawk.get(Constants.USerID) + "", "" + Hawk.get(Constants.USerFromID), "" + Hawk.get(Constants.DontionID));
            }
        });


// or
   /*     for (long time = T; time < 5400000; time++) {
            mCvCountdownView.updateShow(T);
            Toast.makeText(this, "TTTTT", Toast.LENGTH_SHORT).show();
            Hawk.put(Constants.Time, T);
            Log.d("TTTTTT", "onCreate: "+Hawk.get(Constants.Time));

        }*/
        BAN_TEXT = findViewById(R.id.BAN_TEXT);
        if ((CurrentLang).getLanguage().equals("ar")) {
            BAN_TEXT.setText(Hawk.get(Constants.bantext_Ar) + "");
        } else {
            BAN_TEXT.setText(Hawk.get(Constants.bantext_EN) + "");
        }
        catgryType = findViewById(R.id.catgryType);
        QuantityType = findViewById(R.id.QuantityType);
        Image = findViewById(R.id.Image);
        if (x.getCategory().equals("0")) {
            catgryType.setText(this.getString(R.string.Food));
            if (x.getImage() == null || x.getImage().equals("")) {
                Picasso.with(this).load(R.drawable.food).placeholder(R.drawable.food).fit().into(Image);
            } else {
                Picasso.with(this).load(x.getImage()).placeholder(R.drawable.food).fit().into(Image);
            }

            if (x.getType().equals("0")) {
                QuantityType.setText(this.getString(R.string.lessthanthree));
            } else {
                QuantityType.setText(this.getString(R.string.morethanten));

            }
        } else {
            catgryType.setText(this.getString(R.string.accesories));
            if (x.getImage() == null || x.getImage().equals("")) {
                Picasso.with(this).load(R.drawable.accesories).placeholder(R.drawable.accesories).fit().into(Image);
            } else {
                Picasso.with(this).load(x.getImage()).placeholder(R.drawable.accesories).fit().into(Image);
            }
            if (x.getType().equals("0")) {
                QuantityType.setText(this.getString(R.string.lessthanthree));
            } else {
                QuantityType.setText(this.getString(R.string.morethanthree));

            }
        }
        place_no_txt = findViewById(R.id.place_no_txt);
        place_no_txt1 = findViewById(R.id.place_no_txt1);
        place_no_ = findViewById(R.id.place_no_);
        floor_no_txt = findViewById(R.id.floor_no_txt);
        floor_no_ = findViewById(R.id.floor_no_);
        house_no_txt = findViewById(R.id.house_no_txt);
        house_no_ = findViewById(R.id.house_no_);
        Contact_no_ = findViewById(R.id.Contact_no_);
        Location_Description = findViewById(R.id.Location_Description);
        Donation_Description = findViewById(R.id.Donation_Description);
        if (x.getAddress().getType().equals("0")) {
            place_no_txt.setText(getString(R.string.locationtype) + " : " + getString(R.string.Home));
            place_no_txt1.setText(getString(R.string.HomeNo));
            place_no_.setText(x.getAddress().getHouseNo());
            floor_no_txt.setVisibility(View.GONE);
            floor_no_.setVisibility(View.GONE);
            house_no_.setVisibility(View.GONE);
            house_no_txt.setVisibility(View.GONE);
        }
        if (x.getAddress().getType().equals("1")) {
            place_no_txt.setText(getString(R.string.locationtype) + " : " + getString(R.string.Block));
            place_no_txt1.setText(getString(R.string.BlockNo));
            place_no_.setText(x.getAddress().getBlockNo());
            floor_no_.setText(x.getAddress().getFloorNo());
            floor_no_txt.setText(this.getString(R.string.FloorNumber));
            house_no_txt.setText(this.getString(R.string.HomeNo));
            house_no_.setText(x.getAddress().getHouseNo());
        }
        if (x.getAddress().getType().equals("2")) {
            place_no_txt.setText(getString(R.string.locationtype) + " : " + getString(R.string.organizationloc));
            place_no_txt1.setText(getString(R.string.organizationName));
            place_no_.setText(x.getAddress().getOrganization());
            floor_no_txt.setVisibility(View.GONE);
            floor_no_.setVisibility(View.GONE);
            house_no_.setVisibility(View.GONE);
            house_no_txt.setVisibility(View.GONE);

        }
        Location_Description.setText(x.getAddress().getTitle());
        Log.d("TTTT", "onCreate: "+x.getAddress().getTitle());
        Location_Description.setMovementMethod(new ScrollingMovementMethod());
        Donation_Description.setText(x.getDescription());
        Donation_Description.setMovementMethod(new ScrollingMovementMethod());
        Contact_no_.setText(x.getAddress().getMobile());
        Contact_no_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Contact_no_.getText().toString().equals("") || Contact_no_.getText().toString() == null) {
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + Contact_no_.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        Hawk.put(Constants.USerFromID, x.getFromId());
        Hawk.put(Constants.DontionID, x.getId());
        Log.d("TTT", "onCreate: " + x.getFromId());
        Log.d("TTT", "onCreate: " + x.getId());

        Done = findViewById(R.id.iwantit);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete_donation(Hawk.get(Constants.USerID) + "", "" + Hawk.get(Constants.USerFromID), "" + Hawk.get(Constants.DontionID));

            }
        });
        Driveme = findViewById(R.id.Driveme);
        Driveme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String y = "google.navigation:q="+x.getAddress().getLatitude()+","+x.getAddress().getLongitude();
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + x.getAddress().getLatitude() + "," + x.getAddress().getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        didntwant = findViewById(R.id.didntwantit);
        didntwant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelResrvation(Hawk.get(Constants.USerID) + "", "" + Hawk.get(Constants.USerFromID), "" + Hawk.get(Constants.DontionID));
                Log.d("TTTT", "onResponse: " + Hawk.get(Constants.USerFromID) + "-----" + Hawk.get(Constants.USerID) + "-----" + Hawk.get(Constants.DontionID));

            }
        });


    }

    private void CancelResrvation(String UserID, String From, String DonationID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.cancel_donation(From + "", UserID + "", DonationID + "").enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                Log.d("TTTT", "onResponse: " + forgetPassModel.getStatus());
                if (forgetPassModel.getStatus()) {
                    finish();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    mCvCountdownView.start(5400000);
                    Hawk.put(Constants.User_Stuck, "0");
                    Hawk.delete(Constants.Time);
                    Hawk.delete(Constants.AlertMSGFlag);
                    Hawk.delete(Constants.ENROL_TIME);


                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                Log.d("TTTT", "onFailure: " + t.getMessage());

            }
        });
    }

    private void complete_donation(String UserID, String From, String DonationID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.complete_donation(From, UserID, DonationID).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {
                    Intent intent = new Intent(getApplicationContext(), com.smatech.rahmaapp.HomeActivity.class);
                    startActivity(intent);
                    Hawk.put(Constants.User_Stuck, "0");
                    mCvCountdownView.start(5400000);
                    finish();
                    Hawk.delete(Constants.Time);
                    Hawk.delete(Constants.AlertMSGFlag);
                    Hawk.delete(Constants.ENROL_TIME);
                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {

            }
        });

    }
}
