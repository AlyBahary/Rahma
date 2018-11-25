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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahary.rahma.HomeFragments.ReciveDonationFragment;
import com.example.bahary.rahma.HomeFragments.addnewdonationFragment;
import com.example.bahary.rahma.Map.MapFragment;
import com.example.bahary.rahma.NavFragments.AboutUsFragment;
import com.example.bahary.rahma.NavFragments.ContactUSFragment;
import com.example.bahary.rahma.HomeFragments.MainHomeFragment;
import com.example.bahary.rahma.NavFragments.SettingFragment;
import com.example.bahary.rahma.NavFragments.SponserFragment;
import com.example.bahary.rahma.NavFragments.SuggestionFragment;
import com.example.bahary.rahma.NavFragments.ProfileFragment;
import com.example.bahary.rahma.Organization.Organiztion_Main_fragment;
import com.example.bahary.rahma.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    addnewdonationFragment addnewdonationfragment;
    MainHomeFragment mainHomeFragment;
    ReciveDonationFragment reciveDonationFragment;
    MapFragment mapFragment;
    Organiztion_Main_fragment organiztion_main_fragment;
    String type;
    ImageView menu, Share,Back;
    TextView Title;

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
        type = Hawk.get(Constants.UserType);
        mainHomeFragment = new MainHomeFragment();
        reciveDonationFragment = new ReciveDonationFragment();
        mapFragment = new MapFragment();
        organiztion_main_fragment = new Organiztion_Main_fragment();

        if (type.equals(Constants.Donor)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
            Title.setText(getString(R.string.Main));
            fragmentTransaction.commit();
        } else if(type.equals(Constants.Beneficiary)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Frgment_Container, mapFragment);
            Title.setText(getString(R.string.Map));
            fragmentTransaction.commit();
        }
        else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Frgment_Container, organiztion_main_fragment);
            Title.setText(getString(R.string.Map));
            fragmentTransaction.commit();

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

            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            if(Hawk.contains(Constants.DonationDetailsTOEdit)){
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
            } else if(type.equals(Constants.Beneficiary)) {
                fragmentTransaction.replace(R.id.Frgment_Container, mapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else{
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
            Intent i =new Intent(HomeActivity.this,RegistrationsTypeActivity.class);
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
        if(fragments != null){
            for(Fragment fragment : fragments){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
