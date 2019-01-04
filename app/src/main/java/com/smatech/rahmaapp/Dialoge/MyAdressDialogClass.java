package com.smatech.rahmaapp.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import com.smatech.rahmaapp.HomeFragments.AdressListFragmet;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.RecyclerView.AdressItemAdapter;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAdressDialogClass extends Dialog {
    public Activity c;
    public Dialog d;

    ///

    RecyclerView recyclerView;
    AdressItemAdapter adressItemAdapter;
    ArrayList<getAllAdressModel1> donationItemModes;

    ///
    public MyAdressDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_adress_dialog);
        donationItemModes = new ArrayList<>();
        recyclerView = findViewById(R.id.AdressListRV);
        adressItemAdapter = new AdressItemAdapter(donationItemModes, getContext(), new AdressItemAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Hawk.put(Constants.add_new_adress_ID, donationItemModes.get(position).getId() + "");
                MyAdressDialogClass.this.dismiss();


            }
        });
        recyclerView.setAdapter(adressItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        adressItemAdapter.notifyDataSetChanged();
        getAllAdressesServices();

    }
    public void getAllAdressesServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);

        getRegistrationsConnectionServices
                .GetAllAdress(Hawk.get(Constants.USerID) + "").enqueue(new Callback<getAllAdressModel>() {
            @Override
            public void onResponse(Call<getAllAdressModel> call, Response<getAllAdressModel> response) {
                getAllAdressModel mgetAllAdressModel = response.body();
                Log.d("onResponse", "onResponse: " + response.body());
                Log.d("onResponse", "onResponse: " + response.toString());
                Log.d("onResponse", "onResponse: " + response.body().toString());

                Boolean x = mgetAllAdressModel.getStatus();
                if (x) {
                    ArrayList<getAllAdressModel1> Adress = mgetAllAdressModel.getAddresses();
                    MyAdressDialogClass.this.donationItemModes.clear();
                    MyAdressDialogClass.this.donationItemModes.addAll(Adress);
                    Log.d("onResponse", "onResponse: " + "Here");
                    Log.d("onResponse", "onResponse: " + MyAdressDialogClass.this.donationItemModes.size());
                    adressItemAdapter.notifyDataSetChanged();
                } else {
                    Log.d("onResponse", "onResponse: " + response.body());
                    Log.d("onResponse", "onResponse: " + response.body().getStatus() + "");
                    Log.d("onResponse", "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<getAllAdressModel> call, Throwable t) {

                Log.d("onResponse", "onFailure: " + t.getMessage());
            }
        });
    }


}
