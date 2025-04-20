package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView titleView = view.findViewById(R.id.textView);
        TextView descriptionView = view.findViewById(R.id.textView2);
        RecyclerView relatedRecycler = view.findViewById(R.id.relatedStoriesRecyclerView);

        Bundle args = getArguments();
        if (args != null) {
            titleView.setText(args.getString("title"));
            descriptionView.setText(args.getString("description"));
            imageView.setImageResource(args.getInt("image"));
        }

        List<NewsData> relatedList = Arrays.asList(
                new NewsData("Breaking News 1", R.drawable.placeholder, "Breaking News Summary 1"),
                new NewsData("Artificial Intelligence", R.drawable.ai, "Artificial Intelligence and Machine Leaning"),
                new NewsData("Pixel 9a", R.drawable.pixel, "The Latest Pixel Phone from Google")
        );

        relatedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        RelatedNewsAdapter adapter = new RelatedNewsAdapter(getContext(), relatedList, item -> {
            // Optional: handle clicking on related news
        });
        relatedRecycler.setAdapter(adapter);
    }
}