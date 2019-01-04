package com.smatech.rahmaapp.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.AllAdresses.getAllAdressModel1;
import com.smatech.rahmaapp.R;

import java.util.ArrayList;

///getAllA
public class AdressItemAdapter extends RecyclerView.Adapter<AdressItemAdapter.ViewHolder> {
    private ArrayList<getAllAdressModel1> getAllAdressModel1s;
    private AdressItemAdapter.OnItemClick mOnItemClick;
    private Context context;

    public AdressItemAdapter(ArrayList<getAllAdressModel1> getAllAdressModel1s, Context context, AdressItemAdapter.OnItemClick mOnItemClick) {
        this.getAllAdressModel1s = getAllAdressModel1s;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_list_item, viewGroup, false);
        return new AdressItemAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        getAllAdressModel1 itemMode = getAllAdressModel1s.get(i);
        viewHolder.TextDescription.setText(itemMode.getTitle());
        if (itemMode.getType().equals("0")) {
            viewHolder.TextTitle.setText(context.getString(R.string.Home));

        } else if (itemMode.getType().equals("1")) {
            viewHolder.TextTitle.setText(context.getString(R.string.Block));

        } else {
            viewHolder.TextTitle.setText(context.getString(R.string.organizationloc));

        }
        viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.home));
    }

    @Override
    public int getItemCount() {
        return getAllAdressModel1s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageType;
        TextView TextTitle, TextDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ImageType = itemView.findViewById(R.id.Dontation_item_image);
            TextTitle = itemView.findViewById(R.id.Dontation_item_Title);
            TextDescription = itemView.findViewById(R.id.Dontation_item_Description);
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
}