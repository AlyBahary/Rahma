package com.smatech.rahmaapp.HomeFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;

import com.smatech.rahmaapp.Dialoge.MyAdressDialogClass;
import com.smatech.rahmaapp.Map.MapFragment;
import com.smatech.rahmaapp.Map.MapsActivity;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel;
import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Models.GetImagesURLModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.smatech.rahmaapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationDescriptionFragment extends Fragment {

    TextView DonTitle, DonMore, DonLess, mScrollableTextView;
    CheckBox less, more;
    CheckBox button1, button2;
    EditText donationDescriptioneditText;
    ImageView menu, Share, Back;
    ImageView img_picker, upload_img;
    TextView Title;
    Button SaveDonation;
    String Description, Quantity, userID, category;
    LinearLayout scrolbarbgLayout, MyAdressList, addNewAdress, upload;
    MapFragment mMapFragment;
    TextView upload_txt;
    ProgressDialog pd;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> list, ids;
    MultipartBody.Part body;
    String type;
    ArrayList<String> EditableData;
    Spinner spinner2;

    public DonationDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_description, container, false);
        mMapFragment = new MapFragment();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        //


        ///
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(185);
        mScrollableTextView.getBackground().setAlpha(150);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR) + "");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN) + "");
        }
        ///
        img_picker = view.findViewById(R.id.img_picker);
        upload_img = view.findViewById(R.id.upload_img);
        upload_txt = view.findViewById(R.id.upload_txt);
        upload_img.setVisibility(View.INVISIBLE);
        upload_txt.setVisibility(View.VISIBLE);

        if (Hawk.contains(Constants.add_new_adress_ID)) {
            Hawk.delete(Constants.add_new_adress_ID);
        }

        upload = view.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Picked", "onClick");

                ImagePicker.create(getActivity())
                        .folderMode(true) // folder mode (false by default)
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                        .returnMode(ReturnMode.ALL) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                        .single() // single mode
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Rahma") // directory name for captured image  ("Camera" folder by default)
                        .start(); // start image picker activity with request code

            }

        });
        ///

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
        Title.setText(getString(R.string.DonationDescribtion));
