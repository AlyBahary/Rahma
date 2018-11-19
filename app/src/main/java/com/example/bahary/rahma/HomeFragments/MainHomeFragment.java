package com.example.bahary.rahma.HomeFragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahary.rahma.HomeActivity;
import com.example.bahary.rahma.Models.HomeItemModel;
import com.example.bahary.rahma.NavFragments.SettingFragment;
import com.example.bahary.rahma.R;
import com.example.bahary.rahma.RecyclerView.HomeItemsAdapter;
import com.example.bahary.rahma.Utils.Constants;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {

    private static final String TAG = "MainFrgamnt";
    private RecyclerView RV;
    private HomeItemsAdapter mItemsAdapter;
    private ArrayList<HomeItemModel> mhomeItemModels;
    private SliderLayout sliderLayout;
    ImageView menu, Share, Back;
    TextView Title;


    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        menu = getActivity().findViewById(R.id.toolbarMenu);
        Title = getActivity().findViewById(R.id.toolbarTitle);
        Share = getActivity().findViewById(R.id.toolbarshare);
        Back = getActivity().findViewById(R.id.toolbarback);
        Back.setVisibility(View.GONE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getFragmentManager().popBackStack();
                HomeActivity homeActivity=new HomeActivity();
                homeActivity.onBackPressed();

            }
        });
        Title.setText(getString(R.string.Main));

        mhomeItemModels = new ArrayList<>();
        mhomeItemModels.add(new HomeItemModel(0, getContext().getString(R.string.addDonation)));
        mhomeItemModels.add(new HomeItemModel(1, getContext().getString(R.string.addressList)));
        mhomeItemModels.add(new HomeItemModel(2, getContext().getString(R.string.Setting)));
        mhomeItemModels.add(new HomeItemModel(3, getContext().getString(R.string.donationsList)));
        Log.d(TAG, "onCreateView: " + mhomeItemModels.size() + mhomeItemModels.get(0).getImageView() + mhomeItemModels.get(0).getTextView());
        RV = view.findViewById(R.id.Main_home_RV);
        mItemsAdapter = new HomeItemsAdapter(mhomeItemModels, getContext(), new HomeItemsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                if (position == 0) {
                    addnewdonationFragment addnewdonationfragment = new addnewdonationFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, addnewdonationfragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();



                } else if (position == 1) {
                    AdressListFragmet adressListFragmet = new AdressListFragmet();
                    Bundle args = new Bundle();
                    args.putString(Constants.Budle_Fragment_From_My_Adress, "0");
                    adressListFragmet.setArguments(args);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, adressListFragmet);

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (position == 2) {
                    SettingFragment settingFragment = new SettingFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, settingFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (position == 3) {
                    DonationListFragment donationListFragment = new DonationListFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, donationListFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }
        });
        RV.setAdapter(mItemsAdapter);
        RV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mItemsAdapter.notifyDataSetChanged();
////setup Slider
        sliderLayout = view.findViewById(R.id.imageSliderHomeFragment);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        setSliderViews();

        return view;
    }


    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.photo);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.photo);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.photo);

                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.photo);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List <Fragment> fragments = getChildFragmentManager().getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
