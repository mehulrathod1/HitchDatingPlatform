package com.in.hitch.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.in.hitch.Model.Profile;
import com.in.hitch.R;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Tinder_adapter extends RecyclerView.Adapter<Tinder_adapter.Viewholder> {

    private List<Profile> profile;
    private Context context;
    private ProgressDialog progressDialog;
    private SwipePlaceHolderView swipeView;

    public Tinder_adapter(Context context, List<Profile> profile, SwipePlaceHolderView swipeView) {
        this.profile = profile;
        this.context = context;
        this.swipeView = swipeView;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    private OnItemClickListener mlistener;

    public void setOnItemClickListner(OnItemClickListener listner) {
        mlistener = listner;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tinder_card_view, parent, false);
        return new Viewholder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Profile mProfile = profile.get(position);
        Glide.with(context).load(mProfile.getImageUrl()).into(holder.profileImageView);
        holder.nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge() + "   ☑" + "   ⓘ");
        holder.locationNameTxt.setText(mProfile.getLocation());
    }

    @Override
    public int getItemCount() {
        return profile.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView nameAgeTxt, locationNameTxt;

        private ImageView profileImageView, nope, reload, like, superLike, power;
        private FrameLayout cardview;

        public Viewholder(@NonNull View view, final OnItemClickListener listener) {
            super(view);
            nameAgeTxt = view.findViewById(R.id.nameAgeTxt);
            locationNameTxt = view.findViewById(R.id.locationNameTxt);
            profileImageView = view.findViewById(R.id.profileImageView);
            cardview = view.findViewById(R.id.cardview);
            nope = view.findViewById(R.id.nope);
            reload = view.findViewById(R.id.reload);
            like = view.findViewById(R.id.like);
            superLike = view.findViewById(R.id.star);
            power = view.findViewById(R.id.power);


            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            nameAgeTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            nope.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            superLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }
}
