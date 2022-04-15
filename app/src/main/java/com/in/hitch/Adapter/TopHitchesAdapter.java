package com.in.hitch.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.hitch.Model.TopHitchesModel;
import com.in.hitch.Model.WhoLikesYouModel;
import com.in.hitch.R;

import java.util.ArrayList;
import java.util.List;

public class TopHitchesAdapter extends RecyclerView.Adapter<TopHitchesAdapter.ViewHolder> {
    List<WhoLikesYouModel.WhoLikesYou> list = new ArrayList<>();
    Context context;
    Click click;


    public interface Click {

        void onItemClick(int position);
    }

    public TopHitchesAdapter(List<WhoLikesYouModel.WhoLikesYou> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public TopHitchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_hitches_item, parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull TopHitchesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        WhoLikesYouModel.WhoLikesYou model = list.get(position);


        Glide.with(context).load(model.getImage()).into(holder.TopHitchesProfile);

        holder.TopHitchesName.setText(model.getFirst_name()+", "+model.getAge());
        holder.TopHitchesDistance.setText(model.getKm_diff() + " KM");

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
        ImageView TopHitchesProfile;
        TextView TopHitchesName, TopHitchesDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            TopHitchesProfile = itemView.findViewById(R.id.TopHitchesProfile);

            TopHitchesName = itemView.findViewById(R.id.TopHitchesName);
            TopHitchesDistance = itemView.findViewById(R.id.TopHitchesDistance);


        }
    }
}