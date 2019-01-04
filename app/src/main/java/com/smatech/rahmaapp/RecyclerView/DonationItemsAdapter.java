package com.smatech.rahmaapp.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.Donation.DonationItmemModel;
import com.smatech.rahmaapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonationItemsAdapter extends RecyclerView.Adapter<DonationItemsAdapter.ViewHolder> {
    private ArrayList<DonationItmemModel> donationItemModes;
    private DonationItemsAdapter.OnItemClick mOnItemClick;
    private Context context;

    public DonationItemsAdapter(ArrayList<DonationItmemModel> donationItemModes, Context context, DonationItemsAdapter.OnItemClick mOnItemClick) {
        this.donationItemModes = donationItemModes;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_list_item, viewGroup, false);
        return new DonationItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DonationItmemModel itemMode = donationItemModes.get(i);
        if(itemMode.getType().equals("0")){
            viewHolder.TextDescription.setText(context.getString(R.string.lessthanthree));
        }else{
            viewHolder.TextDescription.setText(context.getString(R.string.morethanthree));
        }

        if (itemMode.getCategory().equals("0")) {
            viewHolder.TextTitle.setText(context.getString(R.string.Food));
            if (!(itemMode.getImage()+"").equals(""))
                Picasso.with(context).load(itemMode.getImage()).placeholder(R.drawable.food).fit().into(viewHolder.ImageType);
            else
                Picasso.with(context).load(R.drawable.food).placeholder(R.drawable.food).fit().into(viewHolder.ImageType);

        } else {
            viewHolder.TextTitle.setText(context.getString(R.string.accesories));
            if (!(itemMode.getImage()+"").equals(""))
                Picasso.with(context).load(itemMode.getImage()).placeholder(R.drawable.accesories).fit().into(viewHolder.ImageType);
            else
                Picasso.with(context).load(R.drawable.accesories).placeholder(R.drawable.accesories).fit().into(viewHolder.ImageType);

        }
/*
        if (itemMode.getImageNo() == 1) {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.food));
        } else {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.accesories));
*/


    }

    @Override
    public int getItemCount() {
        return donationItemModes.size();

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