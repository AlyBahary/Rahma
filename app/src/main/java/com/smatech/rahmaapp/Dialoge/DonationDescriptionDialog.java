package com.smatech.rahmaapp.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Models.Donation.DonationItmemModel;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.RegistrationActivity;
import com.smatech.rahmaapp.StuckActivity;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonationDescriptionDialog extends Dialog {

    TextView catgryType, QuantityType, place_no_txt, place_no_, floor_no_txt, floor_no_, house_no_txt, house_no_, Location_Description, Donation_Description,place_no_txt1;
    ImageView Image,dismiss,location;
    Button Done;

    public Activity c;
    public Dialog d;
    DonationItmemModel x;
    LinearLayout background;
    public DonationDescriptionDialog(Activity a, DonationItmemModel x) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.x = x;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.donation_describtion_dialog);
        d=this;
        background=findViewById(R.id.background);
        background.getBackground().setAlpha(1);
        location=findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String uri = "http://maps.google.com/maps?daddr=" + x.getAddress().getLatitude() + "," + x.getAddress().getLongitude() + " (" + "Where the Donation is at" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try
                {
                    c.startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        c.startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(c, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        catgryType = findViewById(R.id.catgryType);
        QuantityType = findViewById(R.id.QuantityType);
        Image = findViewById(R.id.Image);
        dismiss=findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (x.getCategory().equals("0")) {
            catgryType.setText(c.getString(R.string.Food));
            if (x.getImage() == null || x.getImage().equals("")) {
                Picasso.with(getContext()).load(R.drawable.food).placeholder(R.drawable.food).fit().into(Image);
            } else {
                Picasso.with(getContext()).load(x.getImage()).placeholder(R.drawable.food).fit().into(Image);
            }

            if (x.getType().equals("0")) {
                QuantityType.setText(c.getString(R.string.lessthanthree));
            } else {
                QuantityType.setText(c.getString(R.string.morethanten));

            }
        } else {
            catgryType.setText(c.getString(R.string.accesories));
            if (x.getImage() == null || x.getImage().equals("")) {
                Picasso.with(getContext()).load(R.drawable.accesories).placeholder(R.drawable.accesories).fit().into(Image);
            } else {
                Picasso.with(getContext()).load(x.getImage()).placeholder(R.drawable.accesories).fit().into(Image);
            }
            if (x.getType().equals("0")) {
                QuantityType.setText(c.getString(R.string.lessthanthree));
            } else {
                QuantityType.setText(c.getString(R.string.morethanthree));

            }
        }
        place_no_txt = findViewById(R.id.place_no_txt);
        place_no_ = findViewById(R.id.place_no_);
        floor_no_txt = findViewById(R.id.floor_no_txt);
        floor_no_ = findViewById(R.id.floor_no_);
        house_no_txt = findViewById(R.id.house_no_txt);
        house_no_ = findViewById(R.id.house_no_);
        place_no_txt1=findViewById(R.id.place_no_txt1);
        Location_Description = findViewById(R.id.Location_Description);
        Location_Description.setMovementMethod(new ScrollingMovementMethod());
        Donation_Description = findViewById(R.id.Donation_Description);
        Donation_Description.setMovementMethod(new ScrollingMovementMethod());

        if (x.getAddress().getType().equals("0")) {
            place_no_txt.setText( c.getString(R.string.locationtype)+" : "+c.getString(R.string.Home));
            place_no_txt1.setText(c.getString(R.string.HomeNo));
            place_no_.setText(x.getAddress().getHouseNo());
            floor_no_txt.setVisibility(View.GONE);
            floor_no_.setVisibility(View.GONE);
            house_no_.setVisibility(View.GONE);
            house_no_txt.setVisibility(View.GONE);
        }
        if (x.getAddress().getType().equals("1")) {
            place_no_txt.setText( c.getString(R.string.locationtype)+" : "+c.getString(R.string.Block));
            place_no_txt1.setText(c.getString(R.string.BlockNo));
            place_no_.setText(x.getAddress().getBlockNo());
            floor_no_.setText(x.getAddress().getFloorNo());
            floor_no_txt.setText(c.getString(R.string.FloorNumber));
            house_no_txt.setText(c.getString(R.string.HomeNo));
            house_no_.setText(x.getAddress().getHouseNo());
        }
        if (x.getAddress().getType().equals("2")) {
            place_no_txt.setText(c.getString(R.string.locationtype)+" : "+c.getString(R.string.organizationloc));
            place_no_txt1.setText(c.getString(R.string.organizationName));
            place_no_.setText(x.getAddress().getOrganization());
            floor_no_txt.setVisibility(View.GONE);
            floor_no_.setVisibility(View.GONE);
            house_no_.setVisibility(View.GONE);
            house_no_txt.setVisibility(View.GONE);

        }
        Location_Description.setText(x.getAddress().getTitle());
        Location_Description.setMovementMethod(new ScrollingMovementMethod());
        Donation_Description.setText(x.getDescription());
        Donation_Description.setMovementMethod(new ScrollingMovementMethod());


        Done = findViewById(R.id.iwantit);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put("X",x);
                EnrollDonation(""+Hawk.get(Constants.USerID),""+x.getId(),""+x.getFromId());

            }
        });


    }
    private void EnrollDonation(String user_id,String donation_id,String from_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.enroll_to_donation(user_id,donation_id,from_id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                Log.d("ForgetPAss", "onResponse: "+forgetPassModel.getStatus());
                if (!forgetPassModel.getStatus()) {
                    d.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + forgetPassModel.getMessage(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(c.getResources().getColor(android.R.color.holo_red_light))
                            .show();


                }
                else{
                    Intent intent=new Intent(getContext(),StuckActivity.class);
                    Hawk.put(Constants.User_Stuck,"1");
                    c.startActivity(intent);
                    c.finish();
                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {

            }
        });
    }

}
