package com.sudeep.todoapp.util;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sudeep.todoapp.R;

public abstract class AbstractAppActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;
    private boolean avoidFirstTimeWhenInternet = true;
//    private Dialog appWhenNoDataDialog,appShowProgressBar;
//    private boolean isLoadingDoneFirstTime = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      for network status
        initReceiver();
    }

    private void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(NetworkUtilities.isNetworkAvailable(context)) {
                    if(avoidFirstTimeWhenInternet){
                        avoidFirstTimeWhenInternet = false;
                        return;
                    }
                    whenInternet();
                } else {
                    avoidFirstTimeWhenInternet = false;
                    whenNoInternet();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,intentFilter);
    }

    public abstract void whenInternet();
    public abstract void whenNoInternet();

//    public void showNoDataDialog(Context context) {
//        if(appWhenNoDataDialog == null){
//            appWhenNoDataDialog = new Dialog(context);
//            appWhenNoDataDialog.setCancelable(false);
//            appWhenNoDataDialog.setContentView(R.layout.when_no_data);
//            appWhenNoDataDialog.setTitle(context.getString(R.string.app_name));
//
//            Window window = appWhenNoDataDialog.getWindow();
//            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            Button retry_btn = (Button) appWhenNoDataDialog.findViewById(R.id.retry_btn);
//
//            retry_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    refreshUI();
//                }
//            });
//        }
//        if(!appWhenNoDataDialog.isShowing()){
//            appWhenNoDataDialog.show();
//        }
//    }

//    public void showProgressBarDialog(Context context, String title, String msg) {
//        if(appShowProgressBar == null){
//            appShowProgressBar = new Dialog(context);
//            appShowProgressBar.setCancelable(false);
//            appShowProgressBar.setContentView(R.layout.lottie_login_animation);
//            appShowProgressBar.setTitle(context.getString(R.string.app_name));
//
//            Window window = appShowProgressBar.getWindow();
//            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        }
//        TextView tvProgressTitle = appShowProgressBar.findViewById(R.id.tvProgressTitle);
//        tvProgressTitle.setText(title);
//        TextView tvProgressMsg = appShowProgressBar.findViewById(R.id.tvProgressMsg);
//        tvProgressMsg.setText(msg);
//        if(!appShowProgressBar.isShowing() && !isLoadingDoneFirstTime){
//            appShowProgressBar.show();
//            isLoadingDoneFirstTime = true;
//        }
//    }
//
//    public void hideProgressBarDialog() {
//        if(appShowProgressBar != null && appShowProgressBar.isShowing()) {
//            appShowProgressBar.hide();
//        }
//    }
//
//    public void hideNoDataDialog() {
//        if(appWhenNoDataDialog != null && appWhenNoDataDialog.isShowing()) {
//            appWhenNoDataDialog.hide();
//        }
//    }

//    protected abstract void refreshUI();

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(receiver);
//    }

//    public void forceToRenderAgain() {
//        isLoadingDoneFirstTime = false;
//    }
}
