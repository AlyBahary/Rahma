package com.example.bahary.rahma;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.example.bahary.rahma.Models.LoginModel;
import com.example.bahary.rahma.Utils.Connectors;
import com.example.bahary.rahma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button GologinButton;
    EditText usenameEditText, passwordEditText;
    String Name, Pass;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;
    TextView forgetpass,login_Registration;
    public static final String TAG = "LOGIN";
    ProgressDialog pd;
    String L;
    static Activity loginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Hawk.contains(Constants.Language)){
            L=Hawk.get(Constants.Language);
            Constants.languageChange(L+"",LoginActivity.this);

        }
        setContentView(R.layout.activity_login);


    /*    android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
*/
        loginActivity=this;
        scrolbarbgLayout = findViewById(R.id.scrolbarbg);
        mScrollableTextView = findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);

        usenameEditText = findViewById(R.id.Login_Username);
        passwordEditText = findViewById(R.id.Login_Password);
        forgetpass = findViewById(R.id.login_ForgetPass);
        login_Registration = findViewById(R.id.login_Registration);
        GologinButton = findViewById(R.id.loginButton);
        GologinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginConnection("01272346389", "123456");
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage(getString(R.string.loading));
                pd.show();
                Name = usenameEditText.getText().toString();
                Pass = passwordEditText.getText().toString();
                if (Name.equals("") || Name.equals(null)) {
                    pd.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.emailPlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (Pass.equals("") || Pass.equals(null)) {
                    pd.dismiss();


                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {

                    LoginConnection(Name + "", Pass + "");

                }


            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);

            }
        });
        login_Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegistrationsTypeActivity.class);
                startActivity(i);

            }
        });
    }

    private void LoginConnection(String name, String pass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.login(name, pass).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                pd.dismiss();
                LoginModel loginModel = response.body();
                Boolean x = loginModel.getStatus();

                Log.d(TAG, "onResponse: " + loginModel.getStatus());
                if (x) {
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    Hawk.put(Constants.UserRole, loginModel.getUser().getRole());
                    Hawk.put(Constants.UserName, loginModel.getUser().getUsername());
                    Hawk.put(Constants.USerID, loginModel.getUser().getId());
                    Hawk.put(Constants.Name, loginModel.getUser().getName());
                    Hawk.put(Constants.Mobile, loginModel.getUser().getMobile());
                    Hawk.put(Constants.UserCityID, loginModel.getUser().getCityId());
                    Hawk.put(Constants.UserPromoCode, loginModel.getUser().getPromocode());
                    Log.d(TAG, "onResponse: "+loginModel.getUser().getPromocode());
                    Hawk.put(Constants.UserOrganzationID, loginModel.getUser().getOrganizationId());
                    Hawk.put(Constants.User_Exist,"1");
                    //Hawk.put(Constants.Email,loginModel.getUser().get());
                    startActivity(i);
                    finish();

                } else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.Dataisnotcorrect), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                pd.dismiss();
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
    public static void FinishLogin(){
        loginActivity.finish();
    }
}
