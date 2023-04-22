package com.sudeep.todoapp.edit;



import static com.sudeep.todoapp.home.MainActivity.C_KEY_CHECKLIST_DATE;
import static com.sudeep.todoapp.home.MainActivity.C_KEY_CHECKLIST_ID;
import static com.sudeep.todoapp.home.MainActivity.C_KEY_CHECKLIST_TOPIC_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sudeep.todoapp.R;
import com.sudeep.todoapp.firebase.FirebaseDbManger;
import com.sudeep.todoapp.home.HomeCheckListModel;
import com.sudeep.todoapp.util.UserAccountManager;
import com.sudeep.todoapp.util.ValidationHelper;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTask extends AppCompatActivity implements EditTaskAdaptor.OnTaskCheckedClickListener{
    private static final String TAG = "EDITTASK_ACTIVITY";
    private Context context = this;
    private EditText etCheckListTopicName, etCheckListTask;
    private Button btnSave, btnAddTask;
    private TextView tvEditDate;
    private RecyclerView rvTaskList;
    private EditTaskAdaptor editTaskAdaptor;
    private ArrayList<EditTaskModel> editTaskModelArrayList = new ArrayList<>();
    private Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etCheckListTopicName = findViewById(R.id.etCheckListTopicName);
        btnSave = findViewById(R.id.btnSave);
        tvEditDate = findViewById(R.id.tvEditDate);
        btnAddTask = findViewById(R.id.btnAddTask);
        etCheckListTask = findViewById(R.id.etCheckListTask);

//      Recycler View task list set adaptor
        rvTaskList = findViewById(R.id.rvTaskList);
        rvTaskList.setLayoutManager(new LinearLayoutManager(context));

        editTaskAdaptor = new EditTaskAdaptor(context, editTaskModelArrayList,this);
        rvTaskList.setAdapter(editTaskAdaptor);

//      from Home Main Activity on checklist clicked
        intent = getIntent();
        if (intent != null){
            String checkListId = intent.getStringExtra(C_KEY_CHECKLIST_ID);
            String checkListTopicName = intent.getStringExtra(C_KEY_CHECKLIST_TOPIC_NAME);
            String checkListDate = intent.getStringExtra(C_KEY_CHECKLIST_DATE);
            etCheckListTopicName.setText(checkListTopicName);
            tvEditDate.setText(checkListDate);

            if (checkListId!=null){
//              get current checklist tasks data from intent and set tasks on the screen
                FirebaseDbManger.retrieveAllCheckListTasks(context, checkListId, new FirebaseDbManger.FirebaseDbCallbackInterface() {
                    @Override
                    public void onComplete(Object object) {
                        if (object == null) {
                            Toast.makeText(context, "No task exist", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            // To add db data into array list
                            ArrayList<EditTaskModel> taskModelArrayList = new ArrayList<>();
                            Iterable<DataSnapshot> taskListChildren = (Iterable<DataSnapshot>) object;

                            for (DataSnapshot singleChildren: taskListChildren) {
                                EditTaskModel singleTaskList = singleChildren.getValue(EditTaskModel.class);
                                taskModelArrayList.add(singleTaskList);
                            }

                            editTaskModelArrayList.clear();
                            editTaskModelArrayList.addAll(taskModelArrayList);
                            editTaskAdaptor.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        }

        // save the latest/updated checklist
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdateCheckList();
            }
        });

//      On add button clicked save task to that checklist
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text and add to taskListArrayList
                saveCheckListTask();
            }
        });

    }

    private void saveCheckListTask() {
        String taskName = etCheckListTask.getText().toString();
        long randomTaskId = System.currentTimeMillis();

        if (ValidationHelper.isTextEmpty(taskName)){
            Toast.makeText(context, "Please enter the task!", Toast.LENGTH_SHORT).show();
        } else {
            EditTaskModel editTaskModel = new EditTaskModel();
            editTaskModel.setTask_id(randomTaskId);
            editTaskModel.setTaskName(taskName);
            editTaskModel.setTaskDone(false);

            if (intent != null){
                String checkListId = intent.getStringExtra(C_KEY_CHECKLIST_ID);
                String checkListTopicName = intent.getStringExtra(C_KEY_CHECKLIST_TOPIC_NAME);
                FirebaseDbManger.addTaskToDb(context, checkListId, editTaskModel, new FirebaseDbManger.FirebaseDbCallbackInterface() {
                    @Override
                    public void onComplete(Object object) {
                        etCheckListTask.setText("");
                        etCheckListTask.clearFocus();
                        Toast.makeText(context, "Task added into checklist", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Failed to add Task", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(context, "Failed to add Task", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    private void saveUpdateCheckList() {
        String checkListTopicName = etCheckListTopicName.getText().toString();

        if (ValidationHelper.isTextEmpty(checkListTopicName)){
            Toast.makeText(context, "Please Enter Checklist Name", Toast.LENGTH_SHORT).show();
        }
        else {
//            CREATE NEW CHECKLIST
            // Generate id with checklistname and date for checklist and add to db
            long randomId = System.currentTimeMillis();

//          StringBuilder is just like arraylist changeable
//          concatenating randomId with checkList name with underscore
            String randomIdChecklist = String.valueOf(randomId);

//          create  checklist of user with id and add into db
            HomeCheckListModel checkListModel = new HomeCheckListModel();
            checkListModel.setCheckListId(randomId);
            checkListModel.setCheckListTopicName(checkListTopicName);

//          get logged in user_id and set to checklist model
            String user_id = UserAccountManager.getUserId(context);
            checkListModel.setUser_id(user_id);


            //Get current data and set
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            checkListModel.setDate(formattedDate);

//          Add to db, FirebaseDbCallbackInterface to handle the activity things after data added into db on save button clicked
            FirebaseDbManger.addCheckListToDb(context, randomIdChecklist, checkListModel, new FirebaseDbManger.FirebaseDbCallbackInterface() {
                @Override
                public void onComplete(Object object) {
                    etCheckListTopicName.setText("");
                    etCheckListTopicName.clearFocus();
                    Toast.makeText(context, "CheckList Added", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError() {
                    Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(context, "Failed to Adde", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onTaskCheckedClick(EditTaskModel editTask, Boolean isDone) {
        if(intent != null){
            String checkListId = intent.getStringExtra(C_KEY_CHECKLIST_ID);
            if (checkListId != null){
                FirebaseDbManger.markTaskDone(context, checkListId, editTask, isDone, new FirebaseDbManger.FirebaseDbCallbackInterface() {
                    @Override
                    public void onComplete(Object object) {
                        if (isDone == true){
                            Toast.makeText(context, editTask.getTaskName()+" task completed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Task incomplete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "onError: Failed to update task");
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "onError: Failed to update task");
                    }
                });
            }
        }
    }

    public String buildRandomId_checkListName_string(String checkListId, String checkListTopicName){
        if (checkListTopicName == null){
            return checkListId;
        }
        String randomId_checkListName_string;
        StringBuilder randomId_checkListName = new StringBuilder(String.valueOf(checkListId));
        String[] words = checkListTopicName.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "").toLowerCase(Locale.ROOT);
            randomId_checkListName.append("_").append(words[i]);
        }
        // convert id from StringBuilder to String
        randomId_checkListName_string = String.valueOf(randomId_checkListName);
        return randomId_checkListName_string;
    }
}