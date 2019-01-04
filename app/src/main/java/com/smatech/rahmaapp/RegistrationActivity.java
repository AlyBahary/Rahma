package com.smatech.rahmaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.RegistrationModels.RegistraionMode;
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

public class RegistrationActivity extends AppCompatActivity {
    TextView mScrollableTextView;
    LinearLayout goTologinActivity, scrolbarbgLayout;
    EditText nameEditText, numberEditText, emailEditText, passEditText, repassEditText, PromoCodeEditText;
    CheckBox terms_checkbox;
    TextView terms_condition;
    String name, number, email, pass, repass, PromoCode;
    Button Registration;
    public static final String TAG = "REGISTRATIONNN";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        scrolbarbgLayout = findViewById(R.id.scrolbarbg);
        mScrollableTextView = findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);

        //mScrollableTextView.setMovementMethod(new ScrollingMovementMethod());
        //mScrollableTextView.startAnimation((Animation) AnimationUtils.loadAnimation(this, R.anim.animation));
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR) + "");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN) + "");
        }
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
        PromoCodeEditText = findViewById(R.id.PromoCodeEditText);
        terms_checkbox=findViewById(R.id.terms_checkbox);
        terms_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (terms_checkbox.isChecked()) {
                    Registration.setEnabled(true);
                    Registration.setBackgroundResource(R.drawable.button1);


                } else {
                    Registration.setEnabled(false);
                    Registration.setBackgroundResource(R.drawable.button1_disable);
                }
            }
        });
        terms_condition=findViewById(R.id.terms_condition);
        terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegistrationActivity.this,Terms_Condrition.class);
                startActivity(i);
            }
        });


        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegistrationActivity.this);
                pd.setMessage(getString(R.string.loading));
                pd.show();
                name = nameEditText.getText().toString();
                number = numberEditText.getText().toString();
                email = emailEditText.getText().toString();
                pass = passEditText.getText().toString();
                repass = repassEditText.getText().toString();
                PromoCode = PromoCodeEditText.getText().toString();
                Log.d(TAG, "onClick: promo" + PromoCode);

                if (name.equals("") || name.equals(null)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.namePlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (number.equals("") || number.equals(null)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.numberPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (email.equals("") || email.equals(null)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.emailPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (pass.equals("") || pass.equals(null)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (repass.equals("") || repass.equals(null)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!pass.equals(repass)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.notMatching), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else if (!terms_checkbox.isChecked()) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.youhavetoAccepttermsandcondition), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    SingupConnection(pass + ""
                            , email + ""
                            , number + ""
                            , Hawk.get(Constants.Bundle_Login_Type) + ""
                            , name + "", PromoCode);
                }
            }

        });

    }

    private void SingupConnection(String password, String username, String mobile, String role, String name, String PromoCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        Hawk.put(Constants.LOGIN_NAME, mobile);
        Hawk.put(Constants.LOGIN_pass, password);
        getRegistrationsConnectionServices.Signup(password, username, mobile, role, name, PromoCode).enqueue(new Callback<RegistraionMode>() {
            @Override
            public void onResponse(Call<RegistraionMode> call, Response<RegistraionMode> response) {
                RegistraionMode registrationModel = response.body();
                Boolean x = registrationModel.getStatus();
                pd.dismiss();
                if (x) {
                    Hawk.put(Constants.UserRole, registrationModel.getUser().getRole());
                    Hawk.put(Constants.UserName, registrationModel.getUser().getUsername());
                    Hawk.put(Constants.USerID, registrationModel.getUser().getId());
                    Hawk.put(Constants.Name, registrationModel.getUser().getName());
                    Hawk.put(Constants.Mobile, registrationModel.getUser().getMobile());
                    Hawk.put(Constants.UserPromoCode, registrationModel.getUser().getPromocode());
                    Hawk.put(Constants.User_Exist, "1");

                    // Hawk.put(Constants.Mobile, registrationModel.getUser().getType());
                    Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
                    startActivity(i);
                    LoginActivity.FinishLogin();
                    RegistrationsTypeActivity.FinishRegistrationType();
                    finish();


                } else {
                    final Locale CurrentLang = getResources().getConfiguration().locale;
                    if ((CurrentLang).getLanguage().equals("ar")) {
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "" + registrationModel.getMessage_ar(), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "" + registrationModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }

                }


            }

            @Override
            public void onFailure(Call<RegistraionMode> call, Throwable t) {
                pd.dismiss();
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
