package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerTopStories = view.findViewById(R.id.topStoriesRecyclerView);
        RecyclerView recyclerNews = view.findViewById(R.id.newsRecyclerView);

        // Sample data
        List<NewsData> topStories = Arrays.asList(
                new NewsData("Breaking News 1", R.drawable.placeholder, "Breaking News Summary 1"),
                new NewsData("Breaking New 2", R.drawable.bn, "Breaking News Summary 2"),
                new NewsData("Breaking News 3", R.drawable.bn2, "Breaking News Summary 3")
        );

        List<NewsData> newsList = Arrays.asList(
                new NewsData("How artificial intelligence is driving a new wave of self-help bots", R.drawable.ai, "AI chatbots like ChatGPT have quickly become embedded in modern life, and Australians aren't just using them for menial tasks.\n" +
                        "\n" +
                        "Increasingly, people are turning to AI to help them process thoughts and emotions too.\n" +
                        "\n" +
                        "Their lifelike ability to answer questions, engage in conversations and integrate into intimate spaces is driving a new wave of self-help bots."),
                new NewsData("NVIDIA RTX 5060 family", R.drawable.nvidia, "Nvidia has officially announced the latest generation of its GeForce RTX GPUs, the RTX 5060 Family. This new family promises to bring the company’s cutting-edge Blackwell architecture and its suite of advancements to a broader audience of gamers.\n" +
                        "\n"),
                new NewsData("The Pixel 9a is a top-tier device with a reasonable price", R.drawable.pixel, "When it comes to entry-level smartphones, the Pixel 9a is one of the best deals out there right now. Thanks to its great-looking 120Hz display, solid build quality (including a great-looking camera bump), excellent photography performance, and of course, its very reasonable price, the Pixel 9a is the complete package. While the iPhone 16e is a solid smartphone, the Pixel 9a surpasses it in nearly every category as long as you're cool with Google's Android ecosystem."),
                new NewsData("Audi's baby supercar", R.drawable.audi, "The updated 2025 Audi RS3 Sportback and Sedan are due to arrive in Australian showrooms in August, completing the compact A3 family.\n" +
                        "\n" +
                        "Speaking with CarExpert at the launch of the new A3 and S3, senior executive for corporate communications at Audi Australia, Claudia Muller, confirmed the facelifted RS3's local timing."),
                new NewsData("New minimum broadband speed proposal", R.drawable.broadband, "Faster baseline broadband services could be on the horizon for all Australians.\n" +
                "The federal Department of Communications is pushing a proposal that aims to increase minimum broadband speeds from 25Mbps to 100Mbps.\n" +
                "\n")
        );

        // Setup Top Stories
        recyclerTopStories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        TopStoriesAdapter topAdapter = new TopStoriesAdapter(getContext(), topStories, newsItem -> openDetailFragment(newsItem));
        recyclerTopStories.setAdapter(topAdapter);

        // Setup News
        recyclerNews.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2-column grid
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), newsList, newsItem -> openDetailFragment(newsItem));
        recyclerNews.setAdapter(newsAdapter);
    }

    private void openDetailFragment(NewsData newsItem) {
        Bundle bundle = new Bundle();
        bundle.putString("title", newsItem.getTitle());
        bundle.putInt("image", newsItem.getImageResId());
        bundle.putString("description", newsItem.getDescription());

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit();
    }
}