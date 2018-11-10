package com.example.bahary.rahma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.bahary.rahma.Utils.Constants;

public class RegistrationsTypeActivity extends AppCompatActivity {
    TextView scrollingText;
    LinearLayout mCardView;
    Button DonorButton, OrgButton, BeneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations_type);
        scrollingText = (TextView) findViewById(R.id.scrollingtext);
        mCardView = findViewById(R.id.MainScreen_CardView);
        scrollingText.setSelected(true);
        mCardView.getBackground().setAlpha(50);
        DonorButton = findViewById(R.id.MainScreen_Button_Donor);
        OrgButton = findViewById(R.id.MainScreen_Button_Organization);
        BeneButton = findViewById(R.id.MainScreen_Button_Beneficiary);
        DonorButton.getBackground().setAlpha(70);
        OrgButton.getBackground().setAlpha(70);
        BeneButton.getBackground().setAlpha(70);
        DonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginActivity = new Intent(RegistrationsTypeActivity.this, RegistrationActivity.class);
                LoginActivity.putExtra(Constants.Bundle_Login_Type, Constants.Donor);
                startActivity(LoginActivity);
            }
        });
        BeneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginActivity = new Intent(RegistrationsTypeActivity.this, RegistrationActivity.class);
                LoginActivity.putExtra(Constants.Bundle_Login_Type, Constants.Beneficiary);
                startActivity(LoginActivity);
            }
        });
        OrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginActivity = new Intent(RegistrationsTypeActivity.this, RegistrationActivity.class);
                LoginActivity.putExtra(Constants.Bundle_Login_Type, Constants.Organization);
                startActivity(LoginActivity);
            }
        });


    }
}
