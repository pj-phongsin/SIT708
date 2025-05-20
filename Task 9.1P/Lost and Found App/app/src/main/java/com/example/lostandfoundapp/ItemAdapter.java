package com.example.lostandfoundapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfoundapp.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Item> itemList;

    public ItemAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.textType.setText(item.getType() + " - " + item.getName());
        holder.textPhone.setText("Phone: " + item.getPhone());
        holder.textDescription.setText(item.getDescription());
        holder.textDate.setText("Date: " + item.getDate());
        holder.textLocation.setText("Location: " + item.getLocation());

        // Launch detail screen on item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("type", item.getType());
            intent.putExtra("name", item.getName());
            intent.putExtra("phone", item.getPhone());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("date", item.getDate());
            intent.putExtra("location", item.getLocation());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textType, textPhone, textDescription, textDate, textLocation;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textType = itemView.findViewById(R.id.textType);
            textPhone = itemView.findViewById(R.id.textPhone);
            textDescription = itemView.findViewById(R.id.textDescription);
            textDate = itemView.findViewById(R.id.textDate);
            textLocation = itemView.findViewById(R.id.textLocation);
        }
    }
}