package com.example.itubeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private Context context;
    private List<String> playlist;

    public PlaylistAdapter(Context context, List<String> playlist) {
        this.context = context;
        this.playlist = playlist;
    }

    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist_url, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistAdapter.ViewHolder holder, int position) {
        holder.urlText.setText(playlist.get(position));
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urlText;

        public ViewHolder(View itemView) {
            super(itemView);
            urlText = itemView.findViewById(R.id.textViewUrl);
        }
    }
}