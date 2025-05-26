package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.personalizedlearningexperiencesapp.db.UserDatabaseHelper;
import com.example.personalizedlearningexperiencesapp.adapters.HistoryAdapter;
import com.example.personalizedlearningexperiencesapp.model.HistoryItem;
import java.util.List;

import com.example.personalizedlearningexperiencesapp.R;

public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        String username = args != null ? args.getString("username", "guest") : "guest";

        Log.d("HistoryFragment", "Loading history for: " + username);

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
        List<HistoryItem> historyItems = dbHelper.getQuizHistoryForUser(username);
        Log.d("HistoryQuery", "Found " + historyItems.size() + " results for " + username);

        HistoryAdapter adapter = new HistoryAdapter(historyItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
}