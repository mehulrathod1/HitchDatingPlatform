package com.in.hitch.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.hitch.Model.TransactionModel;
import com.in.hitch.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    List<TransactionModel.TransactionHistory> list = new ArrayList<>();
    Context context;
    Click click;

    public TransactionAdapter(List<TransactionModel.TransactionHistory> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    public interface Click {
        void onItemClick(int position);

    }

    @NonNull

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pyment_history_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TransactionModel.TransactionHistory model = list.get(position);

        holder.transactionId.setText(model.getTransaction_id());
        holder.desc.setText(model.getDescription());
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());

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

        TextView transactionId, desc, amount, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionId = itemView.findViewById(R.id.transactionId);
            desc = itemView.findViewById(R.id.desc);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
        }
    }
}
