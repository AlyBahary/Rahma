package com.example.bahary.rahma;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahary.rahma.HomeFragments.ReciveDonationFragment;
import com.example.bahary.rahma.HomeFragments.addnewdonationFragment;
import com.example.bahary.rahma.Map.MapFragment;
import com.example.bahary.rahma.Models.UserData.UserDataModel;
import com.example.bahary.rahma.NavFragments.AboutUsFragment;
import com.example.bahary.rahma.NavFragments.ContactUSFragment;
import com.example.bahary.rahma.HomeFragments.MainHomeFragment;
import com.example.bahary.rahma.NavFragments.SettingFragment;
import com.example.bahary.rahma.NavFragments.SponserFragment;
import com.example.bahary.rahma.NavFragments.SuggestionFragment;
import com.example.bahary.rahma.NavFragments.ProfileFragment;
import com.example.bahary.rahma.Organization.Organiztion_Main_fragment;
import com.example.bahary.rahma.Utils.Connectors;
import com.example.bahary.rahma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    addnewdonationFragment addnewdonationfragment;
    MainHomeFragment mainHomeFragment;
    ReciveDonationFragment reciveDonationFragment;
    MapFragment mapFragment;
    Organiztion_Main_fragment organiztion_main_fragment;
    String type;
    ImageView menu, Share, Back;
    TextView Title, Accounttype;
    public View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.APPBAR);
        setSupportActionBar(toolbar);
/*
        toolbar.getBackground().setAlpha(0);
*/
        getSupportActionBar().setTitle("");
        menu = findViewById(R.id.toolbarMenu);
        Title = findViewById(R.id.toolbarTitle);
        Share = findViewById(R.id.toolbarshare);
        Back = findViewById(R.id.toolbarback);
        Back.setVisibility(View.GONE);
        type = Hawk.get(Constants.UserRole);
        mainHomeFragment = new MainHomeFragment();
        reciveDonationFragment = new ReciveDonationFragment();
        mapFragment = new MapFragment();
        organiztion_main_fragment = new Organiztion_Main_fragment();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        Accounttype = headerView.findViewById(R.id.AccountTypenav);
        Accounttype.setVisibility(View.GONE);
        getUserData(Hawk.get(Constants.USerID)+"");
        Log.d("TTTT", "onCreate: "+type);
        if (type.equals(Constants.Donor)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
            Title.setText(getString(R.string.Main));
            fragmentTransaction.commit();
            Accounttype.setVisibility(View.VISIBLE);
        } else if (type.equals(Constants.Beneficiary)) {
            if (Hawk.contains(Constants.User_Stuck)) {
                if (Hawk.get(Constants.User_Stuck).equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), StuckActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, mapFragment);
                    Title.setText(getString(R.string.Map));
                    fragmentTransaction.commit();
                    Accounttype.setVisibility(View.GONE);

                }

            } else {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, mapFragment);
                Title.setText(getString(R.string.Map));
                fragmentTransaction.commit();
                Accounttype.setVisibility(View.GONE);
            }
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Frgment_Container, organiztion_main_fragment);
            Title.setText(getString(R.string.Map));
            fragmentTransaction.commit();
            Accounttype.setVisibility(View.GONE);
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(null);
/*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });*/
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Signup by promo codePromoCode :"+Hawk.get(Constants.UserPromoCode)+" "+"https://play.google.com/store/apps/details?id=com.smatech.net.rahma&hl=en");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HomeActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        addnewdonationfragment = new addnewdonationFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if (Hawk.contains(Constants.DonationDetailsTOEdit)) {
                Hawk.delete(Constants.DonationDetailsTOEdit);
            }


        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        int id = item.getItemId();

        if (id == R.id.Nav_Home) {
            if (type.equals(Constants.Donor)) {
                fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (type.equals(Constants.Beneficiary)) {
                fragmentTransaction.replace(R.id.Frgment_Container, mapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.replace(R.id.Frgment_Container, organiztion_main_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }


            // Handle the camera action
        } else if (id == R.id.nav_AboutUs) {

            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, aboutUsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_Profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, profileFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_Setting) {
            SettingFragment settingFragment = new SettingFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, settingFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Sponsers) {
            SponserFragment sponserFragment = new SponserFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, sponserFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_ContactUs) {
            ContactUSFragment contactUSFragment = new ContactUSFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, contactUSFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Suggestion) {
            SuggestionFragment suggestionFragment = new SuggestionFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, suggestionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Exit) {
            Hawk.delete(Constants.User_Exist);
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void getUserData(String UserID) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.getUserData(UserID).enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                UserDataModel userDataModel = response.body();
                if (userDataModel.getStatus()) {
                    Log.d("RESPONSE", "onResponse:" + userDataModel.getUser().getAccountType());
                    Accounttype.setText(userDataModel.getUser().getAccountType());
                } else {
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {

            }
        });
    }
}
