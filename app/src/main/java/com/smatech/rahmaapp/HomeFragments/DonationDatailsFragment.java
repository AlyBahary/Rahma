package com.smatech.rahmaapp.HomeFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

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
public class DonationDatailsFragment extends Fragment {

    TextView mScrollableTextView;
    LinearLayout MovingTXTBg;
    ImageView menu, Share, Back;
    TextView Title;
    String Describtion, AdressDesc, Adress, ID, Category, Image, type, To, AdressID;
    TextView FoodQuantity, Adresslocation;
    ImageView ImgView;
    TextView ItemDescription;
    LinearLayout Edit_Img, Delete_Img, Report_Img;
    ArrayList<String> QuantityString, QuantityID, AdressesDescription, AdressesID;
    ArrayAdapter<String> dataAdapter;
    ProgressDialog pd;
    ArrayList<String> DonationElements;

    public DonationDatailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_datails, container, false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        if (Hawk.contains(Constants.DonationDetailsTOEdit)) {
            Hawk.delete(Constants.DonationDetailsTOEdit);

        }
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        mScrollableTextView.getBackground().setAlpha(150);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR) + "");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN) + "");
        }

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
        Title.setText(getString(R.string.DonationDetails));

        ///
        MovingTXTBg = view.findViewById(R.id.scrollbarbg);
        MovingTXTBg.getBackground().setAlpha(185);
        ////// get Data //
        type = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_Type);
        ID = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_ID);
        Image = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_Image);
        Category = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_Category);
        AdressDesc = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_AdressDesc);
        Describtion = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_Describtion);
        Adress = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_AdressDes);
        AdressID = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_AdressID);
        To = getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_TO_ID);
        DonationElements = new ArrayList<>();
        DonationElements.add(type);
        DonationElements.add(ID);
        DonationElements.add(Image);
        DonationElements.add(Category);
        DonationElements.add(Describtion);
        DonationElements.add(Adress);
        DonationElements.add(To);
        DonationElements.add(AdressID);

        Log.d("FFFF", "onCreateView: " + getArguments().getString(Constants.Budle_Fragment_From_Donation_Itme_Describtion));
        /// Fields Declaration
        //Spinners
        FoodQuantity = view.findViewById(R.id.FoodQuantity);
        if (Category.equals("0")) {
            if (type.equals("0")) {
                FoodQuantity.setText(getString(R.string.Lessthanten));

            } else {
                FoodQuantity.setText(getString(R.string.morethanten));

            }
        } else {
            if (type.equals("0")) {
                FoodQuantity.setText(getString(R.string.lessthanthree));

            } else {

                FoodQuantity.setText(getString(R.string.morethanthree));
            }
        }
        QuantityString = new ArrayList<>();


        Adresslocation = view.findViewById(R.id.Adresslocation);

        Adresslocation.setText("" + AdressDesc);

        // EditText
        ItemDescription = view.findViewById(R.id.ItemDescription);
        ItemDescription.setText("" + Describtion);
        // ImageView
        ImgView = view.findViewById(R.id.ImgView);
        if (Category.equals("0"))
            if (Image.equals("") || Image.equals(null)) {
                Picasso.with(getContext()).load(R.drawable.food).placeholder(R.drawable.food).fit().into(ImgView);

            } else {
                Picasso.with(getContext()).load(Image).placeholder(R.drawable.food).fit().into(ImgView);

            }
        else if (Image.equals("") || Image.equals(null)) {
            Picasso.with(getContext()).load(R.drawable.accesories).placeholder(R.drawable.accesories).fit().into(ImgView);
        } else {
            Picasso.with(getContext()).load(Image).placeholder(R.drawable.accesories).fit().into(ImgView);

        }

        //LinearLayout
        Edit_Img = view.findViewById(R.id.Edit_Img);
        Edit_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* addnewdonationFragment addnewdonationfragment = new addnewdonationFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, addnewdonationfragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
*/
                Hawk.put(Constants.DonationDetailsTOEdit, DonationElements);
                DonationDescriptionFragment donationDescriptionFragment = new DonationDescriptionFragment();
                Bundle args = new Bundle();
                if (type.equals("0")) {
                    args.putString(Constants.Budle_Fragment_Donation_type, "FOOD");
                } else {
                    args.putString(Constants.Budle_Fragment_Donation_type, "ACCESRIES");
                }
                donationDescriptionFragment.setArguments(args);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, donationDescriptionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        Delete_Img = view.findViewById(R.id.Delete_Img);
        Delete_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd = new ProgressDialog(getActivity());
                pd.setMessage(getString(R.string.loading));
                pd.show();
                DeleteDonation("" + Hawk.get(Constants.USerID), "" + ID);

                //getActivity().getFragmentManager().popBackStack();
                //                getActivity().onBackPressed();
            }
        });
        Report_Img = view.findViewById(R.id.Report_Img);
        Report_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage(getString(R.string.loading));
                pd.show();
                ReportBenefit("" + Hawk.get(Constants.USerID), "" + To, "" + ID);
            }
        });
        return view;
    }

    private void DeleteDonation(String user_id, String id) {
        pd.dismiss();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.Delete_user_donation(user_id, id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {

                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.PostDeleted), Snackbar.LENGTH_LONG)
                            .setAction("", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    getActivity().getFragmentManager().popBackStack();
                    getActivity().onBackPressed();

                }

            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {


                View parentLayout = getActivity().findViewById(android.R.id.content);
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

    private void ReportBenefit(String user_id, String id, String donation_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.report_user_donation(user_id, id, donation_id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                pd.dismiss();
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.ReportComplete), Snackbar.LENGTH_LONG)
                            .setAction("", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {

                }

            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {

                View parentLayout = getActivity().findViewById(android.R.id.content);
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
