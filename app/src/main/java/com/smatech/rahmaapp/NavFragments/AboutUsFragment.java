package com.smatech.rahmaapp.NavFragments;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {


    ImageView menu, Share, Back;
    TextView Title;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;
    WebView wb;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (Hawk.contains(Constants.Language)) {
            Log.d("TTTTTTT", "onCreate: Contain");

            if (Hawk.get(Constants.Language).equals("ar")) {
                Log.d("TTTTTTT", "onCreate: AR");

                Hawk.put(Constants.Language, "ar");
                Constants.languageChange("ar", getActivity(), "");
            } else {
                Hawk.put(Constants.Language, "en");
                Constants.languageChange("en", getActivity(), "");
                Log.d("TTTTTTT", "onCreate: english");

            }
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        final Locale CurrentLang = getResources().getConfiguration().locale;


        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR) + "");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN) + "");
        }
        Log.d("TTTT", "onCreate: CurrentLang" + CurrentLang.getLanguage());

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
        Title.setText(getString(R.string.Aboutus));
        wb = (WebView) view.findViewById(R.id.webView1);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        wb.setWebViewClient(new HelloWebViewClient());
        if ((CurrentLang).getLanguage().equals("ar")) {
            if (Hawk.contains(Constants.Language)) {
                Log.d("TTTTTTT", "onCreate: Contain");

                if (Hawk.get(Constants.Language).equals("ar")) {
                    Log.d("TTTTTTT", "onCreate: AR");

                    Hawk.put(Constants.Language, "ar");
                    Constants.languageChange("ar", getActivity(), "");
                } else {
                    Hawk.put(Constants.Language, "en");
                    Constants.languageChange("en", getActivity(), "");
                    Log.d("TTTTTTT", "onCreate: english");

                }
            }
            wb.loadUrl("http://www.rahma-app.com/rahma/api/webview?type=about_ar");

        } else {
            wb.loadUrl("http://www.rahma-app.com/rahma/api/webview?type=about");
            if (Hawk.contains(Constants.Language)) {
                Log.d("TTTTTTT", "onCreate: Contain");

                if (Hawk.get(Constants.Language).equals("ar")) {
                    Log.d("TTTTTTT", "onCreate: AR");

                    Hawk.put(Constants.Language, "ar");
                    Constants.languageChange("ar", getActivity(), "");
                } else {
                    Hawk.put(Constants.Language, "en");
                    Constants.languageChange("en", getActivity(), "");
                    Log.d("TTTTTTT", "onCreate: english");

                }
            }
        }

        return view;
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}

