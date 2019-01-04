package com.smatech.rahmaapp.HomeFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.Donation.DonationItmemModel;
import com.smatech.rahmaapp.Models.Donation.DonationModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.RecyclerView.DonationItemsAdapter;
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
import retrofit2.converter.gson.GsonConverterFactory;import com.smatech.rahmaapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationListFragment extends Fragment {
    TextView mScrollableTextView;
    RecyclerView recyclerView;
    DonationItemsAdapter donationItemsAdapter;
    ArrayList<DonationItmemModel> donationItemModes;
    ImageView menu, Share, Back;
    TextView Title;
    String UserID;
    LinearLayout noData;

    LinearLayout scrolbarbgLayout;
    ProgressDialog pd;

    public DonationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_list, container, false);
        noData=view.findViewById(R.id.noData);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
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
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.loading));
        pd.show();
        ////
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
        Title.setText(getString(R.string.donationsList));

        //////
        donationItemModes = new ArrayList<>();

        recyclerView = view.findViewById(R.id.donationListRV);
        donationItemsAdapter = new DonationItemsAdapter(donationItemModes, getContext(), new DonationItemsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                DonationDatailsFragment donationDatailsFragment = new DonationDatailsFragment();
                Bundle args = new Bundle();
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_ID, "" + donationItemModes.get(position).getId());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_TO_ID, "" + donationItemModes.get(position).getToId());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_ID, "" + donationItemModes.get(position).getId());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_Category, "" + donationItemModes.get(position).getCategory());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_AdressDesc, "" + donationItemModes.get(position).getAddress().getTitle());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_Image, "" + donationItemModes.get(position).getImage());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_Type, "" + donationItemModes.get(position).getType());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_Describtion, "" + donationItemModes.get(position).getDescription());
                args.putString(Constants.Budle_Fragment_From_Donation_Itme_AdressID, "" + donationItemModes.get(position).getAddressId());
                //args.putString(Constants.Budle_Fragment_From_Donation_Itme_AdressDes, "" + donationItemModes.get(position).getAddress().getId());
                Log.d("FFFF", "setOnItemCl" +
                        donationItemModes.get(position).getDescription()
                        +"_____"+donationItemModes.get(position).getAddress().getDescription());

                donationDatailsFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, donationDatailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        recyclerView.setAdapter(donationItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        UserID = Hawk.get(Constants.USerID);
        Log.d("Donationss", UserID + "");
        Log.d("Donationss", "-------------");
        getDonationListConnection(UserID + "", "0", "", "", Hawk.get(Constants.UserRole) + "");

        /////
        return view;


    }

    private void getDonationListConnection(String UserID, String CityID, String Longm, String lat, String role) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);

        getRegistrationsConnectionServices
                .getDonations(UserID).enqueue(new Callback<DonationModel>() {
            @Override
            public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
                pd.dismiss();
                DonationModel donationModel = response.body();

                if (donationModel.getStatus()) {
                    Log.d("RESPONSE", "onResponse: " + response.toString());
                    Log.d("RESPONSE", "onResponse: " + response.isSuccessful());
                    Log.d("RESPONSE", "onResponse: " + response.body().getCount());
                    ArrayList<DonationItmemModel> itmemModels = donationModel.getDonations();
                    // Log.d("Donationss", "onResponse: " + DonationListFragment.this.donationItemModes.size());
                    DonationListFragment.this.donationItemModes.clear();
                    DonationListFragment.this.donationItemModes.addAll(itmemModels);
                    donationItemsAdapter.notifyDataSetChanged();
                    noData.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<DonationModel> call, Throwable t) {
                pd.dismiss();

            }
        });
    }

}
