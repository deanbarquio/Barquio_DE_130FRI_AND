package com.capstone.todolistapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private EditText taskInput;
    private Button addButton;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen mode
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        taskInput = findViewById(R.id.taskInput);
        addButton = findViewById(R.id.addButton);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

        addButton.setOnClickListener(v -> addTask());

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int position = listView.pointToPosition((int) e.getX(), (int) e.getY());
                if (position != AdapterView.INVALID_POSITION) {
                    showEditDeleteDialog(position);
                }
                return true;
            }
        });

        listView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void addTask() {
        String taskText = taskInput.getText().toString();
        if (!taskText.isEmpty()) {
            Task task = new Task(taskText);
            taskList.add(task);
            taskAdapter.notifyDataSetChanged();
            taskInput.setText("");
        } else {
            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Task");

        builder.setPositiveButton("Edit", (dialog, which) -> showEditTaskDialog(position));

        builder.setNegativeButton("Delete", (dialog, which) -> showDeleteConfirmationDialog(position));

        builder.create().show();
    }

    private void showEditTaskDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_task, null);
        builder.setView(viewInflated);

        final EditText taskInputEdit = viewInflated.findViewById(R.id.taskInputEdit);
        taskInputEdit.setText(taskList.get(position).getTaskName());

        builder.setPositiveButton("Update", (dialog, which) -> {
            String updatedTaskText = taskInputEdit.getText().toString();
            if (!updatedTaskText.isEmpty()) {
                taskList.get(position).setTaskName(updatedTaskText);
                taskAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            taskList.remove(position);
            taskAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("No", null);
        builder.create().show();
    }
}
