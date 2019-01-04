package com.smatech.rahmaapp.HomeFragments;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;
import com.smatech.rahmaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReciveDonationFragment extends Fragment {

    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;
    LinearLayout mCardView;
    Button Recived, Not_yet;
    ImageView menu, Share,Back;
    TextView Title;


    public ReciveDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_recive_donation, container, false);
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

        mCardView.getBackground().setAlpha(50);
        Recived = view.findViewById(R.id.Fragmet_Button_Recived);
        Not_yet = view.findViewById(R.id.Fragmet_Button_Not_Yet);
        Recived.getBackground().setAlpha(70);
        Not_yet.getBackground().setAlpha(70);
        menu = getActivity().findViewById(R.id.toolbarMenu);
        Title = getActivity().findViewById(R.id.toolbarTitle);
        Share = getActivity().findViewById(R.id.toolbarshare);
        Back = getActivity().findViewById(R.id.toolbarback); Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
                getActivity().onBackPressed();


            }
        });
        Title.setText(getString(R.string.ReciveDonation));


        return view;
    }

}
