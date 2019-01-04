package com.smatech.rahmaapp.Map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.smatech.rahmaapp.Dialoge.DonationDescriptionDialog;
import com.smatech.rahmaapp.HomeFragments.Social_Fragment;
import com.smatech.rahmaapp.Dialoge.DonationDescriptionDialog;
import com.smatech.rahmaapp.Dialoge.LocationDetailsDialoge;
import com.smatech.rahmaapp.HomeFragments.Social_Fragment;
import com.smatech.rahmaapp.Models.AllSlidersModel.AllSliderModel;
import com.smatech.rahmaapp.Models.Donation.DonationItmemModel;
import com.smatech.rahmaapp.Models.Donation.DonationModel;
import com.smatech.rahmaapp.NavFragments.SettingFragment;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.smatech.rahmaapp.Utils.CustomSliderView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.smatech.rahmaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private SliderLayout mDemoSlider;
    String T;
    ArrayList<Double> list;
    public View view;
    MapView mMapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ImageView menu, Share, Back;
    TextView Title;
    private AutoCompleteTextView mSearchText;
    View Removable;
    HashMap<String, String> url_maps;
    LinearLayout layout, layout1;
    Double offSet = 0.001;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();
    private HashMap<Integer, DonationItmemModel> DonationHashMap = new HashMap<Integer, DonationItmemModel>();
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private PlaceAutocompleteFragment placeAutocompleteFragment;
    Marker marker, MarkerGreen;
    ProgressDialog pd;

    SettingFragment settingFragment;
    Social_Fragment social_Fragment;
    MapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.LoadDonations));

        menu = getActivity().findViewById(R.id.toolbarMenu);
        Title = getActivity().findViewById(R.id.toolbarTitle);
        mSearchText = getActivity().findViewById(R.id.input_search);

        settingFragment = new SettingFragment();
        social_Fragment = new Social_Fragment();
        mapFragment = this;
        Removable = getActivity().findViewById(R.id.removableView);
        final BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        bottomNavigationView.setSelected(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                SettingFragment myFragment = (SettingFragment) getActivity().getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                if (menuItem.getItemId() == R.id.navigation_home) {
                    //bottomNavigationView.getMenu().getItem(2).setChecked(true);
                    fragmentTransaction.replace(R.id.Frgment_Container, social_Fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (menuItem.getItemId() == R.id.navigation_settings) {

                    if (myFragment != null && myFragment.isVisible()) {
                        // add your code here
                    } else {
                        fragmentTransaction.replace(R.id.Frgment_Container, settingFragment, "MY_FRAGMENT");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);

                } else if (menuItem.getItemId() == R.id.navigation_Home) {

                    fragmentTransaction.replace(R.id.Frgment_Container, mapFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (menuItem.getItemId() == R.id.navigation_reques) {
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
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.smatech.net.rahma&fbclid=IwAR1iYmo57Kt0B5Wy4hI38MsOmosM0PirNVAARACjvg0q7_VdUhi2SH0JUnA")));
                    }

                }
                return true;
            }

        });


        if (view == null) {

            url_maps = new HashMap<String, String>();
            get_home_sliders();


            view = inflater.inflate(R.layout.fragment_map, container, false);
            placeAutocompleteFragment = (PlaceAutocompleteFragment) getActivity()
                    .getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            placeAutocompleteFragment.getView().setBackgroundResource(R.drawable.searchbar_bg);
            mSearchText.setVisibility(View.GONE);
            mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);


            Share = getActivity().findViewById(R.id.toolbarshare);
            Back = getActivity().findViewById(R.id.toolbarback);
            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getFragmentManager().popBackStack();

                }
            });


            Log.d("NN", "onComplete: task : ");

            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            String rationale = "Please provide location permission to let us Help you.";
            Permissions.Options options = new Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning");
            Permissions.check(getContext()/*context*/, permissions, rationale, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                    mFusedLocationProviderClient = LocationServices
                            .getFusedLocationProviderClient(getContext());
                    mMapView = (MapView) view.findViewById(R.id.mapView);
                    mMapView.onCreate(savedInstanceState);
                    mMapView.onResume(); // needed to get the map to display immediately
                    try {
                        MapsInitializer.initialize(getActivity().getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            googleMap = mMap;


                            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    Integer id = mHashMap.get(marker);
                                    Log.d("FFFF", "onMarkerClick: " + id);
                                    if (id != null) {
                                        DonationItmemModel donationItmemModel = DonationHashMap.get(id);
                                        Log.d("FFFF", "onMarkerClick: " + donationItmemModel.getId());
                                        DonationDescriptionDialog cdd = new DonationDescriptionDialog(getActivity(), donationItmemModel);
                                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        cdd.show();


                                    }
                                    return false;
                                }
                            });
                            //Auto Complete Location
                            getDonationListConnection(Hawk.get(Constants.USerID) + "", "", "", "", Hawk.get(Constants.UserRole) + "", Hawk.get(Constants.UserType) + "");
                            placeAutocompleteFragment.setFilter(new AutocompleteFilter.Builder().setCountry("EG").build());
                            placeAutocompleteFragment.setBoundsBias(LAT_LNG_BOUNDS);
                            placeAutocompleteFragment.setHint(getString(R.string.enterPlace));
                            placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                                @Override
                                public void onPlaceSelected(Place place) {
                                    final LatLng latLngLoc = place.getLatLng();

                                    if (marker != null) {
                                        marker.remove();
                                    }
                                    marker = googleMap.addMarker(new MarkerOptions().position(latLngLoc).title(place.getName().toString()));
                                    CameraPosition cameraPosition1 = new CameraPosition.Builder().target(latLngLoc).zoom(12).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
                                    marker.showInfoWindow();

                                }

                                @Override
                                public void onError(Status status) {
                                    Toast.makeText(getContext(), "" + status.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            ///

                            // For showing a move to my location button
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            googleMap.setMyLocationEnabled(true);
                            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                                @Override
                                public boolean onMyLocationButtonClick() {
                                    getCurrentLocation();
                                    return true;
                                }
                            });
                            getCurrentLocation();


                            // For dropping a marker at a point on the Map
                            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                            // For zooming automatically to the location of the marker

                        }

                    });

                    mGoogleApiClient = new GoogleApiClient
                            .Builder(getContext())
                            .addApi(Places.GEO_DATA_API)
                            .addApi(Places.PLACE_DETECTION_API)
                            .enableAutoManage((FragmentActivity) getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                                @Override
                                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                    // your code here
                                    Toast.makeText(getContext(), getString(R.string.CLOSE), Toast.LENGTH_SHORT).show();

                                }
                            }).build();

                    mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(), mGoogleApiClient,
                            LAT_LNG_BOUNDS, null);
                    mSearchText.setAdapter(mPlaceAutocompleteAdapter);

                    mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH
                                    || actionId == EditorInfo.IME_ACTION_DONE
                                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                                    || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                                //execute our method for searching
                            }

                            return false;
                        }
                    });


                }


                private void moveCamera(LatLng latLng, float zoom, String title) {
                    Log.d("TAGLO", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

                    if (!title.equals("My Location")) {
                        MarkerOptions options = new MarkerOptions()
                                .position(latLng)
                                .title(title);
                        googleMap.addMarker(options);
                    }

                }


                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                    // permission denied, block the feature.
                }
            });


        } else {
            getDonationListConnection(Hawk.get(Constants.USerID) + "", "", "", "", Hawk.get(Constants.UserRole) + "", Hawk.get(Constants.UserType) + "");

        }

        Back.setVisibility(View.GONE);
        Title.setText(getString(R.string.Map));
        return view;
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() == null) {
                        Toast.makeText(getContext(), "Please turn on device location ", Toast.LENGTH_SHORT).show();
                    } else {
                        // Set the map's camera position to the current location of the device.
                        Location location = task.getResult();
                        Log.d("NN", "onComplete: task : " + task.getResult());

                    /*    if (marker != null) {
                            marker.remove();
                        }*/
                        LatLng currentLatLng = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        //marker = googleMap.addMarker(new MarkerOptions().position(currentLatLng).title(getString(R.string.CurrentLocatio)));
                        //marker.showInfoWindow();
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                                15f);
                        Log.d("NN", "onComplete: camera upadte" + update.toString() + location.getLatitude() + " -- " + location.getLongitude());
                        googleMap.moveCamera(update);
                    }

                }
                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        getCurrentLocation();
                        return true;
                    }
                });
            }
        });

    }

    ///// Get All Donation With all Adresses
    public void getDonationListConnection(String UserID, String CityID, String Longm, String lat, String role, String type) {
        pd.show();
        String Type;
        if (Hawk.get(Constants.UserRole).equals("1")){
            Type="0";
        }else{
            Type="1";
        }
        Log.d("TTTT", "getDonationListConnection: Refresh "+Type);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);

        getRegistrationsConnectionServices
                .getDonations("",Type).enqueue(new Callback<DonationModel>() {
            @Override
            public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
                pd.dismiss();
                list = new ArrayList<Double>();

                DonationModel donationModel = response.body();
                if (donationModel.getCount() != null) {
                    if (donationModel.getStatus()) {
                        ArrayList<DonationItmemModel> itmemModels = donationModel.getDonations();
                        // Log.d("Donationss", "onResponse: " + DonationListFragment.this.donationItemModes.size());
                        for (int i = 0; i < itmemModels.size(); i++) {
//                    Log.d("ONRESPONSE", "onResponse: " + itmemModels.size());
                            if ((itmemModels.get(i).getAddress().getId()) == null
                                    || (itmemModels.get(i).getAddress().getId()).equals("")) {
                            } else {

                                Double lat = Double.parseDouble(itmemModels.get(i).getAddress().getLatitude());
                                Double lang = Double.parseDouble(itmemModels.get(i).getAddress().getLongitude());
                                Integer ID = Integer.parseInt(itmemModels.get(i).getId());

                                if (list.contains(lat)) {
                                    //Log.d("ONRESPONSE", "onResponse: found " + list.size());
                                    offSet = offSet + .000015;
                                    lat = lat + offSet;
                                }
                                Log.d("ONRESPONSE", "List Size " + list.size());
                                list.add(lat);

                                LatLng currentLatLng = new LatLng(lat, lang);
                                MarkerGreen = googleMap.addMarker(new MarkerOptions().position(currentLatLng)
                                        .icon(BitmapDescriptorFactory
                                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                              //  MarkerGreen.setTitle("");
                                mHashMap.put(MarkerGreen, ID);
                                DonationHashMap.put(ID, new DonationItmemModel(itmemModels.get(i).getId() + ""
                                        , itmemModels.get(i).getDescription() + ""
                                        , "" + itmemModels.get(i).getFromId()
                                        , "" + itmemModels.get(i).getType()
                                        , "" + itmemModels.get(i).getImage()
                                        , ""
                                        , ""
                                        , ""
                                        , "" + itmemModels.get(i).getCategory()
                                        , ""
                                        , ""
                                        , ""
                                        , itmemModels.get(i).getAddress()));


                            }
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<DonationModel> call, Throwable t) {

            }
        });
    }


    private void get_home_sliders() {
        if (url_maps.size() > 1) {
        } else {
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
                       /* if (Title.contains(T)) {
                            Title.add(T+i);
                            T+=i;
                            }else{
                            Title.add(T);

                        }*/

                            T = allSliderModel.getSliders().get(i).getNameAr();
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

}
