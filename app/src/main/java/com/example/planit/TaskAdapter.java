package com.example.planit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final ArrayList<String> tasks;
    private final Context context;

    public TaskAdapter(ArrayList<String> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = tasks.get(position);
        holder.taskText.setText(task);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(context, "Task Completed: " + task, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            tasks.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Task Deleted: " + task, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.textViewTask);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}

