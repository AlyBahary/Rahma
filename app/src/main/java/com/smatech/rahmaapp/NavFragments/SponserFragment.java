package com.smatech.rahmaapp.NavFragments;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.smatech.rahmaapp.Models.AllSlidersModel.AllSliderModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.smatech.rahmaapp.Utils.CustomSliderView;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SponserFragment extends Fragment {

    private SliderLayout sliderLayout;
    ImageView menu, Share, Back;
    TextView Title;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView, ImageTitle, ImageDescription;
    private com.daimajia.slider.library.SliderLayout mDemoSlider;
    String T;
    HashMap<String, String> url_maps;
    ArrayList<String> TitleArrayList;

    public SponserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sponser, container, false);
        TitleArrayList = new ArrayList<>();
        mDemoSlider = (com.daimajia.slider.library.SliderLayout) view.findViewById(R.id.slider);
        url_maps = new HashMap<String, String>();
        get_home_sliders();
        ImageTitle = view.findViewById(R.id.ImageTitle);
        ImageDescription = view.findViewById(R.id.ImageDescription);
        ImageDescription.setVisibility(View.GONE);


        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

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

        ////setup Slider


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
        Title.setText(getString(R.string.Sponser));


        return view;
    }

    private void get_home_sliders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.get_sponsors().enqueue(new Callback<AllSliderModel>() {
            @Override
            public void onResponse(Call<AllSliderModel> call, Response<AllSliderModel> response) {

                AllSliderModel allSliderModel = response.body();
                if (allSliderModel.getStatus()) {

                    for (int i = 0; i < allSliderModel.getSliders().size(); i++) {
                        T = allSliderModel.getSliders().get(i).getNameAr();
                        if (TitleArrayList.contains(T)) {
                            TitleArrayList.add(T + i);
                            T += i;
                        } else {
                            TitleArrayList.add(T);

                        }

                        url_maps.put("http://www.rahma-app.com/rahma/prod_img/" + allSliderModel.getSliders().get(i).getImage(),"");

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
                    mDemoSlider.setPresetTransformer(com.daimajia.slider.library.SliderLayout.Transformer.Default);
                    mDemoSlider.setPresetIndicator(com.daimajia.slider.library.SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                   // mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(3000);
                    mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            if (position > TitleArrayList.size()) {
                                int x = (position) % TitleArrayList.size();
                                ImageTitle.setText(TitleArrayList.get(x));
                            }
                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                    //setSliderViews(IMGs, Descs);

                }


            }

            @Override
            public void onFailure(Call<AllSliderModel> call, Throwable t) {

            }
        });

    }

}
