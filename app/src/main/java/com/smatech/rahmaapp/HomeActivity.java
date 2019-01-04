package com.smatech.rahmaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smatech.rahmaapp.HomeFragments.ReciveDonationFragment;
import com.smatech.rahmaapp.HomeFragments.addnewdonationFragment;
import com.smatech.rahmaapp.Map.MapFragment;
import com.smatech.rahmaapp.Models.LoginModel;
import com.smatech.rahmaapp.Models.UserData.UserDataModel;
import com.smatech.rahmaapp.NavFragments.AboutUsFragment;
import com.smatech.rahmaapp.NavFragments.ContactUSFragment;
import com.smatech.rahmaapp.HomeFragments.MainHomeFragment;
import com.smatech.rahmaapp.NavFragments.SettingFragment;
import com.smatech.rahmaapp.NavFragments.SponserFragment;
import com.smatech.rahmaapp.NavFragments.ProfileFragment;
import com.smatech.rahmaapp.Organization.Organiztion_Main_fragment;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

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
    Locale CurrentLang;
    public NavigationView navigationView;
    private static final String CHANNEL_ID = "1";
    private static final String CHANNEL_NAME = "RAHMA";
    private static final String CHANNEL_DESCRIPTION = "Raham Transaction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.APPBAR);
        setSupportActionBar(toolbar);
        displayNotification();
/*
        toolbar.getBackground().setAlpha(0);
*/

/*
if(Hawk.contains(Constants.Language)){
    if(Hawk.get(Constants.Language).equals("")||Hawk.get(Constants.Language)==null){
        Constants.languageChange(Hawk.get(Constants.Language)+"", this);

    }
}
*/
        CurrentLang = getResources().getConfiguration().locale;

        Log.d("TTTT", "onCreate: CurrentLang" + CurrentLang.getLanguage());

        LoginConnection();
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        Accounttype = headerView.findViewById(R.id.AccountTypenav);
        Accounttype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Frgment_Container, profileFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        Accounttype.setVisibility(View.GONE);
        getUserData(Hawk.get(Constants.USerID) + "");
        Log.d("TTTT", "onCreate: " + type);
        if (type.equals(Constants.Donor)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Frgment_Container, mainHomeFragment);
            Title.setText(getString(R.string.Main));
            fragmentTransaction.commit();
            Accounttype.setVisibility(View.VISIBLE);
        } else if (type.equals(Constants.Beneficiary) || type.equals("3")) {
            if (Hawk.contains(Constants.User_Stuck)) {
                if (Hawk.get(Constants.User_Stuck).equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), StuckActivity.class);
                    startActivity(intent);
                    finish();
                } else {
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
               /* Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Signup by promo codePromoCode :"+Hawk.get(Constants.UserPromoCode)+" "+"https://play.google.com/store/apps/details?id=com.smatech.net.rahma&hl=en");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HomeActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Rahma");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.inviationtxt1)
                        + "https://play.google.com/store/apps/details?id=com.smatech.rahmaapp" + getString(R.string.inviationtxt2) +
                        Hawk.get(Constants.UserPromoCode));
                startActivity(Intent.createChooser(intent, "choose one"));

            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        addnewdonationfragment = new addnewdonationFragment();
    }

    private void displayNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setSmallIcon(R.drawable.app_icon);

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
            } else if (type.equals(Constants.Beneficiary) || type.equals("3")) {
                Log.d("TTTTT", "onNavigationItemSelected: Refresh");

                mapFragment.getDonationListConnection(Hawk.get(Constants.USerID) + "", "", "", "", Hawk.get(Constants.UserRole) + "", Hawk.get(Constants.UserType) + "");

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
            Log.d("TTTTT", "onNavigationItemSelected: Refresh");
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

        } /*else if (id == R.id.nav_Suggestion) {
         *//*  SuggestionFragment suggestionFragment = new SuggestionFragment();
            fragmentTransaction.replace(R.id.Frgment_Container, suggestionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
*//*
        }*/ else if (id == R.id.nav_Exit) {
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
                    if ((CurrentLang).getLanguage().equals("ar")) {
                        Accounttype.setText(userDataModel.getUser().getAccountType());
                    } else {
                        Accounttype.setText(userDataModel.getUser().getAccount_type_en());
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {

            }
        });
    }

    private void LoginConnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);

        getRegistrationsConnectionServices.login(Hawk.get(Constants.LOGIN_NAME) + ""
                , "" + Hawk.get(Constants.LOGIN_pass)
                , "" + Hawk.get(Constants.TOKEN)).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                LoginModel loginModel = response.body();
                Boolean x = loginModel.getStatus();
                if (x) {


                } else {
                    final AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(HomeActivity.this);
                    }
                    builder.setTitle(getString(R.string.Alert))
                            .setMessage(loginModel.getMessage())
                            .setPositiveButton(getString(R.string.Confirm), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Hawk.deleteAll();
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    builder.setCancelable(false);

                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Hawk.deleteAll();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    });
                    Timer timer = new Timer();
                    final long DELAY = 3000; // milliseconds
                    timer.cancel();
                    timer = new Timer();

                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    // TODO: do what you need here (refresh list)
                                    HomeActivity.this.runOnUiThread(new Runnable() {

                                        public void run() {


                                            Hawk.deleteAll();
                                            android.os.Process.killProcess(android.os.Process.myPid());
                                            System.exit(1);

                                        }
                                    });
                                }


                            },
                            DELAY
                    );

                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }
}
