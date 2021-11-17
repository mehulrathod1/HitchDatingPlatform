package com.in.hitch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.in.hitch.Model.ChatDashboardModel;
import com.in.hitch.Model.MembershipPlaneModel;
import com.in.hitch.R;

import java.util.ArrayList;

public class UpgradeMembershipAdapter extends PagerAdapter {

    Context context;
    ArrayList<MembershipPlaneModel.PlaneData> pager;


    public UpgradeMembershipAdapter(Context context, ArrayList<MembershipPlaneModel.PlaneData> pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.membership_item, container, false);

        TextView planeName, amount, duration, rewind_swipe, travelToHitch, unlimitedSwipe,
                shoeAds, hideDistance, hideAge, appearTop, advanceLike, directMessage;

        planeName = view.findViewById(R.id.planeName);
        amount = view.findViewById(R.id.amount);
        duration = view.findViewById(R.id.duration);
        rewind_swipe = view.findViewById(R.id.rewind_swipe);
        travelToHitch = view.findViewById(R.id.travelToHitch);
        unlimitedSwipe = view.findViewById(R.id.unlimitedSwipe);
        shoeAds = view.findViewById(R.id.shoeAds);
        hideDistance = view.findViewById(R.id.hideDistance);
        hideAge = view.findViewById(R.id.hideAge);
        appearTop = view.findViewById(R.id.appearTop);
        advanceLike = view.findViewById(R.id.advanceLike);
        directMessage = view.findViewById(R.id.directMessage);


        MembershipPlaneModel.PlaneData planeData = pager.get(position);


        planeName.setText(planeData.getPlan_name());
        amount.setText("₹ " +planeData.getAmount());
        duration.setText(planeData.getDuration_in_days()+" Days");
        rewind_swipe.setText(planeData.getUnhitch_rewind_your_swipe());
        travelToHitch.setText(planeData.getTravel_to_hitch_around_world());
        unlimitedSwipe.setText(planeData.getUnlimited_right_swipes());
        shoeAds.setText(planeData.getNo_sds());
        hideDistance.setText(planeData.getHide_distance());
        hideAge.setText(planeData.getHide_age());
        appearTop.setText(planeData.getAppear_in_top_hitches());
        advanceLike.setText(planeData.getKnow_in_advance_who_likes_you());
        directMessage.setText(planeData.getDirect_message_without_match());
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
