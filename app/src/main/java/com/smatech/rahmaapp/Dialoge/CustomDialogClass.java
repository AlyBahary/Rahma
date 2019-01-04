package com.smatech.rahmaapp.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Map.MapsActivity;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomDialogClass extends Dialog {
    public Activity c;
    public Dialog d;
    getAllAdressModel1 getAllAdressModel1;

    TextView Adress_location_Number, Adress_location_Name, Adress_location_Description, number;
    ImageView imageDelete;
    Button Edit;
    String type;

    public CustomDialogClass(Activity a, getAllAdressModel1 getAllAdressModel1) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.getAllAdressModel1 = getAllAdressModel1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        d = this;
        setContentView(R.layout.custom_dialog);

        Adress_location_Number = findViewById(R.id.Adress_location_Number);
        Adress_location_Description = findViewById(R.id.Adrees_Location_Description);
        Adress_location_Name = findViewById(R.id.Adrees_Location_name);
        number = findViewById(R.id.number);
        Edit = findViewById(R.id.Edit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                LocationDetailsDialoge cdd = new LocationDetailsDialoge(c, getAllAdressModel1);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
        imageDelete = findViewById(R.id.Adrees_Delete_IMG);
        if (getAllAdressModel1.getType().equals("0")) {
            number.setText(c.getString(R.string.HomeNo));
            Adress_location_Name.setText(c.getString(R.string.Home));
            Adress_location_Number.setText(getAllAdressModel1.getHouseNo());

        } else if (getAllAdressModel1.getType().equals("1")) {
            number.setText(c.getString(R.string.BlockNo));
            Adress_location_Name.setText(c.getString(R.string.Block));
            Adress_location_Number.setText(getAllAdressModel1.getBlockNo());

        } else {
            number.setText(c.getString(R.string.organizationName));
            Adress_location_Name.setText(c.getString(R.string.organizationloc));
            Adress_location_Number.setText(getAllAdressModel1.getOrganization());

        }


        Adress_location_Description.setText(getAllAdressModel1.getTitle());
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteAdressService(Hawk.get(Constants.USerID) + "", getAllAdressModel1.getId());
            }
        });


    }

    private void deleteAdressService(String UserID, String id) {

        Log.d("TTTT", "deleteAdressService: " + UserID + "----" + id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.delete_address(UserID, id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {

                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + c.getString(R.string.Delete), Snackbar.LENGTH_LONG)
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
                CustomDialogClass.this.dismiss();
                c.getFragmentManager().popBackStack();
                c.onBackPressed();
            }


            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {

            }
        });

    }
}