///
        donationDescriptioneditText = view.findViewById(R.id.donationDescription);


        ///

        button1 = view.findViewById(R.id.checkboxMoreTen);
        button2 = view.findViewById(R.id.checkboxlessTen);
        button1.setChecked(true);
        Quantity = "1";
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setChecked(false);
                Quantity = "1";
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setChecked(false);
                Quantity = "0";
            }
        });
        type = getArguments().getString(Constants.Budle_Fragment_Donation_type);

        SaveDonation = view.findViewById(R.id.publish);

        if (Hawk.contains(Constants.DonationDetailsTOEdit)) {
            EditableData = new ArrayList<>();
            EditableData.addAll((Collection<? extends String>) Hawk.get(Constants.DonationDetailsTOEdit));
            Title.setText(getString(R.string.Edit));
            SaveDonation.setText(getString(R.string.Edit));
            if (EditableData.get(3).equals("0")) {
                type = "FOOD";
            } else {
                type = "D";

            }
            if (EditableData.get(0).equals("0")) {
                button1.setChecked(false);
                button2.setChecked(true);
                Quantity = "0";


            } else {
                button1.setChecked(true);
                button2.setChecked(false);
                Quantity = "1";

            }
            donationDescriptioneditText.setText(EditableData.get(4));
            if (EditableData.get(2).equals("") || EditableData.get(2) == null) {
            } else {
                Picasso.with(getContext()).load(EditableData.get(2)).fit().into(img_picker);
                img_picker.setVisibility(View.VISIBLE);
                upload_img.setVisibility(View.INVISIBLE);
                upload_txt.setVisibility(View.INVISIBLE);
            }
            Hawk.put(Constants.add_new_adress_ID, EditableData.get(7));


        }

        /////
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        ///
        DonTitle = view.findViewById(R.id.DonationTitle);
        DonMore = view.findViewById(R.id.Donationmore);
        DonLess = view.findViewById(R.id.Donationless);

        Log.d("TTT", "onCreateView: type -->" + type + "-------" + Hawk.contains(Constants.DonationDetailsTOEdit));

        if (!type.equals("FOOD"))

        {
            category = "1";
            DonTitle.setText(getString((R.string.AccesriesQuantityTxt)));
            DonMore.setText(getString(R.string.morethanthree));
            DonLess.setText(getString(R.string.lessthanthree));
        } else

        {
            category = "0";
        }
        ////

        if (Hawk.contains("Image_Path"))

        {
            upload_img.setVisibility(View.GONE);
            upload_txt.setVisibility(View.GONE);
            //img_picker.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
            Log.d("Picked", "onCreateView: " + Hawk.get("Image_Path"));

            Picasso.with(getContext()).load(Hawk.get("Image_Path") + "").fit().into(img_picker);
        }

        ///
        addNewAdress = view.findViewById(R.id.newLocation);
        addNewAdress.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
         spinner2 = (Spinner) view.findViewById(R.id.spinner);
        list = new ArrayList<String>();
        list.add(

                getString(R.string.SelectAdress));
        ids = new ArrayList<String>();
        ids.add("0000");
        //list.add("Select Adress");
        //spinner2.setPrompt(getString(R.string.SelectAdress));
        dataAdapter = new ArrayAdapter<String>(

                getContext(),

                android.R.layout.simple_spinner_item, list);

        getAllAdressesServices();
        dataAdapter.setDropDownViewResource(R.layout.test);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Hawk.delete(Constants.add_new_adress_ID);

                } else {
                    Hawk.put(Constants.add_new_adress_ID, ids.get(position));
                    //Toast.makeText(getContext(), "" + ids.get(position), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        MyAdressList = view.findViewById(R.id.locationList);
     /*   MyAdressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//* AdressListFragmet adressListFragmet = new AdressListFragmet();
                Bundle args = new Bundle();
                args.putString(Constants.Budle_Fragment_From_My_Adress, "1");
                adressListFragmet.setArguments(args);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, adressListFragmet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                MyAdressDialogClass cdd = new MyAdressDialogClass(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();*//*
            }
        });*/
        SaveDonation.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getContext());
                pd.setMessage(getString(R.string.loading));
                pd.show();
                userID = Hawk.get(Constants.USerID);
                Description = donationDescriptioneditText.getText().toString() + "";
                if (Hawk.contains(Constants.add_new_adress_ID)) {
                    Log.d("TTTT", "onClick: 1" + Hawk.get(Constants.add_new_adress_ID));
                    if (Hawk.contains("Image_Path")) {
                        String x = Hawk.get("Image_Path");
                        Log.d("Picked", "there is Path " + x);
                        UploadphotoService1(x);
                    } else {
                        Log.d("Picked", "there is No Path ");
                        if (Hawk.contains(Constants.DonationDetailsTOEdit)) {
                            Log.d("TTTT", "onClick: " + Hawk.get(Constants.add_new_adress_ID));
                            String imgname="";
                            if (EditableData.get(2).length() > 40){
                                 imgname = (EditableData.get(2) + "").substring(40, (EditableData.get(2) + "").length());
                            }
                            edit_donation(Hawk.get(Constants.add_new_adress_ID) + ""
                                    , userID + ""
                                    , Quantity + ""
                                    , "" + Description
                                    , "" + Description
                                    , imgname + ""
                                    , "" + EditableData.get(1)
                                    , category + "");

                        } else {
                            PublishDonation(Hawk.get(Constants.add_new_adress_ID) + ""
                                    , userID
                                    , Quantity
                                    , "0"
                                    , Description + " "
                                    , "0"
                                    , category + "");
                        }
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.ChooseAdrees), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                img_picker.setVisibility(View.GONE);
                upload_txt.setVisibility(View.VISIBLE);
            }
        });
        //

        ///
        return view;
    }

    private void PublishDonation(
            String address_id
            , String user_id
            , String type
            , String description
            , String descriptionDetail
            , String image
            , String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices
                .addDonation(address_id, user_id, type, description, descriptionDetail, image, category)
                .enqueue(new Callback<ForgetPassModel>() {
                    @Override
                    public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                        pd.dismiss();
                        ForgetPassModel forgetPassModel = response.body();
                        Log.d("Donation", "onResponse: " + response.toString());
                        Log.d("Donation", "onResponse: " + response.body().toString());
                        Log.d("Donation", "onResponse: " + forgetPassModel.getStatus());
                        Boolean x = forgetPassModel.getStatus();
                        if (x) {
                            View parentLayout = getActivity().findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "" + getString(R.string.published), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_green_light))
                                    .show();
                            getActivity().getFragmentManager().popBackStack();
                            getActivity().onBackPressed();

                        } else {
                            View parentLayout = getActivity().findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "" + getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                        pd.dismiss();
                        View parentLayout = getActivity().findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "" + getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();

                    }
                });
    }

    public void UploadphotoService1(String path) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        File file = new File(path);
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("parameters[0]", file.getName(), reqFile);

        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices
                .Upload_Img(body).enqueue(new Callback<GetImagesURLModel>() {
            @Override
            public void onResponse(Call<GetImagesURLModel> call, Response<GetImagesURLModel> response) {
                if (response.isSuccessful()) {
                    Log.d("Picked", "in " + Hawk.get("Image_Path"));

                    Hawk.delete("Image_Path");
                    if (response.body().getImages().size() > 0) {
                        Log.d("Picked", "in successful state");
                        if (Hawk.contains(Constants.DonationDetailsTOEdit)) {
                            edit_donation(Hawk.get(Constants.add_new_adress_ID) + ""
                                    , userID + ""
                                    , Quantity + ""
                                    , "" + Description
                                    , "" + Description
                                    , response.body().getImages().get(0) + ""
                                    , "" + EditableData.get(1)
                                    , category + "");
                        } else {
                            PublishDonation(Hawk.get(Constants.add_new_adress_ID) + ""
                                    , userID + ""
                                    , Quantity + ""
                                    , "0"
                                    , Description + " "
                                    , response.body().getImages().get(0) + ""
                                    , category + "");

                            Log.d("Picked", "onSuccess:Picked " + response.body().getImages().size());
                            Log.d("Picked", "onSuccess:Picked " + response.body().getImages().get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetImagesURLModel> call, Throwable t) {
                pd.dismiss();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            // or get a single image only
            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null) {
                img_picker.setVisibility(View.VISIBLE);
                upload_img.setVisibility(View.GONE);
                upload_txt.setVisibility(View.GONE);
                img_picker.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
                Hawk.put("Image_Path", image.getPath());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        if (Hawk.contains("Image_Path")) {
            img_picker.setImageBitmap(BitmapFactory.decodeFile(Hawk.get("Image_Path") + ""));

        }
        if(Hawk.contains(Constants.newaddressDesc)){
            Log.d("TTTT", "onResume:1 ");
            if(!Hawk.get(Constants.newaddressDesc).equals("")){
                Log.d("TTTT", "onResume:2 ");

                list.add(Hawk.get(Constants.newaddressDesc)+"");
                ids.add(Hawk.get(Constants.add_new_adress_ID)+"");
                dataAdapter.notifyDataSetChanged();
                spinner2.setSelection(list.size());
                Hawk.delete(Constants.newaddressDesc);


            }
        }
        super.onResume();
    }

    public void getAllAdressesServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);

        getRegistrationsConnectionServices
                .GetAllAdress(Hawk.get(Constants.USerID) + "").enqueue(new Callback<getAllAdressModel>() {
            @Override
            public void onResponse(Call<getAllAdressModel> call, Response<getAllAdressModel> response) {
                getAllAdressModel mgetAllAdressModel = response.body();
                Log.d("onResponse", "onResponse: " + response.body());
                Log.d("onResponse", "onResponse: " + response.toString());
                Log.d("onResponse", "onResponse: " + response.body().toString());

                Boolean x = mgetAllAdressModel.getStatus();
                if (x) {
                    ArrayList<getAllAdressModel1> Adress = mgetAllAdressModel.getAddresses();
                    if (Adress.size() > 0) {
                        for (int i = 0; i < Adress.size(); i++) {
                            list.add(Adress.get(i).getTitle());
                            ids.add(Adress.get(i).getId());

                        }
                        Log.d("TTT", "onResponse: " + list.size());
                        Log.d("TTT", "onResponse: " + ids.size());
                        dataAdapter.notifyDataSetChanged();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<getAllAdressModel> call, Throwable t) {

                Log.d("onResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void edit_donation(String address_id
            , String user_id
            , String type
            , String description
            , String description_det
            , String image
            , String id
            , String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices
                .edit_donation(address_id, user_id, type, description, description_det, image, id, category).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                pd.dismiss();
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {

                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.Updated), Snackbar.LENGTH_LONG)
                            .setAction("", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_green_light))
                            .show();
                    getActivity().getFragmentManager().popBackStack();
                    getActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                pd.dismiss();

            }
        });

    }
}
