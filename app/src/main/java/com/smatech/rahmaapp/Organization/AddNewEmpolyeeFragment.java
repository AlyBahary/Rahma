package com.smatech.rahmaapp.Organization;


import android.app.ProgressDialog;
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

import com.smatech.rahmaapp.Models.RegistrationModel;
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
public class AddNewEmpolyeeFragment extends Fragment {
    TextView mScrollableTextView;
    LinearLayout goTologinActivity, scrolbarbgLayout;
    EditText nameEditText, numberEditText, emailEditText, passEditText, repassEditText;
    String name, number, email, pass, repass;
    Button Registration;
    public static final String TAG = "REGISTRATIONNNFragment";
    ProgressDialog pd;
    ImageView menu, Share, Back;
    TextView Title;

    public AddNewEmpolyeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_empolyee, container, false);
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
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
        Title.setText(getString(R.string.AddnewEmployee));


        nameEditText = view.findViewById(R.id.RegistratioName);
        numberEditText = view.findViewById(R.id.RegistratioNumber);
        emailEditText = view.findViewById(R.id.RegistrationEmail);
        passEditText = view.findViewById(R.id.RegistratioPass);
        Registration = view.findViewById(R.id.RegistrationButton);
        repassEditText = view.findViewById(R.id.RegistratioRePass);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getContext());
                pd.setMessage(getString(R.string.loading));
                pd.show();
                name = nameEditText.getText().toString();
                number = numberEditText.getText().toString();
                email = emailEditText.getText().toString();
                pass = passEditText.getText().toString();
                repass = repassEditText.getText().toString();
                if (name.equals("") || name.equals(null)) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.namePlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (number.equals("") || number.equals(null)) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.numberPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (email.equals("") || email.equals(null)) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.emailPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (pass.equals("") || pass.equals(null)) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (repass.equals("") || repass.equals(null)) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.passPlease), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!pass.equals(repass)) {
                    pd.dismiss();
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.notMatching), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.CLOSE), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    SingupConnection(pass + ""
                            , email + ""
                            , number + ""
                            , "3" + ""
                            , name + "",Hawk.get(Constants.USerID)+"");
                }
            }

        });

        return view;
    }

    private void SingupConnection(String password, String username, String mobile, String role, String name, String OrganisationID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.add_empoley(password, username, mobile, role, name,OrganisationID).enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                RegistrationModel registrationModel = response.body();
                Boolean x = registrationModel.getStatus();
                pd.dismiss();
                Log.d(TAG, "onResponse: " + registrationModel.getStatus() + registrationModel.getMessage());
                if (x) {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.EmployeeAdded), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_green_light))
                            .show();

                    getActivity().getFragmentManager().popBackStack();
                    getActivity().onBackPressed();

                } else {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + getString(R.string.numberExist), Snackbar.LENGTH_LONG)

                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }


            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
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
