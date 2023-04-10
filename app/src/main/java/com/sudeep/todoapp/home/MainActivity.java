package com.sudeep.todoapp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sudeep.todoapp.R;
import com.sudeep.todoapp.edit.EditTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Write a message to the database
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference myRef = database.getReference("message");
    private Context context = this;
    private ImageView ivAddToList;
    private RecyclerView rvCheckList;
    private HomeCheckListAdapter homeCheckListAdapter;
    private ArrayList<HomeCheckListModel> checkListArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAddToList = findViewById(R.id.ivAddToList);
        rvCheckList = findViewById(R.id.rvCheckList);

        // Set adaptor for recyclerView checkList
        rvCheckList.setLayoutManager(new LinearLayoutManager(context));
        // takes activity and datalist


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        HomeCheckListModel homeCheckListModel= new HomeCheckListModel(123,"Lecuture 1",  formattedDate);
        HomeCheckListModel homeCheckListModel2= new HomeCheckListModel(124,"Lecuture 2",  formattedDate);
        HomeCheckListModel homeCheckListModel3= new HomeCheckListModel(125,"College Lecuture 2",  formattedDate);
        HomeCheckListModel homeCheckListModel4= new HomeCheckListModel(126,"Lecuture 2",  formattedDate);
        checkListArrayList.add(homeCheckListModel);
        checkListArrayList.add(homeCheckListModel2);
        checkListArrayList.add(homeCheckListModel3);
        checkListArrayList.add(homeCheckListModel4);

        homeCheckListAdapter = new HomeCheckListAdapter(context, checkListArrayList);
        rvCheckList.setAdapter(homeCheckListAdapter);

        ivAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTask.class);
                startActivity(intent);
            }
        });



    }
}