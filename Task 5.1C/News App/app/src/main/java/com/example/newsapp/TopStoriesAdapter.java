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

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.TopStoryViewHolder> {

    private Context context;
    private List<NewsData> topStories;
    private OnItemClickListener listener;

    public TopStoriesAdapter(Context context, List<NewsData> topStories, OnItemClickListener listener) {
        this.context = context;
        this.topStories = topStories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_stories, parent, false);
        return new TopStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoryViewHolder holder, int position) {
        NewsData item = topStories.get(position);
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTopStoryClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }

    public static class TopStoryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public TopStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageTopStory);
            title = itemView.findViewById(R.id.titleTopStory);
        }
    }

    public interface OnItemClickListener {
        void onTopStoryClick(NewsData item);
    }
}