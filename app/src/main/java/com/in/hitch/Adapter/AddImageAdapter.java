package com.in.hitch.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.hitch.Model.GetUserImageModel;
import com.in.hitch.R;

import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolde> {

    List<GetUserImageModel.GetImageModel> list;
    Context context;
    Click click;

    public AddImageAdapter(List<GetUserImageModel.GetImageModel> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    public interface Click {
        void onItemClick(int position);

        void onAddImageClick(int position);
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_photos_item, parent, false);
        return new ViewHolde(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, @SuppressLint("RecyclerView") int position) {
        GetUserImageModel.GetImageModel model = list.get(position);

        Glide.with(context)
                .load(model.getImage_name())
                .into(holder.imageView);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onAddImageClick(position);
            }
        });
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

    public class ViewHolde extends RecyclerView.ViewHolder {

        ImageView imageView, edit;

        public ViewHolde(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
