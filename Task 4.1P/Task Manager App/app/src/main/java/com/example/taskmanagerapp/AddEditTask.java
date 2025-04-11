package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditTask extends AppCompatActivity {

    private EditText titleInput, descriptionInput, dateInput;
    private Button saveButton, deleteButton, markAsDoneButton;

    private TaskDatabaseHelper dbHelper;
    private Task existingTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        titleInput = findViewById(R.id.editTextText);
        descriptionInput = findViewById(R.id.editTextTextMultiLine);
        dateInput = findViewById(R.id.editTextDate);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteTaskButton);

        dbHelper = new TaskDatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("task")) {
            existingTask = (Task) intent.getSerializableExtra("task");
            titleInput.setText(existingTask.getTitle());
            descriptionInput.setText(existingTask.getDescription());
            dateInput.setText(existingTask.getDueDate());

            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.GONE);
        }

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();
            String dueDate = dateInput.getText().toString().trim();

            if (title.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Title and Due Date are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (existingTask != null) {
                existingTask.setTitle(title);
                existingTask.setDescription(description);
                existingTask.setDueDate(dueDate);
                dbHelper.updateTask(existingTask);
                Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
            } else {
                Task newTask = new Task(title, description, dueDate, false);
                dbHelper.addTask(newTask);
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            }

            setResult(RESULT_OK);
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            if (existingTask != null) {
                dbHelper.deleteTask(existingTask.getId());
                Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}