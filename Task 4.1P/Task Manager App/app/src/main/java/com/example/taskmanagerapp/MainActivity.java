package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<Task> taskList;
    private TaskDatabaseHelper dbHelper;
    private ActivityResultLauncher<Intent> addEditTaskLauncher;

    private static final int REQUEST_ADD_EDIT = 1; // This constant is no longer directly needed with ActivityResultLauncher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new TaskDatabaseHelper(this);

        taskList = dbHelper.getAllTasks();
        adapter = new TaskAdapter(this, taskList, addEditTaskLauncher);
        recyclerView.setAdapter(adapter);

        // Initialize and register the ActivityResultLauncher
        addEditTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // The AddEditTask activity finished successfully (task added or updated)
                        reloadTasks();
                    }
                }
        );

        adapter = new TaskAdapter(this, taskList, addEditTaskLauncher); // Pass the launcher here
        recyclerView.setAdapter(adapter);

        Button addNewTaskButton = findViewById(R.id.addNewTaskButton);
        addNewTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTask.class);
            addEditTaskLauncher.launch(intent);
        });
    }


    private void reloadTasks() {
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }
}