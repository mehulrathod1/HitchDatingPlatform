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
import com.in.hitch.Model.NotificationModel;
import com.in.hitch.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationModel.NotificationData> list = new ArrayList<>();
    Context context;
    Click click;

    public NotificationAdapter(List<NotificationModel.NotificationData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    public interface Click {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NotificationModel.NotificationData model = list.get(position);


        holder.date.setText(model.getTime());
        holder.notificationText.setText(model.getNotification());

        Glide.with(context)
                .load(model.getProfile_img())
                .into(holder.notificationItemImage);

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

        ImageView notificationItemImage;
        TextView notificationText, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationItemImage = itemView.findViewById(R.id.notificationItemImage);
            notificationText = itemView.findViewById(R.id.notificationText);
            date = itemView.findViewById(R.id.date);

        }
    }
}
