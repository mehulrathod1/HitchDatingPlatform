package com.in.hitch.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.hitch.Model.ChatDashboardModel;
import com.in.hitch.R;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    List<ChatDashboardModel.ChatDashboardList> list = new ArrayList<>();
    Context context;
    longClick longClick;

    public interface longClick {
        void onItemClick(int position);

        void onClick(int position);
    }

    public DashboardAdapter(List<ChatDashboardModel.ChatDashboardList> list, Context context, DashboardAdapter.longClick longClick) {
        this.list = list;
        this.context = context;
        this.longClick = longClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_dashboare_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ChatDashboardModel.ChatDashboardList model = list.get(position);

        holder.date_layout.setVisibility(View.GONE);
        holder.receivedLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.sendLayout.setBackgroundColor(Color.parseColor("#ffffff"));


        Glide.with(context)
                .load(model.getImage())
                .into(holder.userImage);

        if (model.getType().equals("received")) {

//            StringEscapeUtils.unescapeJava(serverResponse);
            holder.receivedMessage.setText(StringEscapeUtils.unescapeJava(model.getMessage()));
//            holder.receivedMessage.setText(model.getMessage());
            holder.receivedMessageTime.setText(model.getTime());
            holder.second_chat.setVisibility(View.GONE);


        }
        if (model.getType().equals("sent")) {

            holder.send_message.setText(StringEscapeUtils.unescapeJava(model.getMessage()));

//            holder.send_message.setText(model.getMessage());
            holder.sendMessageTime.setText(model.getTime());
            holder.first_chat.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                longClick.onItemClick(position);


                holder.receivedLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                holder.sendLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));

                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longClick.onClick(position);

                holder.receivedLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.sendLayout.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView receivedMessage, receivedMessageTime, send_message, sendMessageTime;
        RelativeLayout first_chat, second_chat;
        LinearLayout date_layout, receivedLayout, sendLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            receivedMessage = itemView.findViewById(R.id.receivedMessage);
            receivedMessageTime = itemView.findViewById(R.id.receivedMessageTime);
            send_message = itemView.findViewById(R.id.send_message);
            sendMessageTime = itemView.findViewById(R.id.sendMessageTime);
            first_chat = itemView.findViewById(R.id.first_chat);
            date_layout = itemView.findViewById(R.id.date_layout);
            second_chat = itemView.findViewById(R.id.second_chat);
            receivedLayout = itemView.findViewById(R.id.receivedLayout);
            sendLayout = itemView.findViewById(R.id.sendLayout);
        }
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
}
