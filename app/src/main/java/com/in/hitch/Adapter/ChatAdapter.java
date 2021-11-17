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
import com.in.hitch.Model.ChatModel;
import com.in.hitch.R;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<ChatModel.ChatModelData> list = new ArrayList<>();
    Context context;
    Click click;


    public interface Click {
        void onItemClick(int position);
    }

    public ChatAdapter(List<ChatModel.ChatModelData> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ChatModel.ChatModelData model = list.get(position);

//        holder.itemImage.setImageResource(model.getItemImage());
        holder.name.setText(model.getUsername());
        holder.message.setText(StringEscapeUtils.unescapeJava(model.getMessage()));
        holder.date.setText(model.getDatetime());
        holder.NoOfMessage.setText(model.getUnread_msg());

        if (model.getUnread_msg().equals("0")) {
            holder.NoOfMessage.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(model.getImage())
                .into(holder.itemImage);

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
        ImageView itemImage;
        TextView name, message, date, NoOfMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            NoOfMessage = itemView.findViewById(R.id.NoOfMessage);

        }
    }
}
