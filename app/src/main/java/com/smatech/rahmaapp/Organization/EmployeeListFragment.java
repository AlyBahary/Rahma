package com.smatech.rahmaapp.Organization;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smatech.rahmaapp.Dialoge.CustomDialogClass;
import com.smatech.rahmaapp.Models.EmployeModel;
import com.smatech.rahmaapp.Models.UserModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.RecyclerView.AdressItemAdapter;
import com.smatech.rahmaapp.RecyclerView.EmployeeListAdapter;
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
public class EmployeeListFragment extends Fragment {
    TextView mScrollableTextView;
    RecyclerView recyclerView;
    EmployeeListAdapter employeeListAdapter;
    ArrayList<UserModel> donationItemModes;
    LinearLayout scrolbarbgLayout;
    ImageView menu, Share, Back;
    TextView Title;
    Button AddAdressButton;
    ProgressDialog pd;
    LinearLayout noData;

    public EmployeeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);
        noData=view.findViewById(R.id.noData);

        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        mScrollableTextView.setSelected(true);
        mScrollableTextView.setHorizontallyScrolling(true);
        scrolbarbgLayout = view.findViewById(R.id.scrolbarbg);
        mScrollableTextView = view.findViewById(R.id.scrollingtext);
        scrolbarbgLayout.getBackground().setAlpha(120);
        mScrollableTextView.getBackground().setAlpha(120);
        final Locale CurrentLang = getResources().getConfiguration().locale;
        if ((CurrentLang).getLanguage().equals("ar")) {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_AR)+"");
        } else {
            mScrollableTextView.setText(Hawk.get(Constants.Slider_txt_EN)+"");
        }
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.loading));
        pd.show();

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
        Title.setText(getString(R.string.EmloyeeList));

        donationItemModes = new ArrayList<>();
        recyclerView = view.findViewById(R.id.AdressListRV);

        employeeListAdapter = new EmployeeListAdapter(donationItemModes, getContext(),getActivity(), new AdressItemAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {


            }
        });
        recyclerView.setAdapter(employeeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        employeeListAdapter.notifyDataSetChanged();
        getEmployeeList(Hawk.get(Constants.USerID)+"");


        return view;
    }
    private void getEmployeeList(String ID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.get_empolyees(ID).enqueue(new Callback<EmployeModel>() {
            @Override
            public void onResponse(Call<EmployeModel> call, Response<EmployeModel> response) {
                pd.dismiss();
                EmployeModel employeModel=response.body();
                if(employeModel.getStatus()){
                    EmployeeListFragment.this.donationItemModes.addAll(employeModel.getUsers());
                    employeeListAdapter.notifyDataSetChanged();
                    noData.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<EmployeModel> call, Throwable t) {

            }
        });
    }

}
