package com.smatech.rahmaapp.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.smatech.rahmaapp.Models.VideoModel.VideoModel;
import com.smatech.rahmaapp.Models.VideoModel.VideoModelExample;
import com.smatech.rahmaapp.NavFragments.AboutUsFragment;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.smatech.rahmaapp.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstTimeAddActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public Activity c;
    public Dialog d;
    TextView Describtion,Agreementtext;
    Button Done;
    CheckBox checkBox;
    Locale CurrentLang;
    private final String KEY = "AIzaSyAFNioehAC-KZF6-WNxKvg_WV8zX0N8PE8";
    YouTubePlayerView youTubeView;
    String link = "";
    WebView wb;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (checkBox.isChecked()) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time_explain);
        Done = findViewById(R.id.Done);
        Agreementtext = findViewById(R.id.Agreementtext);
        CurrentLang = getResources().getConfiguration().locale;
        if (Hawk.contains(Constants.Language)){
            Log.d("TTTTT", "Currnet Hawk"+Hawk.get(Constants.Language)+"----"+"onCreate: "+ CurrentLang );
            if (Hawk.get(Constants.Language).equals("en")) {
                Constants.languageChange("en",this,"");
            }else{
                Constants.languageChange("ar",this,"");
            }
            CurrentLang = getResources().getConfiguration().locale;
            Agreementtext.setText(getString(R.string.addAdressReaded));
            Done.setText(getString(R.string.Done));

        }
        getVideo();
        Describtion = findViewById(R.id.Describtion);
        Done.setEnabled(false);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Done.setEnabled(true);
                    Done.setBackgroundResource(R.drawable.button1);


                } else {
                    Done.setEnabled(false);
                    Done.setBackgroundResource(R.drawable.button1_disable);
                }
            }
        });
        youTubeView = (YouTubePlayerView)
                findViewById(R.id.youtubeVideo);
        wb = (WebView) findViewById(R.id.webView1);


        // Grabs a reference to the player view

        // Sets the callback to this Activity, since it inherits EasyVideoCallback

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        // player.setSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) youTubePlayer.cueVideo("" + link); // your video to play


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private void getVideo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.get_video().enqueue(new Callback<VideoModelExample>() {
            @Override
            public void onResponse(Call<VideoModelExample> call, Response<VideoModelExample> response) {
                VideoModelExample videoModelExample = response.body();
                Log.d("TTTT", "onResponse: " + ".......--..." + CurrentLang);

                if (videoModelExample.getStatus()) {
                    VideoModel video = videoModelExample.getVideo();
                    Log.d("TTTT", "onResponse: " + video.getDescription() + video.getDescriptionAr());


                    if (CurrentLang.getLanguage().equals("en")) {
                        Describtion.setText(Html.fromHtml(video.getDescription()));

                    } else {
                        Describtion.setText(Html.fromHtml(video.getDescriptionAr()));
                    }
                    link = video.getVideo();
                    youTubeView.initialize(KEY, FirstTimeAddActivity.this);

                    wb.getSettings().setJavaScriptEnabled(true);
                    wb.getSettings().setLoadWithOverviewMode(true);
                    wb.getSettings().setUseWideViewPort(true);
                    wb.getSettings().setBuiltInZoomControls(true);
                    wb.getSettings().setPluginState(WebSettings.PluginState.ON);
                    wb.setWebViewClient(new FirstTimeAddActivity.HelloWebViewClient());
                    if ((CurrentLang).getLanguage().equals("ar")) {
                        if (Hawk.contains(Constants.Language)) {
                            Log.d("TTTTTTT", "onCreate: Contain");

                            if (Hawk.get(Constants.Language).equals("ar")) {
                                Log.d("TTTTTTT", "onCreate: AR");

                                Hawk.put(Constants.Language, "ar");
                                Constants.languageChange("ar", FirstTimeAddActivity.this, "");
                            } else {
                                Hawk.put(Constants.Language, "en");
                                Constants.languageChange("en", FirstTimeAddActivity.this, "");
                                Log.d("TTTTTTT", "onCreate: english");

                            }
                        }
                        wb.loadUrl(link);

                    } else {
                        wb.loadUrl(link);
                        if (Hawk.contains(Constants.Language)) {
                            Log.d("TTTTTTT", "onCreate: Contain");

                            if (Hawk.get(Constants.Language).equals("ar")) {
                                Log.d("TTTTTTT", "onCreate: AR");

                                Hawk.put(Constants.Language, "ar");
                                Constants.languageChange("ar", FirstTimeAddActivity.this, "");
                            } else {
                                Hawk.put(Constants.Language, "en");
                                Constants.languageChange("en", FirstTimeAddActivity.this, "");
                                Log.d("TTTTTTT", "onCreate: english");

                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<VideoModelExample> call, Throwable t) {

            }
        });


    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}
