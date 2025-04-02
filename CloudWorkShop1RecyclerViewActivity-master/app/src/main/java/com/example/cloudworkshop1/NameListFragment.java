package com.example.cloudworkshop1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class NameListFragment extends Fragment {

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name_list, container, false);

        ArrayList<Name> nameList = new ArrayList<>();
        nameList.add(new Name("PJ"));
        nameList.add(new Name("PJ"));
        nameList.add(new Name("PJ"));
        nameList.add(new Name("PJ"));
        nameList.add(new Name("PJ"));
        nameList.add(new Name("PJ"));
        nameList.add(new Name("PJ"));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set Adapter
        //myAdapter = new MyAdapter(this, dataNames);

        myAdapter = new MyAdapter(getContext(), nameList);
        recyclerView.setAdapter(myAdapter);

        return view;
    }
}