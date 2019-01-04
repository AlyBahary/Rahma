package com.smatech.rahmaapp.HomeFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Dialoge.CustomDialogClass;
import com.smatech.rahmaapp.Map.MapsActivity;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.RecyclerView.AdressItemAdapter;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.smatech.rahmaapp.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class AdressListFragmet extends Fragment {
    TextView mScrollableTextView;
    RecyclerView recyclerView;
    
    AdressItemAdapter adressItemAdapter;
    ArrayList<getAllAdressModel1> donationItemModes;
    LinearLayout scrolbarbgLayout;
    ImageView menu, Share, Back;
    TextView Title;
    Button AddAdressButton;
    ProgressDialog pd;
    LinearLayout noData;



    public AdressListFragmet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adress_list_fragmet, container, false);
        noData=view.findViewById(R.id.noData);
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        AddAdressButton = view.findViewById(R.id.AddAdressButton);
        AddAdressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
        }
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.loading));
        pd.show();
        final String type = getArguments().getString(Constants.Budle_Fragment_From_My_Adress);
        menu = getActivity().findViewById(R.id.toolbarMenu);
        Title = getActivity().findViewById(R.id.toolbarTitle);
        Share = getActivity().findViewById(R.id.toolbarshare);
        Back = getActivity().findViewById(R.id.toolbarback);
        Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
                getActivity().onBackPressed();


            }
        });
        Title.setText(getString(R.string.addressList));

        donationItemModes = new ArrayList<>();
        recyclerView = view.findViewById(R.id.AdressListRV);
        adressItemAdapter = new AdressItemAdapter(donationItemModes, getContext(), new AdressItemAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

                if (type.equals("0")) {
                    CustomDialogClass cdd = new CustomDialogClass(getActivity(),donationItemModes.get(position));
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                } else {
                    Hawk.put(Constants.add_new_adress_ID, donationItemModes.get(position).getId() + "");
                    getActivity().getFragmentManager().popBackStack();
                    getActivity().onBackPressed();
                }
            }
        });
        recyclerView.setAdapter(adressItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        adressItemAdapter.notifyDataSetChanged();
        getAllAdressesServices();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                pd.dismiss();
                getAllAdressModel mgetAllAdressModel = response.body();
                Log.d("onResponse", "onResponse: " + response.body());
                Log.d("onResponse", "onResponse: " + response.toString());
                Log.d("onResponse", "onResponse: " + response.body().toString());

                Boolean x = mgetAllAdressModel.getStatus();
                if (x) {
                    ArrayList<getAllAdressModel1> Adress = mgetAllAdressModel.getAddresses();
                    AdressListFragmet.this.donationItemModes.clear();
                    AdressListFragmet.this.donationItemModes.addAll(Adress);
                    Log.d("onResponse", "onResponse: " + "Here");
                    Log.d("onResponse", "onResponse: " + AdressListFragmet.this.donationItemModes.size());
                    adressItemAdapter.notifyDataSetChanged();
                    noData.setVisibility(View.GONE);
                } else {
                    Log.d("onResponse", "onResponse: " + response.body());
                    Log.d("onResponse", "onResponse: " + response.body().getStatus() + "");
                    Log.d("onResponse", "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<getAllAdressModel> call, Throwable t) {
                pd.dismiss();
                Log.d("onResponse", "onFailure: " + t.getMessage());
            }
        });
    }

}
