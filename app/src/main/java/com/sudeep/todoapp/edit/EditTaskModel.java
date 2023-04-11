package com.sudeep.todoapp.edit;

public class EditTaskModel {
    long task_id;
    String taskName;
    boolean taskDone =false;

    public EditTaskModel() {
    }

    public EditTaskModel(long task_id, String taskName, boolean taskDone) {
        this.task_id = task_id;
        this.taskName = taskName;
        this.taskDone = taskDone;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }
}
