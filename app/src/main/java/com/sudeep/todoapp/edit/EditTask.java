package com.sudeep.todoapp.edit;



import static com.sudeep.todoapp.home.MainActivity.C_KEY_CHECKLIST_DATE;
import static com.sudeep.todoapp.home.MainActivity.C_KEY_CHECKLIST_ID;
import static com.sudeep.todoapp.home.MainActivity.C_KEY_CHECKLIST_TOPIC_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sudeep.todoapp.R;
import com.sudeep.todoapp.firebase.FirebaseDbManger;
import com.sudeep.todoapp.home.HomeCheckListModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTask extends AppCompatActivity {
    Context context = this;
    private EditText etCheckListTopicName;
    private Button btnSave;
    private TextView tvEditDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etCheckListTopicName = findViewById(R.id.etCheckListTopicName);
        btnSave = findViewById(R.id.btnSave);
        tvEditDate = findViewById(R.id.tvEditDate);

//       from Home Main Activity on checklist clicked
        Intent intent = getIntent();
        if (intent != null){
            String checkListId = intent.getStringExtra(C_KEY_CHECKLIST_ID);
            String checkListTopicName = intent.getStringExtra(C_KEY_CHECKLIST_TOPIC_NAME);
            String checkListDate = intent.getStringExtra(C_KEY_CHECKLIST_DATE);
            etCheckListTopicName.setText(checkListTopicName);
            tvEditDate.setText(checkListDate);
        }

        // save the latest/updated checklist
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkListTopicName = etCheckListTopicName.getText().toString();

//              Generate id with checklistname and date for checklist and add to db
                long randomId = System.currentTimeMillis();
//              StringBuilder is just like arraylist changeable
                StringBuilder randomId_checkListName;
//              concatenating randomId with checkList name with underscore
                randomId_checkListName = new StringBuilder(String.valueOf(randomId));

                /*
                randomId_chckListName (for db)
                checkListId (for subTask)

                 */

                // Take checkList name sperated by space make it lowercased and add underscore between them
                // and append with randomId the whole will be checkList id
                String[] words = checkListTopicName.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    // You may want to check for a non-word character before blindly
                    // performing a replacement
                    // It may also be necessary to adjust the character class
                    words[i] = words[i].replaceAll("[^\\w]", "").toLowerCase(Locale.ROOT);
                    randomId_checkListName.append("_").append(words[i]);
                }
                // convert id from StringBuilder to String
                String  randomId_checkListName_string = String.valueOf(randomId_checkListName);

//              create  checklist of user with id and add into db
                HomeCheckListModel checkListModel = new HomeCheckListModel();
                checkListModel.setCheckListId(randomId);
                checkListModel.setCheckListTopicName(checkListTopicName);

                //Get current data and set
                Date c = Calendar.getInstance().getTime();
//                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                checkListModel.setDate(formattedDate);

//              Add to db, FirebaseDbCallbackInterface to handle the activity things after data added into db
                FirebaseDbManger.addCheckListToDb(context, randomId_checkListName_string, checkListModel, new FirebaseDbManger.FirebaseDbCallbackInterface() {
                    @Override
                    public void onComplete(Object object) {
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
        });

    }
}