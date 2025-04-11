package com.example.taskmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private ArrayList<Task> taskList;
    private final ActivityResultLauncher<Intent> editTaskLauncher;

    // Corrected Constructor: Accepting the ActivityResultLauncher
    public TaskAdapter(Context context, ArrayList<Task> taskList, ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.taskList = taskList;
        this.editTaskLauncher = launcher; // Assign the passed launcher
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, dueDateView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.taskTitle);
            dueDateView = itemView.findViewById(R.id.taskDueDate);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleView.setText(task.getTitle());
        holder.dueDateView.setText("Due: " + task.getDueDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditTask.class);
            intent.putExtra("task", task);
            editTaskLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}