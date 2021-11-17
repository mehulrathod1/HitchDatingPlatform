package com.in.hitch.Activities;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.in.hitch.Model.Profile;
import com.in.hitch.R;
import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.SwipeDirectionalView;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;

import javax.security.auth.callback.Callback;

@Layout(R.layout.tinder_card_view)
public class TinderCard {


    @View(R.id.reload)
    private ImageView reload;

    @View(R.id.nope)
    private ImageView nope;

    @View(R.id.like)
    private ImageView like;

    @View(R.id.star)
    private ImageView star;


    @View(R.id.power)
    private ImageView power;

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @View(R.id.cardview)
    private FrameLayout frameLayout;
    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private OnItemClickListener mlistener;

    @SwipeView
    android.view.View SwipeView;
    Callback callback;


    public TinderCard(Context context, Profile profile, SwipePlaceHolderView swipeView, OnItemClickListener listener) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        mlistener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListner(OnItemClickListener listner) {
        mlistener = listner;
    }

    @Resolve
    public void onResolved() {

        Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);

        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
        locationNameTxt.setText(mProfile.getLocation());

        reload.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                if (mlistener != null) {

                    mSwipeView.undoLastSwipe();

                }
                Toast.makeText(mContext, "undo", Toast.LENGTH_SHORT).show();
            }
        });
        nope.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                mSwipeView.doSwipe(false);
            }
        });
        like.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                mSwipeView.doSwipe(true);
            }
        });
        nameAgeTxt.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                if (mlistener != null) {
                    mlistener.onItemClick();
                }
            }
        });
        star.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(mContext, "super Like", Toast.LENGTH_SHORT).show();

            }
        });
        power.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Toast.makeText(mContext, "power", Toast.LENGTH_SHORT).show();
            }
        });

//        Configuration config = mContext.getResources().getConfiguration();
//        float scale_height = mContext.getResources().getDimension(R.dimen._390sdp);
//        float scale_width = mContext.getResources().getDimension(R.dimen._290sdp);
//        int h = (int) scale_height;
//        int w = (int) scale_width;
//
//        if (config.smallestScreenWidthDp >= 380) {
//            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(w, h));
//        }

    }

    @SwipeOutDirectional
    private void onSwipeOutDirectional(@NonNull SwipeDirection direction) {
        Log.d("DEBUG", "SwipeOutDirectional " + direction.name());
    }

    @SwipeInDirectional
    private void onSwipeInDirectional(@NonNull SwipeDirection direction) {
        Log.d("DEBUG", "SwipeInDirectional " + direction.name());
    }
    @SwipingDirection
    private void onSwipingDirection(SwipeDirection direction) {
        Log.e("DEBUG", "SwipingDirection " + direction.name());
    }

    @SwipeTouch
    private void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {
        Log.d("DEBUG", "onSwipeTouch "
                + " xStart : " + xStart
                + " yStart : " + yStart
                + " xCurrent : " + xCurrent
                + " yCurrent : " + yCurrent
                + " distance : "
                + Math.sqrt(Math.pow(xCurrent - xStart, 2) + (Math.pow(yCurrent - yStart, 2)))
        );
    }

    @SwipeOut
    public void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }

    interface Callback {
        void onSwipeUp();
    }
}
