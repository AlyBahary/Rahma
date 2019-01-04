package com.smatech.rahmaapp.NavFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.UserData.UserDataModel;
import com.smatech.rahmaapp.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    ImageView menu, Share, Back;
    TextView Title;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;
    TextView AccountType, Nmae, Email, Number, Noperfamily1, NopermoreFamily1, AccountcharityReferedByYouNo,
            Noperfamily2, NopermoreFamily2,NumberBenefitorRefferedByYoyNo,AccountbenefitesReferedByYouNo,AccountPromoCode,NumberCharityRefferedByYoyNo;
    TextView totalnumberdonationsreferedbyyouNo,totalnumberdonationsreferedbyyou;
    ProgressDialog pd;
    LinearLayout profileExtraData;
    ImageView Profile_logo;
    Locale CurrentLang;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.loading));
        pd.show();
        CurrentLang = getResources().getConfiguration().locale;
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        Profile_logo=view.findViewById(R.id.Profile_logo);
        Profile_logo.setVisibility(View.GONE);
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        Log.d("TTTT", "onCreate: CurrentLang"+CurrentLang.getLanguage());

        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
        }

        profileExtraData = view.findViewById(R.id.profileExtraData);
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
        Title.setText(getString(R.string.Profile));

        Nmae = view.findViewById(R.id.AccountName);
        Nmae.setText(Hawk.get(Constants.Name) + "");
        Email = view.findViewById(R.id.AccountEmail);
        Email.setText(Hawk.get(Constants.UserName) + "");
        Number = view.findViewById(R.id.AccountNumber);
        Number.setText(Hawk.get(Constants.Mobile) + "");
        AccountPromoCode=view.findViewById(R.id.AccountPromoCode);
        AccountPromoCode.setText(getString(R.string.PromoCodee)+" : "+Hawk.get(Constants.UserPromoCode));
        AccountType = view.findViewById(R.id.AccountType);
        NumberCharityRefferedByYoyNo = view.findViewById(R.id.NumberCharityRefferedByYoyNo);
        Noperfamily1 = view.findViewById(R.id.AccountFamilyDonationNo);
        NopermoreFamily1 = view.findViewById(R.id.AccountMoreFamilyDonationNo);
        Noperfamily2 = view.findViewById(R.id.AccountEoughForFamilyDonationNo);
        NopermoreFamily2 = view.findViewById(R.id.AccountUserReferedByYouNo);
        AccountbenefitesReferedByYouNo = view.findViewById(R.id.AccountbenefitesReferedByYouNo);
        AccountcharityReferedByYouNo=view.findViewById(R.id.AccountcharityReferedByYouNo);
        totalnumberdonationsreferedbyyouNo = view.findViewById(R.id.totalnumberdonationsreferedbyyouNo);
        totalnumberdonationsreferedbyyou = view.findViewById(R.id.totalnumberdonationsreferedbyyou);
        getUserData(Hawk.get(Constants.USerID) + "");
        if (Constants.UserRole.equals("0")) {
            AccountType.setVisibility(View.VISIBLE);
        } else {
            AccountType.setVisibility(View.GONE);

        }
        return view;
    }

    private void getUserData(String UserID) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.getUserData(UserID).enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                pd.dismiss();
                UserDataModel userDataModel = response.body();
                if (userDataModel.getStatus()) {
                    Log.d("RESPONSE", "onResponse:" + userDataModel.getUser().getAccountType());
                    if (Hawk.get(Constants.UserRole).equals("0")) {
                        profileExtraData.setVisibility(View.VISIBLE);
                        AccountType.setVisibility(View.VISIBLE);
                        if ((CurrentLang).getLanguage().equals("ar")) {
                            AccountType.setText(userDataModel.getUser().getAccountType());
                        } else {
                            AccountType.setText(userDataModel.getUser().getAccount_type_en());
                        }
                        Noperfamily1.setText("" + userDataModel.getUser().getMydonationOneFamily());
                        NopermoreFamily1.setText("" + userDataModel.getUser().getMydonationMoreFamily());
                        ///
                        Noperfamily2.setText("" + userDataModel.getUser().getDonationOneFamily());
                        NumberCharityRefferedByYoyNo.setText(""+userDataModel.getUser().getDonationMoreFamily());
                        //
                        AccountbenefitesReferedByYouNo.setText(""+userDataModel.getUser().getBenifitPromo());
                        NopermoreFamily2.setText("" + userDataModel.getUser().getUserPromo());
                        AccountcharityReferedByYouNo.setText(""+userDataModel.getUser().getOrganizationPromo());
                        int x1=Integer.parseInt(userDataModel.getUser().getDonationMoreFamily());
                        int x2=Integer.parseInt(userDataModel.getUser().getDonationOneFamily());
                        totalnumberdonationsreferedbyyouNo.setText(""+(x1+x2));


                    } else {
                        Profile_logo.setVisibility(View.VISIBLE);
                        AccountType.setVisibility(View.GONE);
                        Noperfamily1.setVisibility(View.GONE);
                        NopermoreFamily1.setVisibility(View.GONE);
                        //Noperfamily2.setVisibility(View.GONE);

                        // NopermoreFamily2.setVisibility(View.GONE);
                        profileExtraData.setVisibility(View.GONE);
                        totalnumberdonationsreferedbyyouNo.setVisibility(View.GONE);
                        totalnumberdonationsreferedbyyou.setVisibility(View.GONE);
                        AccountbenefitesReferedByYouNo.setText(""+userDataModel.getUser().getBenifitPromo());
                        NopermoreFamily2.setText("" + userDataModel.getUser().getUserPromo());
                        AccountcharityReferedByYouNo.setText(""+userDataModel.getUser().getOrganizationPromo());
                        }


                } else {
                    Profile_logo.setVisibility(View.VISIBLE);
                    AccountType.setVisibility(View.GONE);
                    Noperfamily1.setVisibility(View.GONE);
                    NopermoreFamily1.setVisibility(View.GONE);
                    //Noperfamily2.setVisibility(View.GONE);
                    profileExtraData.setVisibility(View.GONE);

  //                  profileExtraData.setVisibility(View.VISIBLE);

                    //  NopermoreFamily2.setVisibility(View.GONE);
                    totalnumberdonationsreferedbyyouNo.setVisibility(View.GONE);
                    totalnumberdonationsreferedbyyou.setVisibility(View.GONE);
                    AccountbenefitesReferedByYouNo.setText(""+userDataModel.getUser().getBenifitPromo());
                    NopermoreFamily2.setText("" + userDataModel.getUser().getUserPromo());
                    AccountcharityReferedByYouNo.setText(""+userDataModel.getUser().getOrganizationPromo());


                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                AccountType.setVisibility(View.GONE);
                Noperfamily1.setVisibility(View.GONE);
                NopermoreFamily1.setVisibility(View.GONE);
                Noperfamily2.setVisibility(View.GONE);
                NopermoreFamily2.setVisibility(View.GONE);
                profileExtraData.setVisibility(View.GONE);

            }
        });
    }

}
