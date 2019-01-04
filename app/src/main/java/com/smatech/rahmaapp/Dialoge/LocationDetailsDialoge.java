package com.smatech.rahmaapp.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Models.NewAdress.AddNewAdress;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationDetailsDialoge extends Dialog {
    public Activity c;
    public Dialog d;

    EditText titleEditText, homeEditText, floorEditText;
    EditText block_noEditText, floor_noEditText, house_noEditText;
    String title, block_no, floor_no, house_no, Quantity;
    Button Add, CancellocationhereButton;
    CheckBox button1, button2, button3;
    ProgressDialog pd;
    Spinner locationtype;
    LinearLayout Type_Home, Type_Block, Type_Organiztion;
    getAllAdressModel1 getAllAdressModel1;

    public LocationDetailsDialoge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public LocationDetailsDialoge(Activity a, getAllAdressModel1 getAllAdressModel1) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.getAllAdressModel1 = getAllAdressModel1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.location_details_dialoge);
        Type_Home = findViewById(R.id.Type_Home);
        Type_Block = findViewById(R.id.Type_Block);
        Type_Organiztion = findViewById(R.id.Type_Organziation);
        locationtype = findViewById(R.id.locationtype);

        locationtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if ((parent.getItemAtPosition(position)).equals(getContext().getString(R.string.Home))) {
                    Quantity = "0";
                    Type_Home.setVisibility(View.VISIBLE);
                    Type_Block.setVisibility(View.GONE);
                    Type_Organiztion.setVisibility(View.GONE);
                } else if ((parent.getItemAtPosition(position)).equals(getContext().getString(R.string.Block))) {
                    Quantity = "1";
                    Type_Home.setVisibility(View.GONE);
                    Type_Block.setVisibility(View.VISIBLE);
                    Type_Organiztion.setVisibility(View.GONE);
                } else  {
                    Quantity = "2";
                    Type_Home.setVisibility(View.GONE);
                    Type_Block.setVisibility(View.GONE);
                    Type_Organiztion.setVisibility(View.VISIBLE);
                    Log.d("TTTT", "onItemSelected: "+Quantity);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Quantity = "0";
            }
        });

        Add = findViewById(R.id.AddlocationhereButton);

        if (getAllAdressModel1 != null) {

            Add.setText(getContext().getString(R.string.Edit));
            if (getAllAdressModel1.getType().equals("0")) {
                locationtype.setPrompt(getContext().getString(R.string.Home));
                locationtype.setSelection(0);
                Quantity = "0";
                Type_Home.setVisibility(View.VISIBLE);
                Type_Block.setVisibility(View.GONE);
                Type_Organiztion.setVisibility(View.GONE);
                EditText DESEditText = findViewById(R.id.Wtritelocationhere);
                DESEditText.setText(getAllAdressModel1.getTitle());
                //
                EditText HouseNoEditText = findViewById(R.id.homeNumber);
                HouseNoEditText.setText(getAllAdressModel1.getHouseNo());
                //
                EditText ContactEditText = findViewById(R.id.Contactno1);
                ContactEditText.setText(getAllAdressModel1.getMobile());

            } else if (getAllAdressModel1.getType().equals("1")) {
                locationtype.setPrompt(getContext().getString(R.string.Block));
                locationtype.setSelection(1);
                Quantity = "1";
                Type_Home.setVisibility(View.GONE);
                Type_Block.setVisibility(View.VISIBLE);
                Type_Organiztion.setVisibility(View.GONE);

                EditText DESEditText = findViewById(R.id.BlocklocationDesc);
                DESEditText.setText(getAllAdressModel1.getTitle());
                //
                EditText BLOCKEditText = findViewById(R.id.BlockNohere);
                BLOCKEditText.setText(getAllAdressModel1.getBlockNo());
                //
                EditText PartmentNoEditText = findViewById(R.id.PartmentNo);
                PartmentNoEditText.setText(getAllAdressModel1.getHouseNo());
                //
                EditText FLOORNOEditText = findViewById(R.id.FloorNumber);
                FLOORNOEditText.setText(getAllAdressModel1.getFloorNo());
                //
                EditText ContactEditText = findViewById(R.id.Contactno3);
                ContactEditText.setText(getAllAdressModel1.getMobile());
            } else {
                locationtype.setSelection(2);
                Quantity = "2";
                Type_Home.setVisibility(View.GONE);
                Type_Block.setVisibility(View.GONE);
                Type_Organiztion.setVisibility(View.VISIBLE);
                EditText DESEditText = findViewById(R.id.Wtritelocationhere2);
                DESEditText.setText(getAllAdressModel1.getTitle());

                //
                EditText OrganisationName = findViewById(R.id.WtriteOrganizationName);
                OrganisationName.setText(getAllAdressModel1.getOrganization());
                //
                EditText ContactEditText = findViewById(R.id.OrganizationNo);
                ContactEditText.setText(getAllAdressModel1.getMobile());

            }
        }


        //-----------------------------------------------------------//

        //-----------------------------------------------------------------//
        CancellocationhereButton = findViewById(R.id.CancellocationhereButton);
        CancellocationhereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd = new ProgressDialog(getContext());
                    pd.setMessage(c.getString(R.string.loading));

                    pd.show();
                    Log.d("TTTT", "onItemSelected: "+Quantity);

                    /////////
                    if (Quantity.equals("0")) {


                        EditText DESEditText = findViewById(R.id.Wtritelocationhere);
                        String Des = DESEditText.getText().toString();
                        //
                        EditText HouseNoEditText = findViewById(R.id.homeNumber);
                        String house = HouseNoEditText.getText().toString();
                        //
                        EditText ContactEditText = findViewById(R.id.Contactno1);
                        String Contact = ContactEditText.getText().toString();

                        Log.d("TTTT", "onClick: " + Des + "----" + house + "----" + Contact);
                        //
                        if (Des.equals("") || Des.equals(null)) {
                            Toast.makeText(c, "Please fill Describtion", Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        } else if (house.equals("") || house.equals(null)) {
                            Toast.makeText(c, "Please fill House Number", Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        } else {
                            if (getAllAdressModel1 != null) {

                                editAdreesService(""+Des
                                        , Hawk.get(Constants.UserCityID) + ""
                                        , Hawk.get(Constants.Addlocationdlong) + ""
                                        , Hawk.get(Constants.Addlocationdlat) + ""
                                        , Des + ""
                                        , Quantity, "", "", "" + house
                                        , Hawk.get(Constants.USerID) + "", ""+Des, Contact, getAllAdressModel1.getId()
                                        ,"");
                                //Update Adress
                            } else {
                                addNewAdreesService(""+Des
                                        , Hawk.get(Constants.UserCityID) + ""
                                        , Hawk.get(Constants.Addlocationdlong) + ""
                                        , Hawk.get(Constants.Addlocationdlat) + ""
                                        , Des + ""
                                        , Quantity
                                        , "", "", "" + house
                                        , Hawk.get(Constants.USerID) + ""
                                        , ""+Des
                                        , Contact,"");
                            }
                        }

                    } else if (Quantity.equals("1")) {
                        EditText DESEditText = findViewById(R.id.BlocklocationDesc);
                        String Des = DESEditText.getText().toString();
                        //
                        EditText BLOCKEditText = findViewById(R.id.BlockNohere);
                        String BlockNo = BLOCKEditText.getText().toString();

                        //
                        EditText PartmentNoEditText = findViewById(R.id.PartmentNo);
                        String PartmentNo = PartmentNoEditText.getText().toString();
                        //
                        EditText FLOORNOEditText = findViewById(R.id.FloorNumber);
                        String floorNO = FLOORNOEditText.getText().toString();
                        //
                        EditText ContactEditText = findViewById(R.id.Contactno3);
                        String Contact = ContactEditText.getText().toString();
                        if (Des.equals("") || Des.equals(null)) {
                            Toast.makeText(c, "Please fill Describtion", Toast.LENGTH_SHORT).show();

                            pd.dismiss();
                        } else if (BlockNo.equals("") || BlockNo.equals(null)) {
                            Toast.makeText(c, "Please fill Block number", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        } else if (PartmentNo.equals("") || PartmentNo.equals(null)) {
                            Toast.makeText(c, "Please fill Partment Number", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        } else if (floorNO.equals("") || floorNO.equals(null)) {
                            Toast.makeText(c, "Please fill Floor Number", Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        } else {
                            if (getAllAdressModel1 != null) {
                                //Update Adress
                                editAdreesService(""+Des
                                        , Hawk.get(Constants.UserCityID) + ""
                                        , Hawk.get(Constants.Addlocationdlong) + ""
                                        , Hawk.get(Constants.Addlocationdlat) + ""
                                        , Des + ""
                                        , Quantity, "" + BlockNo, floorNO + "", "" + PartmentNo
                                        , Hawk.get(Constants.USerID) + "", ""+Des, Contact, getAllAdressModel1.getId(),"");
                            } else {

                                addNewAdreesService(""+Des
                                        , Hawk.get(Constants.UserCityID) + ""
                                        , Hawk.get(Constants.Addlocationdlong) + ""
                                        , Hawk.get(Constants.Addlocationdlat) + ""
                                        , Des + ""
                                        , Quantity, "" + BlockNo, floorNO + "", "" + PartmentNo
                                        , Hawk.get(Constants.USerID) + "", ""+Des, Contact,"");
                            }
                        }
                    } else {
                        EditText DESEditText = findViewById(R.id.Wtritelocationhere2);
                        String Des = DESEditText.getText().toString();
                        //
                        EditText OrganisationName = findViewById(R.id.WtriteOrganizationName);
                        String Organisation = OrganisationName.getText().toString();
                        //
                        EditText ContactEditText = findViewById(R.id.OrganizationNo);
                        String Contact = ContactEditText.getText().toString();

                        //
                        if (Des.equals("") || Des.equals(null)) {
                            Toast.makeText(c, "Please fill Describtion", Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        } else if (Organisation.equals("") || Organisation.equals(null)) {
                            Toast.makeText(c, "Please fill Organisation Name", Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        } else {
                            if (getAllAdressModel1 != null) {
                                //Update Adress
                                editAdreesService(""+Des
                                        , Hawk.get(Constants.UserCityID) + ""
                                        , Hawk.get(Constants.Addlocationdlong) + ""
                                        , Hawk.get(Constants.Addlocationdlat) + ""
                                        , Des + ""
                                        , Quantity+"", "", "", "" + Organisation
                                        , Hawk.get(Constants.USerID) + "", ""+Des, Contact, getAllAdressModel1.getId()
                                        ,Organisation);
                            } else {
                                addNewAdreesService(""+Des
                                        , Hawk.get(Constants.UserCityID) + ""
                                        , Hawk.get(Constants.Addlocationdlong) + ""
                                        , Hawk.get(Constants.Addlocationdlat) + ""
                                        , Des + ""
                                        , Quantity, "", "", "" + Organisation
                                        , Hawk.get(Constants.USerID) + ""
                                        , ""+Des, Contact,Organisation);

                            }
                        }
                    }
              /*  addNewAdreesService(title
                        , Hawk.get(Constants.UserCityID) + ""
                        , Hawk.get(Constants.Addlocationdlong) + ""
                        , Hawk.get(Constants.Addlocationdlat) + ""
                        , "Description"
                        , Quantity, home, floor, home
                        , Hawk.get(Constants.USerID) + "", title);
*/
                }
            });
        }


    public void addNewAdreesService(String Adress
            , String CityID
            , String longtud
            , String latitude
            , String description
            , String type
            , String block_no
            , String floor_no
            , String house_no
            , String userID
            , final String Title
            , String mobile
            , String organization) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.AddNewAddress(
                Adress
                , CityID
                , longtud
                , latitude
                , description
                , type
                , block_no
                , floor_no
                , house_no
                , userID
                , Title
                , mobile
        ,organization).enqueue(new Callback<AddNewAdress>() {
            @Override
            public void onResponse(Call<AddNewAdress> call, Response<AddNewAdress> response) {
                pd.dismiss();

                AddNewAdress addNewAddressModel = response.body();
                Log.d("ONRESPONSE", "onResponse: " + response.toString());
                Log.d("ONRESPONSE", "onResponse:======= " + response.body().getStatus());
                Log.d("ONRESPONSE", "onResponse: " + addNewAddressModel.getStatus());
                Log.d("ONRESPONSE", "onResponse:-------- ");
                Boolean x = addNewAddressModel.getStatus();

                if (x) {
                    Log.d("Add Adress ID", "onResponse: " + response.toString());
                    Log.d("Add Adress ID", "onResponse: " + response.body());
                    Log.d("Add Adress ID", "onResponse: " + addNewAddressModel.getAddress().getId());
                    Hawk.put(Constants.add_new_adress_ID, addNewAddressModel.getAddress().getId() + "");
                    Hawk.put(Constants.newaddressDesc, Title);
                    Log.d("TTTT", "onResume:00 "+Title);

                    dismiss();
                    c.finish();
                     Hawk.delete(Constants.Addlocationdlong);
                     Hawk.delete(Constants.Addlocationdlat);
                } else {

                    Log.d("ONRESPONSE", "on NOt TRUE Response: " + response.body().getStatus());

                }
            }

            @Override
            public void onFailure(Call<AddNewAdress> call, Throwable t) {
                pd.dismiss();

                Log.d("ONRESPONSE", "on FAIL Response: " + t.getMessage());


            }
        });
    }


    public void editAdreesService(String Adress
            , String CityID
            , String longtud
            , String latitude
            , String description
            , String type
            , String block_no
            , String floor_no
            , String house_no
            , String userID
            , String Title
            , String mobile
            , String id
            ,String organization) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.edit_address(Adress
                , CityID
                , longtud
                , latitude
                , description
                , type
                , block_no
                , floor_no
                , house_no
                , userID
                , Title
                , mobile
                , id
                ,organization).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                pd.dismiss();
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {
                    dismiss();
                    View parentLayout = c.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + c.getString(R.string.AdressUpdated), Snackbar.LENGTH_LONG)
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    c.getFragmentManager().popBackStack();
                    c.onBackPressed();
                    Hawk.delete(Constants.Addlocationdlong);
                    Hawk.delete(Constants.Addlocationdlat);
                }


            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                pd.dismiss();
                View parentLayout = c.findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "" + c.getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                        .setAction("try Again", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //                              PublishDonation(address_id, user_id, type, description, descriptionDetail, image);

                            }
                        })
                        .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }
}
///////////////////////////
/* button1 = findViewById(R.id.checkboxHome);
        button2 = findViewById(R.id.checkboxBlock);
        button3 = findViewById(R.id.checkboxOrganiztion);
        button1.setChecked(true);
        Quantity = "0";
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setChecked(false);
                button3.setChecked(false);
                Quantity = "0";
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setChecked(false);
                button3.setChecked(false);
                Quantity = "1";
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setChecked(false);
                button1.setChecked(false);
                Quantity = "2";

            }
        });
*/