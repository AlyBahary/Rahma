package com.smatech.rahmaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.orhanobut.hawk.Hawk;
import com.smatech.rahmaapp.Utils.Constants;

import java.util.Locale;

public class RegistrationsTypeActivity extends AppCompatActivity {
    TextView scrollingText;
    LinearLayout mCardView;
    LinearLayout scrolbarbgLayout;
    Button DonorButton, OrgButton, BeneButton;
    static Activity RegtypeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrations_type);
        //Hawk.init(this).build();
        RegtypeActivity=this;
        scrolbarbgLayout=findViewById(R.id.scrolbarbg);
        scrollingText = (TextView) findViewById(R.id.scrollingtext);
        mCardView = findViewById(R.id.MainScreen_CardView);
        scrollingText.setSelected(true);
        mCardView.getBackground().setAlpha(50);
        scrolbarbgLayout.getBackground().setAlpha(120);
        scrollingText.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            scrollingText.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            scrollingText.setText(Hawk.get(Constants.Slider_txt_EN)+"");
        }

        DonorButton = findViewById(R.id.MainScreen_Button_Donor);
        OrgButton = findViewById(R.id.MainScreen_Button_Organization);
        BeneButton = findViewById(R.id.MainScreen_Button_Beneficiary);
        DonorButton.getBackground().setAlpha(70);
        OrgButton.getBackground().setAlpha(70);
        BeneButton.getBackground().setAlpha(70);
        DonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.UserType, Constants.Donor);
                Intent LoginActivity = new Intent(RegistrationsTypeActivity.this, RegistrationActivity.class);
                Hawk.put(Constants.Bundle_Login_Type, Constants.Donor);
                startActivity(LoginActivity);
            }
        });
        BeneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.UserType, Constants.Beneficiary);
                Intent LoginActivity = new Intent(RegistrationsTypeActivity.this, RegistrationActivity.class);
                Hawk.put(Constants.Bundle_Login_Type, Constants.Beneficiary);
                startActivity(LoginActivity);
            }
        });
        OrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.UserType, Constants.Organization);
                Intent LoginActivity = new Intent(RegistrationsTypeActivity.this, RegistrationActivity.class);
                Hawk.put(Constants.Bundle_Login_Type, Constants.Organization);
                startActivity(LoginActivity);
            }
        });


    }
    public static void FinishRegistrationType(){
        RegtypeActivity.finish();
    }
}
