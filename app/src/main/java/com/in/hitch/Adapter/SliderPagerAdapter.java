package com.in.hitch.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.in.hitch.Model.ProfileDetailModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<ProfileDetailModel.ProfileDetail.ImageList> image_arraylist;



    public SliderPagerAdapter(Activity activity, ArrayList<ProfileDetailModel.ProfileDetail.ImageList> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);

        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);

        ProfileDetailModel.ProfileDetail.ImageList model = image_arraylist.get(position);

        Picasso.get()
                .load(model.getImage_name())
                .placeholder(R.drawable.ic_action_close) // optional
                .error(R.mipmap.ic_launcher)         // optional
                .into(im_slider);


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
