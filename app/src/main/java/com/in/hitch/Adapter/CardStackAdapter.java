package com.in.hitch.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.hitch.Model.ItemModel;
import com.in.hitch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<ItemModel> items;
    Context context;
    Click click;


    public interface Click{
        void onClickItem(int position);
        void onClickReload(int position);
        void onClickNope(int position);
        void onClickLike(int position);
        void onClickSuperLike(int position);
    }

    public CardStackAdapter(List<ItemModel> items, Context context, Click click) {
        this.items = items;
        this.context = context;
        this.click = click;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemModel itemModel = items.get(position);
        holder.setData(items.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickItem(position);
            }
        });

        holder.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickReload(position);
            }
        });
        holder.nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickNope(position);
            }
        });
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickSuperLike(position);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClickLike(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image,reload,nope,star,like,power;
        TextView nama, usia, kota;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            nama = itemView.findViewById(R.id.item_name);
            usia = itemView.findViewById(R.id.item_age);
            kota = itemView.findViewById(R.id.item_city);

            reload = itemView.findViewById(R.id.reload);
            nope = itemView.findViewById(R.id.nope);
            star = itemView.findViewById(R.id.star);
            like = itemView.findViewById(R.id.like);
            power = itemView.findViewById(R.id.power);
        }

        void setData(ItemModel data) {
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .centerCrop()
                    .into(image);
            nama.setText(data.getNama());
            usia.setText(data.getUsia());
            kota.setText(data.getKota());
        }
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
