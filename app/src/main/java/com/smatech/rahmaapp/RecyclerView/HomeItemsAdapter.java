package com.smatech.rahmaapp.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smatech.rahmaapp.Models.HomeItemModel;
import com.smatech.rahmaapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.ViewHolder> {

    private ArrayList<HomeItemModel> homeItemModels;
    private OnItemClick mOnItemClick;
    private Context context;

    public HomeItemsAdapter(ArrayList<HomeItemModel> homeItemModels, Context context, OnItemClick mOnItemClick) {
        this.homeItemModels = homeItemModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_list_itme, viewGroup, false);
        return new HomeItemsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemsAdapter.ViewHolder viewHolder, int position) {
        HomeItemModel item = homeItemModels.get(position);
        //Picasso.get().load(R.drawable.list_of_donation).fit().into(viewHolder.ImageType);

        if (item.getImageView() == 0) {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.new_donation));
            viewHolder.card_home_item.setBackground(context.getResources().getDrawable(R.drawable.new_donation_background));

            //Picasso.with(context).load(R.drawable.new_donation_background).into(viewHolder.ImageType);
        } else if (item.getImageView() == 1) {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.address_icon));
            viewHolder.card_home_item.setBackground(context.getResources().getDrawable(R.drawable.adress_list_background));

        } else if (item.getImageView() == 2) {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.setting));
            viewHolder.card_home_item.setBackground(context.getResources().getDrawable(R.drawable.setting_background));
        } else if (item.getImageView() == 4) {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.add_employee_icon));
            viewHolder.card_home_item.setBackground(context.getResources().getDrawable(R.drawable.add_employee_background));
        }else if (item.getImageView() == 5) {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.emoloyee_icon));
            viewHolder.card_home_item.setBackground(context.getResources().getDrawable(R.drawable.emoloyee_list_background));

        } else {
            viewHolder.ImageType.setImageDrawable(context.getResources().getDrawable(R.drawable.donation_list_icon));
            viewHolder.card_home_item.setBackground(context.getResources().getDrawable(R.drawable.donation_list_background));

        }
        viewHolder.TextType.setText(item.getTextView() + "");

    }

    @Override
    public int getItemCount() {
        return homeItemModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageType;
        TextView TextType;
        CardView card_home_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ImageType = itemView.findViewById(R.id.Crd_view_Img);
            TextType = itemView.findViewById(R.id.Crd_view_Txt);
            card_home_item=itemView.findViewById(R.id.card_home_item);
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

