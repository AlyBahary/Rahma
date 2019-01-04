package com.smatech.rahmaapp.NavFragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.smatech.rahmaapp.HomeActivity;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.smatech.rahmaapp.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    TextView mScrollableTextView;
    ImageView menu, Share, Back;
    TextView Title, PasswordTextView;
    EditText EmailEditText, NameEditText, MobileEditText;
    String Name, Email, Password, Mobile, ID;
    Button save;
    LinearLayout scrolbarbgLayout;
    Spinner LanugaeSpinner;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> Language;
    String X = "L";
    int Positi;
    ProgressDialog pd;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        pd=new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.loading));
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
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
        }

        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
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
        Title.setText(getString(R.string.Setting));

        Name = Hawk.get(Constants.Name) + "";
        Email = Hawk.get(Constants.UserName) + "";
        Mobile = Hawk.get(Constants.Mobile) + "";
        Password = Hawk.get(Constants.UserName) + "";
        ID = Hawk.get(Constants.USerID) + "";
        save = view.findViewById(R.id.Settingsave);
        EmailEditText = view.findViewById(R.id.SettingEmail);
        NameEditText = view.findViewById(R.id.SettingName);
        MobileEditText = view.findViewById(R.id.SettingNumber);
        PasswordTextView = view.findViewById(R.id.SettingPassword);
        EmailEditText.setText("" + Email);
        NameEditText.setText("" + Name);
        MobileEditText.setText("" + Mobile);
        PasswordTextView.setText("***************");


        Language = new ArrayList<>();
        Language.add(getString(R.string.SelectLanguage));
        Language.add(getString(R.string.Ennglish));
        Language.add(getString(R.string.Arabic));

        LanugaeSpinner = view.findViewById(R.id.LanugaeSpinner);
        dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, Language);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LanugaeSpinner.setAdapter(dataAdapter);


        LanugaeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Positi = position;

                    final AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle(getString(R.string.Alert))
                            .setMessage(getString(R.string.LanguageMessage))

                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Positi == 1) {
                                        Hawk.put(Constants.Language, "en");
                                        Constants.languageChange("en", getActivity(),"");
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        getActivity().startActivity(intent);
                                        getActivity().finish();


                                    } else if (Positi == 2) {
                                        Hawk.put(Constants.Language, "ar");
                                        Constants.languageChange("ar", getActivity(),"");
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        getActivity().startActivity(intent);
                                        getActivity().finish();

                                    }


                                }
                            })
                            .
                                    setNegativeButton(getString(R.string.Cancell), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        save.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                pd.show();

                if (EmailEditText.getText().toString() == null || EmailEditText.getText().toString().equals("")
                        || NameEditText.getText().toString().equals("") || NameEditText.getText().toString() == null
                        || MobileEditText.getText().toString().equals("") || MobileEditText.getText().toString() == null) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.PleaseFillEmptyFields), Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Name = NameEditText.getText().toString();
                    Email = EmailEditText.getText().toString();
                    Mobile = MobileEditText.getText().toString();
                    editUserService(Email, Mobile, Name, ID);
                }

            }
        });

        return view;
    }

    private void editUserService(final String email, final String mobile, final String name, String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.EditAcc(email, mobile, name, id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                pd.dismiss();

                ForgetPassModel editModel = response.body();
                if (editModel.getStatus()) {
                    Hawk.put(Constants.Mobile, mobile);
                    Hawk.put(Constants.UserName, email);
                    Hawk.put(Constants.Name, name);
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.Accounthadbeenedited), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    /*getActivity().getFragmentManager().popBackStack();
                    getActivity().onBackPressed();*/
                  /*  View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.Accounthadbeenedited), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();*/


                }
                Log.d("ProfileChanged", "onResponse: " + editModel.getStatus());
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                pd.dismiss();
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
