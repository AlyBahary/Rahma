package com.smatech.rahmaapp.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.smatech.rahmaapp.R;

public class CustomSliderView extends BaseSliderView {

    public CustomSliderView(Context context) {
        super(context);
    }

    public View getView() {

        View v = LayoutInflater.from(this.getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_text, null);
        ImageView target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        LinearLayout frame = (LinearLayout) v.findViewById(R.id.description_layout);
        frame.setBackgroundColor(Color.TRANSPARENT);

//      if you need description
//      description.setText(this.getDescription());

        this.bindEventAndShow(v, target);

        return v;
    }
}