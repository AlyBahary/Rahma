package com.smatech.rahmaapp.HomeFragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smatech.rahmaapp.Models.SocialModel.SocialModel;
import com.smatech.rahmaapp.Models.SocialModel.SocialModelExample;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;import com.smatech.rahmaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Social_Fragment extends Fragment {

    TextView howtouse, about;
    ImageView fb, tw, ins, wtsp;
    String f, t, i, w;
    ProgressDialog pd;
    ImageView menu, Share, Back;
    TextView Title;

    public Social_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getSocial();
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
        Title.setText(getString(R.string.Follow_us));

        pd = new ProgressDialog(getActivity());
        pd.setMessage(getString(R.string.loading));
        pd.show();
        View view = inflater.inflate(R.layout.fragment_social_, container, false);
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        howtouse = view.findViewById(R.id.howtouse);
        about = view.findViewById(R.id.about);
        fb = view.findViewById(R.id.facebook);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("http://facebook.com/" + f);
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.facebook.katana");

                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://facebook.com/" + f)));
                    }
                } catch (Exception e) {
                }

            }
        });
        tw = view.findViewById(R.id.twitter);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + t)));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + t)));
                }

            }
        });
        ins = view.findViewById(R.id.instgram);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/" + i);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/"+i)));
                }

            }
        });
        wtsp = view.findViewById(R.id.wtsp);
        wtsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PackageManager packageManager = getContext().getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone=" + w + "&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        getContext().startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

          /*      PackageManager pm=getActivity().getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "YOUR TEXT HERE";

                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }



                            try {

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                    sendIntent.putExtra("jid", "966503572205" + "@s.whatsapp.net");
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } catch (Exception e)

                {
                    Toast.makeText(getActivity(), "Error\n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
*/
            }

        });

        return view;
    }

    private void getSocial() {
        final String CurrentLang = Locale.getDefault().getLanguage();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.get_social().enqueue(new Callback<SocialModelExample>() {
            @Override
            public void onResponse(Call<SocialModelExample> call, Response<SocialModelExample> response) {
                pd.dismiss();
                SocialModelExample example = response.body();
                if (example.getStatus()) {
                    SocialModel socialModel = example.getSetting();
                    if (CurrentLang == "en") {
                        howtouse.setText(socialModel.getHowToUse());
                        about.setText(socialModel.getAbout());
                    } else {
                        howtouse.setText(socialModel.getHowToUseAr());
                        about.setText(socialModel.getAboutAr());

                    }
                    f = socialModel.getFacebook();
                    i = socialModel.getInstagram();
                    w = (socialModel.getWhatsapp()).substring(1,socialModel.getWhatsapp().length());
                    Log.d("TTTT", "onResponse: "+w);
                    t = socialModel.getTwitter();

                }

            }

            @Override
            public void onFailure(Call<SocialModelExample> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "There is no internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
