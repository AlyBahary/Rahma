package com.smatech.rahmaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class Terms_Condrition extends AppCompatActivity {
    WebView wb;

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Hawk.contains(Constants.Language)){
            Log.d("TTTTTTT", "onCreate: Contain");

            if(Hawk.get(Constants.Language).equals("ar")){
                Context ct = Terms_Condrition.this;
                Resources res = ct.getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ct);
                String lang = sharedPref.getString("locale", "ar");
                Locale myLocale = new Locale(lang);
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
            }else{
                Context ct = Terms_Condrition.this;
                Resources res = ct.getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ct);
                String lang = sharedPref.getString("locale", "en");
                Locale myLocale = new Locale(lang);
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);

            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms__condrition);
        wb=(WebView)findViewById(R.id.webView1);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        Log.d("TTTT", "onCreate: CurrentLang"+CurrentLang.getLanguage());

        wb.setWebViewClient(new HelloWebViewClient());
        if ((CurrentLang).getLanguage().equals("ar")) {
            wb.loadUrl("http://www.rahma-app.com/rahma/api/webview?type=terms_ar");

        } else {
            wb.loadUrl("http://www.rahma-app.com/rahma/api/webview?type=terms");
        }
    }
}
