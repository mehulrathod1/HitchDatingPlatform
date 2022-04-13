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
import com.in.hitch.Model.MyMatchesModel;
import com.in.hitch.R;

import java.util.ArrayList;
import java.util.List;

public class MyMatchesAdapter extends RecyclerView.Adapter<MyMatchesAdapter.ViewHolder> {

    List<MyMatchesModel.MatchesData> list = new ArrayList<>();
    Context context;
    Click click;

    public MyMatchesAdapter(List<MyMatchesModel.MatchesData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    public interface Click{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_matches_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMatchesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyMatchesModel.MatchesData model = list.get(position);

        Glide.with(context).load(model.getImage()).into(holder.matchImage);

        holder.matchesDistance.setText(model.getKm_diff() + " Km away");
        holder.matchesName.setText(model.getTitle());
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

        ImageView  matchImage,matchesLike;
        TextView matchesName,matchesDistance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            matchImage = itemView.findViewById(R.id.matchImage);
            matchesLike = itemView.findViewById(R.id.matchesLike);
            matchesName = itemView.findViewById(R.id.matchesName);
            matchesDistance = itemView.findViewById(R.id.matchesDistance);
        }
    }
}
