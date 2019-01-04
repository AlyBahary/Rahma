package com.smatech.rahmaapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText EmailEditText;
    String email;
    Button send;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;
    TextView haveCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        scrolbarbgLayout=findViewById(R.id.scrolbarbg);
        mScrollableTextView=findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);

        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
        }
        haveCode=findViewById(R.id.haveCode);
        haveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ForgetPasswordActivity.this,ResetPassword.class);
                startActivity(i);
            }
        });

        EmailEditText = findViewById(R.id.ForgetPass_Email);
        send=findViewById(R.id.SendVerifiactionButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = EmailEditText.getText().toString() + "";
                forgetPassConnection(email+"");
            }
        });
    }

    private void forgetPassConnection(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.ForgetPass(email).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                Log.d("ForgetPAss", "onResponse: "+forgetPassModel.getStatus());
                if (!forgetPassModel.getStatus()) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.ErrorEmail1), Snackbar.LENGTH_LONG)
                            .setAction("Registration", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent i=new Intent(getApplicationContext(),RegistrationActivity.class);
                                    startActivity(i);
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
                else{
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.VertificationCode), Snackbar.LENGTH_LONG)
                            .setAction("", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    Intent i=new Intent(getApplicationContext(),ResetPassword.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {

            }
        });
    }
}

