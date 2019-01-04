package com.smatech.rahmaapp.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smatech.rahmaapp.Dialoge.EmployeeEditDialige;
import com.smatech.rahmaapp.Models.ForgetPassModel;
import com.smatech.rahmaapp.Models.UserModel;
import com.smatech.rahmaapp.R;
import com.smatech.rahmaapp.Utils.Connectors;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//EmployeeListAdapter
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {
    private ArrayList<UserModel> getAllAdressModel1s;
    private AdressItemAdapter.OnItemClick mOnItemClick;
    private Context context;
    private Activity A;

    public EmployeeListAdapter(ArrayList<UserModel> getAllAdressModel1s, Context context, Activity A, AdressItemAdapter.OnItemClick mOnItemClick) {
        this.getAllAdressModel1s = getAllAdressModel1s;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
        this.A = A;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_item, viewGroup, false);
        return new EmployeeListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final UserModel itemMode = getAllAdressModel1s.get(i);
        viewHolder.TextDescription.setText(itemMode.getName());
        viewHolder.TextTitle.setText(itemMode.getUsername());
        viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.home));
        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeEditDialige cdd = new EmployeeEditDialige(A,itemMode);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(""+A.getString(R.string.DeleteConfirmation))
                        .setCancelable(false)
                        .setPositiveButton(A.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                Delete_Empolyee(itemMode.getId());
                            }
                        })
                        .setNegativeButton(context.getString(R.string.Cancell), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return getAllAdressModel1s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageType,Edit,Delete;
        TextView TextTitle, TextDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ImageType = itemView.findViewById(R.id.Dontation_item_image);
            TextTitle = itemView.findViewById(R.id.Dontation_item_Title);
            TextDescription = itemView.findViewById(R.id.Dontation_item_Description);
            Edit = itemView.findViewById(R.id.Edit);
            Delete = itemView.findViewById(R.id.Delete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClick.setOnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void setOnItemClick(int position);
    }

    public void Delete_Empolyee(String user_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getRegistrationsConnectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getRegistrationsConnectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.getRegistrationsConnectionServices.class);
        getRegistrationsConnectionServices.delete_empoley(user_id).enqueue(new Callback<ForgetPassModel>() {
            @Override
            public void onResponse(Call<ForgetPassModel> call, Response<ForgetPassModel> response) {
                ForgetPassModel forgetPassModel=response.body();
                if(forgetPassModel.getStatus()){
                    View parentLayout = A.findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "" + A.getString(R.string.EmployeeDeleted), Snackbar.LENGTH_LONG)
                            .show();
                    A.getFragmentManager().popBackStack();
                    A.onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ForgetPassModel> call, Throwable t) {
                View parentLayout = A.findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "" + A.getString(R.string.SomethingWrong), Snackbar.LENGTH_LONG)
                        .show();


            }
        });
    }
}



