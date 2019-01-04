package com.smatech.rahmaapp.NavFragments;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUSFragment extends Fragment {

    ImageView menu, Share, Back;
    TextView Title;
    LinearLayout scrolbarbgLayout;
    TextView mScrollableTextView;

    EditText nameEditText, numberEditText, MSGEditText, MSGtittleEditText;
    String name, number, MSG, title;
    Button Send;

    public ContactUSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_u, container, false);


        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        Log.d("TTTT", "onCreate: CurrentLang"+CurrentLang.getLanguage());

        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR) + "");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN) + "");
        }
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
        Title.setText(getString(R.string.Contact_Us));
        nameEditText = view.findViewById(R.id.name);
        numberEditText = view.findViewById(R.id.number);
        MSGEditText = view.findViewById(R.id.MSG);
        MSGtittleEditText = view.findViewById(R.id.MSGtittle);
        Send = view.findViewById(R.id.send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                number = numberEditText.getText().toString();
                MSG = MSGEditText.getText().toString();
                title = MSGtittleEditText.getText().toString();
                if ((name == null || name.equals("") || number == null || number.equals("")
                        || MSG == null || MSG.equals("") || title == null || title.equals(""))) {

                    {
                        View parentLayout = getActivity().findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "" + getString(R.string.PleaseFillEmptyFields), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }


                } else {
                    Send_FeedBack(name + "", number + "", MSG + ""
                            , Hawk.get(Constants.USerID) + "", "" + title);
                }


            }
        });

        return view;

    }

    private void Send_FeedBack(String name
            , String mobile
            , String message
            , String user_id
            , String title) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.send_feedback(name, mobile, message, user_id, title).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel = response.body();
                if (forgetPassModel.getStatus()) {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.yourMSGHadbeenRecived), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_green_light))
                            .show();
                    nameEditText.setText("");
                    numberEditText.setText("");
                    MSGEditText.setText("");
                    MSGtittleEditText.setText("");


                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                View parentLayout = getActivity().findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "" + getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }


}
