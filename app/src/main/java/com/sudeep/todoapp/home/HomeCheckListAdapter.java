package com.sudeep.todoapp.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudeep.todoapp.R;

import java.util.ArrayList;


public class HomeCheckListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<HomeCheckListModel> checkListArrayList = new ArrayList<>();
//    we have declare the listener method in this class so do not impelment
    private OnSingleCheckListClickListener onSingleCheckListClickListener;


    public HomeCheckListAdapter(Context context, ArrayList<HomeCheckListModel> checkListArrayList, OnSingleCheckListClickListener onSingleCheckListClickListener) {
        this.context = context;
        this.checkListArrayList = checkListArrayList;
        this.onSingleCheckListClickListener = onSingleCheckListClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate recycler view design on main screen
        View view = LayoutInflater.from(context).inflate(R.layout.rv_checklist_design, parent, false);
        return new HomeCheckListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // get ArrayList one by one to set data on ViewHolder
        HomeCheckListModel homeCheckListModel = checkListArrayList.get(position);
        HomeCheckListViewHolder homeCheckListViewHolder = (HomeCheckListViewHolder) holder;
        homeCheckListViewHolder.tvTopicName.setText(homeCheckListModel.getCheckListTopicName());
        homeCheckListViewHolder.tvCheckListTime.setText(homeCheckListModel.getDate());
        // for edit image button we will require click lister
        homeCheckListViewHolder.llCheckListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSingleCheckListClickListener.onSingleCheckListClick(homeCheckListModel);
            }
        });
        homeCheckListViewHolder.llCheckListEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String checkListName = checkListArrayList.get(position).getCheckListTopicName();
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?" + "\n"+checkListName)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                checkListArrayList.remove(position);
                                notifyItemRemoved(position);
                                onSingleCheckListClickListener.onSingleLongClickDelete(homeCheckListModel);

                            }
                        })

// A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("cancel", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkListArrayList.size();
    }

    //inner class view holder

    public class HomeCheckListViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTopicName;
        private TextView tvCheckListTime;
        private ImageView ivEditCheckList;
        private LinearLayout llCheckListEdit;
        public HomeCheckListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopicName = itemView.findViewById(R.id.tvTopicName);
            tvCheckListTime = itemView.findViewById(R.id.tvCheckListTime);
            ivEditCheckList = itemView.findViewById(R.id.ivEditCheckList);
            llCheckListEdit = itemView.findViewById(R.id.llCheckListEdit);
        }
    }

    // click listener for every checklist
    public interface  OnSingleCheckListClickListener {
//      To get the checklist data using HomeCheckListModel
        public void onSingleCheckListClick(HomeCheckListModel homeCheckListModel);
        public void onSingleLongClickDelete(HomeCheckListModel homeCheckListModel);
    }

}
