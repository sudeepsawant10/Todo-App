package com.sudeep.todoapp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.sudeep.todoapp.LauncherActivity;
import com.sudeep.todoapp.LoginActivity;
import com.sudeep.todoapp.R;
import com.sudeep.todoapp.SignupActivity;
import com.sudeep.todoapp.edit.EditTask;
import com.sudeep.todoapp.firebase.FirebaseAuthManger;
import com.sudeep.todoapp.firebase.FirebaseDbManger;
import com.sudeep.todoapp.util.Common;
import com.sudeep.todoapp.util.PreferenceHelper;
import com.sudeep.todoapp.util.UserAccountManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeCheckListAdapter.OnSingleCheckListClickListener {

    private Context context = this;
    private ImageView ivAddToList;
    private RecyclerView rvCheckList;
    private HomeCheckListAdapter homeCheckListAdapter;
    private ArrayList<HomeCheckListModel> checkListArrayList = new ArrayList<>();
    // Intent Put Extra Keys
    public static final String C_KEY_CHECKLIST_ID = "C_KEY_CHECKLIST_ID";
    public static final String C_KEY_CHECKLIST_TOPIC_NAME = "C_KEY_CHECKLIST_TOPIC_NAME";
    public static final String C_KEY_CHECKLIST_DATE = "C_KEY_CHECKLIST_DATE";

    private ImageView ivLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAddToList = findViewById(R.id.ivAddToList);
        rvCheckList = findViewById(R.id.rvCheckList);
        // Set adaptor for recyclerView checkList
        rvCheckList.setLayoutManager(new LinearLayoutManager(context));

//      onSingleCheckListClick for every checklist to inflate in recycler view
        homeCheckListAdapter = new HomeCheckListAdapter(context, checkListArrayList, this);
        rvCheckList.setAdapter(homeCheckListAdapter);

        ivAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTask.class);
                startActivity(intent);
            }
        });

        ivLogout = findViewById(R.id.ivLogout);
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuthManger.signOutUser(context);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCheckListUi();
    }

    public void refreshCheckListUi() {

        String userId = UserAccountManager.getUserId(context);
        FirebaseDbManger.retrieveAllUserCheckLists(context, userId, new FirebaseDbManger.FirebaseDbCallbackInterface() {
            @Override
            public void onComplete(Object object) {
                if (object == null){
                    Toast.makeText(context, "No checklist exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Add the db data into array list
                    ArrayList<HomeCheckListModel> checkListArray = new ArrayList<>();
//                  Make object iterable
                    Iterable<DataSnapshot> checkListChildren = (Iterable<DataSnapshot>) object;
//                  get children from data snapshot and add to arraylist
                    for (DataSnapshot singleChildren: checkListChildren){
                        HomeCheckListModel singleCheckList = singleChildren.getValue(HomeCheckListModel.class);
                        checkListArray.add(singleCheckList);
                    }

                    checkListArrayList.clear();
                    checkListArrayList.addAll(checkListArray);
                    homeCheckListAdapter.notifyDataSetChanged();
                    rvCheckList.scrollToPosition(checkListArrayList.size() - 1);
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
    private Intent intent;
    // on checklist click implementation
    //    go to edit screen with data
    @Override
    public void onSingleCheckListClick(HomeCheckListModel homeCheckListModel) {
        String checkListId = String.valueOf(homeCheckListModel.getCheckListId());
        String checkListTopicName = homeCheckListModel.getCheckListTopicName();
        String checkListDate = homeCheckListModel.getDate();
        String date = homeCheckListModel.getDate();
        intent = new Intent(context, EditTask.class);
        intent.putExtra("C_KEY_CHECKLIST_ID", checkListId);
        intent.putExtra("C_KEY_CHECKLIST_TOPIC_NAME", checkListTopicName);
        intent.putExtra("C_KEY_CHECKLIST_DATE", checkListDate);
        startActivity(intent);
    }

    @Override
    public void onSingleLongClickDelete(HomeCheckListModel homeCheckListModel) {
        FirebaseDbManger.deleteCheckList(context, homeCheckListModel, new FirebaseDbManger.FirebaseDbCallbackInterface() {
            @Override
            public void onComplete(Object object) {
//                Intent intent = getIntent();
                intent.removeExtra(C_KEY_CHECKLIST_ID);
                intent.removeExtra(C_KEY_CHECKLIST_TOPIC_NAME);
                intent.removeExtra(C_KEY_CHECKLIST_DATE);

                Toast.makeText(context, "Checklist Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}