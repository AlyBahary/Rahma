package com.smatech.rahmaapp.HomeFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class addnewdonationFragment extends Fragment {

    TextView mScrollableTextView;
    LinearLayout Food, Accesries;
    ImageView menu, Share, Back;
    TextView Title;
    //LinearLayout scrolbarbgLayout;

    public addnewdonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addnewdonation, container, false);

        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        //       scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        //       scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
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
        Title.setText(getString(R.string.addDonation));

        Food = view.findViewById(R.id.FoodDonation);
        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.smatech.rahmaapp.HomeFragments.DonationDescriptionFragment donationDescriptionFragment = new DonationDescriptionFragment();
                Bundle args = new Bundle();
                args.putString(Constants.Budle_Fragment_Donation_type, "FOOD");
                donationDescriptionFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, donationDescriptionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Accesries = view.findViewById(R.id.AccesroiesDonatation);
        Accesries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.smatech.rahmaapp.HomeFragments.DonationDescriptionFragment donationDescriptionFragment = new DonationDescriptionFragment();
                Bundle args = new Bundle();
                args.putString(Constants.Budle_Fragment_Donation_type, "ACCESRIES");
                donationDescriptionFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, donationDescriptionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }


    }
}
