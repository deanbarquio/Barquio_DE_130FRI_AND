package com.capstone.todolistapplication;

public class Task {

    private String taskName;
    private boolean isChecked;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isChecked = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
