package com.example.bahary.rahma;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bahary.rahma.HomeFragments.addnewdonationFragment;
import com.example.bahary.rahma.NavFragments.ContactUSFragment;
import com.example.bahary.rahma.HomeFragments.MainHomeFragment;
import com.example.bahary.rahma.NavFragments.SettingFragment;
import com.example.bahary.rahma.NavFragments.SuggestionFragment;
import com.example.bahary.rahma.NavFragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    addnewdonationFragment addnewdonationfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(50);
        final MainHomeFragment mainHomeFragment=new MainHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
          addnewdonationfragment=new addnewdonationFragment();
        mainHomeFragment.setOnSearchBarHomeClicked(new MainHomeFragment.OnSearchBarHomeClicked() {
            @Override
            public void setOnSearchBarHomeClicked(int type) {
                if(type==0) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frgment_Container, addnewdonationfragment);
                    fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


        int id = item.getItemId();

        if (id == R.id.Nav_Home) {
            MainHomeFragment mainHomeFragment=new MainHomeFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
            fragmentTransaction.commit();

            // Handle the camera action
        } else if (id == R.id.nav_AboutUs) {



        } else if (id == R.id.nav_Profile) {
            ProfileFragment profileFragment=new ProfileFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, profileFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_Setting) {
            SettingFragment settingFragment=new SettingFragment();
            fragmentTransaction.replace(R.id.Frgment_Container,settingFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Sponsers) {
            addnewdonationFragment addnewdonationFragment=new addnewdonationFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, addnewdonationFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_ContactUs) {
            ContactUSFragment contactUSFragment=new ContactUSFragment();
            fragmentTransaction.replace(R.id.Frgment_Container,contactUSFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Suggestion) {
            SuggestionFragment suggestionFragment=new SuggestionFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, suggestionFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
