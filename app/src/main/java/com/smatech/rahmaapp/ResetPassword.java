package com.smatech.rahmaapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ResetPassword extends AppCompatActivity {

    EditText VertificationCodeEditText, NewPassEditText;
    String Vertification, Pass;
    Button send;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        scrolbarbgLayout = findViewById(R.id.scrolbarbg);
        mScrollableTextView = findViewById(R.id.scrollingtext);
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

        VertificationCodeEditText = findViewById(R.id.VertificationCode);
        NewPassEditText = findViewById(R.id.NewPassword);
        send = findViewById(R.id.ResetPass);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vertification = VertificationCodeEditText.getText().toString() + "";
                Pass = NewPassEditText.getText().toString();
                Resetpass(Vertification,Pass);
            }
        });
    }

    private void Resetpass(String Vertification, String Password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.change_Pass(Vertification, Password).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.PassHadbeenEditted), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_green_light))
                            .show();
                } else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.WrongCode), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                }

            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "" + getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });
    }
}
