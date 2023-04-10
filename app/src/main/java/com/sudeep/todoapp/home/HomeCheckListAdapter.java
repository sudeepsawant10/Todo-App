package com.sudeep.todoapp.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudeep.todoapp.R;

import java.util.ArrayList;


public class HomeCheckListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<HomeCheckListModel> checkListArrayList = new ArrayList<>();

    public HomeCheckListAdapter(Context context, ArrayList<HomeCheckListModel> checkListArrayList) {
        this.context = context;
        this.checkListArrayList = checkListArrayList;
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
        // get ArrayList one by one to set on ViewHolder
        HomeCheckListModel homeCheckListModel = checkListArrayList.get(position);
        HomeCheckListViewHolder homeCheckListViewHolder = (HomeCheckListViewHolder) holder;
        homeCheckListViewHolder.tvTopicName.setText(homeCheckListModel.getCheckListTopicName());
        homeCheckListViewHolder.tvCheckListTime.setText(homeCheckListModel.getDate());
        // for edit image button we will require click lister
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
        public HomeCheckListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopicName = itemView.findViewById(R.id.tvTopicName);
            tvCheckListTime = itemView.findViewById(R.id.tvCheckListTime);
            ivEditCheckList = itemView.findViewById(R.id.ivEditCheckList);
        }
    }
}
