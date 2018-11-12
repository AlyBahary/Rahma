package com.example.bahary.rahma.HomeFragments;


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
import android.widget.Toast;

import com.example.bahary.rahma.HomeActivity;
import com.example.bahary.rahma.Models.HomeItemModel;
import com.example.bahary.rahma.NavFragments.SettingFragment;
import com.example.bahary.rahma.R;
import com.example.bahary.rahma.RecyclerView.HomeItemsAdapter;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {

    private static final String TAG = "MainFrgamnt";
    private RecyclerView RV;
    private HomeItemsAdapter mItemsAdapter;
    private ArrayList<HomeItemModel> mhomeItemModels;
    private SliderLayout sliderLayout;
    OnSearchBarHomeClicked mOnSearchBarHomeClicked;


    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main_home, container, false);
        mhomeItemModels=new ArrayList<>();
        mhomeItemModels.add(new HomeItemModel(R.drawable.new_donation,getContext().getString(R.string.addDonation)));
        mhomeItemModels.add(new HomeItemModel(R.drawable.address_list,getContext().getString(R.string.addressList)));
        mhomeItemModels.add(new HomeItemModel(R.drawable.setting,getContext().getString(R.string.Setting)));
        mhomeItemModels.add(new HomeItemModel(R.drawable.list_of_donation,getContext().getString(R.string.donationsList)));
        Log.d(TAG, "onCreateView: "+mhomeItemModels.size()+mhomeItemModels.get(0).getImageView()+mhomeItemModels.get(0).getTextView());
        RV=view.findViewById(R.id.Main_home_RV);
        mItemsAdapter=new HomeItemsAdapter(mhomeItemModels, getContext(), new HomeItemsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                if(position==0){
                    mOnSearchBarHomeClicked.setOnSearchBarHomeClicked(0);


                }


                else if(position==2){
                    SettingFragment settingFragment=new SettingFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, settingFragment);
                    fragmentTransaction.commit();

                }

            }
        });
        RV.setAdapter(mItemsAdapter);
        RV.setLayoutManager(new GridLayoutManager(getContext(),2));
        mItemsAdapter.notifyDataSetChanged();
////setup Slider
        sliderLayout = view.findViewById(R.id.imageSliderHomeFragment);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        setSliderViews();

        return view ;
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
    public interface OnSearchBarHomeClicked {
        void setOnSearchBarHomeClicked(int type);
    }

    public void setOnSearchBarHomeClicked(OnSearchBarHomeClicked mOnSearchBarHomeClicked) {
        this.mOnSearchBarHomeClicked = mOnSearchBarHomeClicked;
    }
}
