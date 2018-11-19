package com.example.bahary.rahma;

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
import android.widget.Toast;

import com.example.bahary.rahma.Models.LoginModel;
import com.example.bahary.rahma.Models.RegistrationModel;
import com.example.bahary.rahma.Utils.Connectors;
import com.example.bahary.rahma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    TextView mScrollableTextView;
    LinearLayout goTologinActivity,scrolbarbgLayout;
    EditText nameEditText, numberEditText, emailEditText, passEditText, repassEditText;
    String name, number, email, pass, repass;
    Button Registration;
    public static final String TAG = "REGISTRATIONNN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        scrolbarbgLayout=findViewById(R.id.scrolbarbg);
        mScrollableTextView=findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        goTologinActivity = findViewById(R.id.Login);
        goTologinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        nameEditText = findViewById(R.id.RegistratioName);
        numberEditText = findViewById(R.id.RegistratioNumber);
        emailEditText = findViewById(R.id.RegistrationEmail);
        passEditText = findViewById(R.id.RegistratioPass);
        Registration = findViewById(R.id.RegistrationButton);
        repassEditText = findViewById(R.id.RegistratioRePass);


        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                number = numberEditText.getText().toString();
                email = emailEditText.getText().toString();
                pass = passEditText.getText().toString();
                repass = repassEditText.getText().toString();
                if (name.equals("") || name.equals(null)) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.namePlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (number.equals("") || number.equals(null)) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.numberPlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (email.equals("") || email.equals(null)) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.emailPlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (pass.equals("") || pass.equals(null)) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (repass.equals("") || repass.equals(null)) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!pass.equals(repass)) {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.notMatching), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    SingupConnection(pass+""
                            , email+""
                            , number+""
                            , Hawk.get(Constants.Bundle_Login_Type)+""
                            , name+"");
                }
            }

        });

    }

    private void SingupConnection(String password, String username, String mobile, String role, String name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.Signup(password, username, mobile, role, name).enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                RegistrationModel registrationModel = response.body();
                Log.d(TAG, "onResponse: " + registrationModel.getStatus() + registrationModel.getMessage());
                if (registrationModel.getStatus().equals("true")) {
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.numberExist), Snackbar.LENGTH_LONG)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(i);
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }


            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "" + getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
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
