package com.example.planit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button addButton;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<String> tasks;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTask);
        addButton = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerViewTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("ToDoList", Context.MODE_PRIVATE);
        tasks = new ArrayList<>(loadTasks());

        adapter = new TaskAdapter(tasks, this);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String task = editText.getText().toString().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                adapter.notifyItemInserted(tasks.size() - 1);
                saveTasks();
                editText.setText("");
                Toast.makeText(MainActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a task!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTasks() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> taskSet = new HashSet<>(tasks);
        editor.putStringSet("tasks", taskSet);
        editor.apply();
    }

    private Set<String> loadTasks() {
        return sharedPreferences.getStringSet("tasks", new HashSet<>());
    }
}
