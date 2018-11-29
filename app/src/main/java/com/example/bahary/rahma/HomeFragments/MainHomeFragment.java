package com.example.bahary.rahma.HomeFragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.esafirm.imagepicker.model.Image;
import com.example.bahary.rahma.HomeActivity;
import com.example.bahary.rahma.Models.AllSlidersModel.AllSliderModel;
import com.example.bahary.rahma.Models.GetImagesURLModel;
import com.example.bahary.rahma.Models.HomeItemModel;
import com.example.bahary.rahma.Models.UserData.UserDataModel;
import com.example.bahary.rahma.NavFragments.SettingFragment;
import com.example.bahary.rahma.R;
import com.example.bahary.rahma.RecyclerView.HomeItemsAdapter;
import com.example.bahary.rahma.Utils.Connectors;
import com.example.bahary.rahma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {

    private static final String TAG = "MainFrgamnt";
    private RecyclerView RV;
    private HomeItemsAdapter mItemsAdapter;
    private ArrayList<HomeItemModel> mhomeItemModels;
    // private SliderLayout sliderLayout;
    ImageView menu, Share, Back;
    TextView Title, Accounttype;
    private SliderLayout mDemoSlider;
    String T;
    HashMap<String, String> url_maps;

    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        //
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        url_maps = new HashMap<String, String>();


        //
        final BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelected(false);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;

            }
        });


        menu = getActivity().findViewById(R.id.toolbarMenu);
        Title = getActivity().findViewById(R.id.toolbarTitle);
        Share = getActivity().findViewById(R.id.toolbarshare);
        Back = getActivity().findViewById(R.id.toolbarback);
        Back.setVisibility(View.GONE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getFragmentManager().popBackStack();
                HomeActivity homeActivity = new HomeActivity();
                homeActivity.onBackPressed();

            }
        });
        Title.setText(getString(R.string.Main));

        get_home_sliders();
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
      /*  sliderLayout = view.findViewById(R.id.imageSliderHomeFragment);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
*/
        return view;
    }


   /* private void setSliderViews(ArrayList<String> sliderViewsArraylist, ArrayList<String> SliderDesc) {

        for (int i = 0; i < sliderViewsArraylist.size(); i++) {

            SliderView sliderView = new SliderView(getContext());
            if (i == i) {
                sliderView.setImageUrl(sliderViewsArraylist.get(i).replaceFirst("s", ""));
                sliderView.setDescription(SliderDesc.get(i));

            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);


            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);

        }
    }
*/

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

    private void get_home_sliders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.get_home_sliders().enqueue(new Callback<AllSliderModel>() {
            @Override
            public void onResponse(Call<AllSliderModel> call, Response<AllSliderModel> response) {

                AllSliderModel allSliderModel = response.body();
                if (allSliderModel.getStatus()) {
                    ArrayList<String> Title = new ArrayList<>();
                    for (int i = 0; i < allSliderModel.getSliders().size(); i++) {
                        T = allSliderModel.getSliders().get(i).getNameAr();
                       /* if (Title.contains(T)) {
                            Title.add(T+i);
                            T+=i;
                            }else{
                            Title.add(T);

                        }*/

                        url_maps.put( "http://www.rahma-app.com/rahma/prod_img/" + allSliderModel.getSliders().get(i).getImage(),T);

                    }
                    for (String name : url_maps.keySet()) {
                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout
                        textSliderView
                                .description(url_maps.get(name))
                                .image(name)
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name);

                        mDemoSlider.addSlider(textSliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(3000);


                    //setSliderViews(IMGs, Descs);

                }


            }

            @Override
            public void onFailure(Call<AllSliderModel> call, Throwable t) {

            }
        });

    }
}
