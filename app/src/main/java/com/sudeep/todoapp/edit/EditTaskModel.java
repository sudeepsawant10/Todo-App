package com.sudeep.todoapp.edit;

public class EditTaskModel {
    long task_id;
    String taskName;
    boolean is_taskDone;

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

    public boolean isIs_taskDone() {
        return is_taskDone;
    }

    public void setIs_taskDone(boolean is_taskDone) {
        this.is_taskDone = is_taskDone;
    }
}
