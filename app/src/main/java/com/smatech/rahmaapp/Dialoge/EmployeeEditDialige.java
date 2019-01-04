package com.smatech.rahmaapp.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Models.UserModel;

import com.smatech.rahmaapp.Utils.Connectors;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeEditDialige extends Dialog {

    EditText EmailEditText, NameEditText, MobileEditText;
    String Name, Email, Password, Mobile, ID;
    Button save;
    
    public Activity c;
    public Dialog d;
    UserModel x;
    ProgressDialog pd;

    public EmployeeEditDialige(Activity a, UserModel x) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.x = x;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.employee_edit_dialoge);
        pd=new ProgressDialog(getContext());
        pd.setMessage(c.getString(R.string.loading));
        EmailEditText = findViewById(R.id.SettingEmail);
        NameEditText = findViewById(R.id.SettingName);
        MobileEditText = findViewById(R.id.SettingNumber);
        EmailEditText.setText(""+x.getUsername() );
        NameEditText.setText("" + x.getName());
        MobileEditText.setText("" + x.getMobile());
        save = findViewById(R.id.Settingsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailEditText.getText().toString().equals(Email)
                        && NameEditText.getText().toString().equals(Name)
                        && MobileEditText.getText().toString().equals(Mobile)) {
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + c.getString(R.string.NoChangeHappen), Snackbar.LENGTH_LONG)
                            .setAction( c.getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    pd.show();
                    Name=NameEditText.getText().toString();
                    Email=EmailEditText.getText().toString();
                    Mobile=MobileEditText.getText().toString();
                    ID=x.getId();
                    editEmployee(Email, Mobile, Name, ID);
                }
            }
        });

    }

    private void editEmployee(String email, String mobile, String name, String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.EditAcc(email, mobile, name, id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                pd.dismiss();
                ForgetPassModel editModel = response.body();
                if(editModel.getStatus()){
                    dismiss();
                    c.getFragmentManager().popBackStack();
                    c.onBackPressed();
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + c.getString(R.string.Accounthadbeenedited), Snackbar.LENGTH_LONG)
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    c.getFragmentManager().popBackStack();
                    //c.onBackPressed();

                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                pd.dismiss();
                View parentLayout = c.findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "" + c.getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                        .setAction(""+c.getString(R.string.CLOSE), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });
    }
}
