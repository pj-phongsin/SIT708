package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.RelatedNewsViewHolder> {

    private Context context;
    private List<NewsData> relatedNewsList;
    private OnItemClickListener listener;

    public RelatedNewsAdapter(Context context, List<NewsData> relatedNewsList, OnItemClickListener listener) {
        this.context = context;
        this.relatedNewsList = relatedNewsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.related_news, parent, false);
        return new RelatedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedNewsViewHolder holder, int position) {
        NewsData item = relatedNewsList.get(position);
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onRelatedNewsClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return relatedNewsList.size();
    }

    public static class RelatedNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public RelatedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageNews);
            title = itemView.findViewById(R.id.titleNews);
        }
    }

    public interface OnItemClickListener {
        void onRelatedNewsClick(NewsData item);
    }
}