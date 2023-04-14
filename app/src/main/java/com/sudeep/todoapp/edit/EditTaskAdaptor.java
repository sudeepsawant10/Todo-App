package com.sudeep.todoapp.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.sudeep.todoapp.R;

import java.util.ArrayList;

public class EditTaskAdaptor extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<EditTaskModel> editTaskModelArrayList = new ArrayList<>();
    private OnTaskCheckedClickListener onTaskCheckedClickListener;

    public EditTaskAdaptor(Context context, ArrayList<EditTaskModel> editTaskModelArrayList, OnTaskCheckedClickListener onTaskCheckedClickListener) {
        this.context = context;
        this.editTaskModelArrayList = editTaskModelArrayList;
        this.onTaskCheckedClickListener = onTaskCheckedClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_edit_task_design, parent, false);
        return new EditTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EditTaskModel editTask = editTaskModelArrayList.get(position);
        EditTaskViewHolder editTaskViewHolder = (EditTaskViewHolder) holder;
        editTaskViewHolder.checkBoxTask.setText(editTask.getTaskName());
        Boolean isTaskDone = editTask.isTaskDone();
        editTaskViewHolder.checkBoxTask.setChecked(isTaskDone);
        editTaskViewHolder.checkBoxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onTaskCheckedClickListener.onTaskCheckedClick(editTask, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return editTaskModelArrayList.size();
    }

    public class EditTaskViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxTask;
        public EditTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkboxTask);
        }
    }

    public interface OnTaskCheckedClickListener{
        public void onTaskCheckedClick(EditTaskModel editTask, Boolean isDone);
    }

}
