package com.capstone.todolistapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends android.widget.ArrayAdapter<Task> {

    private final Activity context;
    private final ArrayList<Task> tasks;

    public TaskAdapter(Activity context, ArrayList<Task> tasks) {
        super(context, R.layout.list_item_task, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_task, null, true);

        TextView taskNameView = rowView.findViewById(R.id.taskName);
        ImageView taskImageView = rowView.findViewById(R.id.taskImage);
        CheckBox checkBox = rowView.findViewById(R.id.checkbox);

        Task currentTask = tasks.get(position);

        taskNameView.setText(currentTask.getTaskName());
        taskImageView.setImageResource(R.drawable.pencil);

        checkBox.setChecked(currentTask.isChecked());


        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setChecked(isChecked);
        });

        return rowView;
    }
}
