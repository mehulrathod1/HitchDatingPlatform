package com.in.hitch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.hitch.Model.WhoLikesYouModel;
import com.in.hitch.R;

import java.util.ArrayList;
import java.util.List;

public class WhoLikeYouAdapter extends RecyclerView.Adapter<WhoLikeYouAdapter.ViewHolder> {

    List<WhoLikesYouModel> list = new ArrayList<>();
    Context context;
    Click click;

    public WhoLikeYouAdapter(List<WhoLikesYouModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    public interface Click {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public WhoLikeYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.who_likes_you_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WhoLikeYouAdapter.ViewHolder holder, int position) {

        WhoLikesYouModel model = list.get(position);

        holder.topHitchesProfile.setImageResource(model.getTopHitchesProfile());

        holder.topHitchesCancel.setImageResource(model.getTopHitchesCancel());
        holder.topHitchesLike.setImageResource(model.getTopHitchesLike());
        holder.topHitchesName.setText(model.getTopHitchesName());
        holder.topHitchesDistance.setText(model.getTopHitchesDistance());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 click.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView topHitchesProfile, topHitchesCancel, topHitchesLike;
        TextView topHitchesName, topHitchesDistance;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            topHitchesProfile = itemView.findViewById(R.id.topHitchesProfile);
            topHitchesCancel = itemView.findViewById(R.id.topHitchesCancel);
            topHitchesLike = itemView.findViewById(R.id.topHitchesLike);

            topHitchesName = itemView.findViewById(R.id.topHitchesName);
            topHitchesDistance = itemView.findViewById(R.id.topHitchesDistance);

        }
    }
}
