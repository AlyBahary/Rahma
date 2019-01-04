package com.smatech.rahmaapp.HomeFragments;


import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.smatech.rahmaapp.HomeActivity;
import com.smatech.rahmaapp.Models.AllSlidersModel.AllSliderModel;
import com.smatech.rahmaapp.Models.HomeItemModel;
import com.smatech.rahmaapp.NavFragments.SettingFragment;
import com.smatech.rahmaapp.Organization.Organiztion_Main_fragment;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.RecyclerView.HomeItemsAdapter;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.smatech.rahmaapp.Utils.CustomSliderView;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.smatech.rahmaapp.R;

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
    String type;
    MainHomeFragment mainHomeFragment;
    Organiztion_Main_fragment organiztion_main_fragment;
    SettingFragment settingFragment;
    Social_Fragment social_Fragment;


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
        settingFragment = new SettingFragment();
        social_Fragment =new Social_Fragment();
        mainHomeFragment = new MainHomeFragment();
        organiztion_main_fragment = new Organiztion_Main_fragment();
        type = Hawk.get(Constants.UserRole);

        //
        final BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                SettingFragment myFragment = (SettingFragment) getActivity().getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");

                if (menuItem.getItemId() == R.id.navigation_home) {
                    //bottomNavigationView.getMenu().getItem(2).setChecked(true);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, social_Fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (menuItem.getItemId() == R.id.navigation_settings) {

                    if (myFragment != null && myFragment.isVisible()) {
                        // add your code here
                    }else {
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Frgment_Container, settingFragment, "MY_FRAGMENT");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);

                } else if (menuItem.getItemId() == R.id.navigation_Home) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                    if (type.equals(Constants.Donor)) {
                        fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
                        fragmentTransaction.commit();
                    } else {
                        fragmentTransaction.replace(R.id.Frgment_Container, organiztion_main_fragment);
                        fragmentTransaction.commit();

                    }


                } else if (menuItem.getItemId() == R.id.navigation_reques) {
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
          /*          Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Signup by promo codePromoCode :" + Hawk.get(Constants.UserPromoCode) + " " + "https://play.google.com/store/apps/details?id=com.smatech.net.rahma&hl=en");
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                    }*/
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Rahma");
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.inviationtxt1)
                                    +"https://play.google.com/store/apps/details?id=com.smatech.rahmaapp"+"\n"+getString(R.string.inviationtxt2)+
                             Hawk.get(Constants.UserPromoCode));
                    startActivity(Intent.createChooser(intent, "choose one"));



                } else if (menuItem.getItemId() == R.id.navigation_request) {
                    final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.smatech.rahmaapp")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.smatech.rahmaapp")));
                    }

                }
                return true;
            }

        });

///////////////////
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

                        url_maps.put("http://www.rahma-app.com/rahma/prod_img/" + allSliderModel.getSliders().get(i).getImage(), T);

                    }
                    for (String name : url_maps.keySet()) {
                        CustomSliderView textSliderView = new CustomSliderView(getContext());
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
