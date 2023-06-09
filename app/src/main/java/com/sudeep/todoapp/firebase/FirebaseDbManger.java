package com.sudeep.todoapp.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sudeep.todoapp.User;
import com.sudeep.todoapp.edit.EditTaskModel;
import com.sudeep.todoapp.home.HomeCheckListModel;
import com.sudeep.todoapp.util.Common;
import com.sudeep.todoapp.util.PreferenceHelper;

import org.json.JSONObject;

import java.util.Map;

public class FirebaseDbManger {
    // Get instance of firebaseDb to access it from app
    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

//    DatabaseReference ref = firebaseDatabase.child("CheckList");

    /*
    MAP OF ACCESSING DB
    FirebaseDatabase instance
        DatabaseReference rootRef (parent)
            DatabaseReference ref = rootRef.child("CheckList") (childs) (datasnapshots)
    */


    // To get data from reference (Dynamically getting reference )
    public static DatabaseReference getReference(String reference) {
        return firebaseDatabase.getReference(reference);
    }

    // On edit screen use
    public static void addCheckListToDb(Context context, String randomIdChecklist, HomeCheckListModel checkListModel, FirebaseDbCallbackInterface firebaseDbCallbackInterface) {
        getReference(Common.CL_TX_CHECKLISTS).child(randomIdChecklist).setValue((checkListModel))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // It will call onComplete from where the call get called
                        firebaseDbCallbackInterface.onComplete(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDbCallbackInterface.onError();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        firebaseDbCallbackInterface.onCancel();
                    }
                });
    }

    public static void retrieveAllUserCheckLists(Context context, String userId, FirebaseDbCallbackInterface firebaseDbCallbackInterface){
        getReference(Common.CL_TX_CHECKLISTS).orderByChild("user_id").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                if (count > 0) {
                    firebaseDbCallbackInterface.onComplete(snapshot.getChildren());
                }
                else {
                    firebaseDbCallbackInterface.onComplete(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                firebaseDbCallbackInterface.onCancel();
            }
        });
    }

    public static void addTaskToDb(Context context, String checkListId, EditTaskModel editTaskModel, FirebaseDbCallbackInterface firebaseDbCallbackInterface) {
        String checkListTaskReference = checkListId+"_tasks";
        getReference(Common.CL_TX_CHECKLISTS).child(checkListId).child(checkListTaskReference).child(String.valueOf(editTaskModel.getTask_id()))
                .setValue((editTaskModel)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDbCallbackInterface.onComplete(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDbCallbackInterface.onError();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        firebaseDbCallbackInterface.onCancel();
                    }
                });
    }

    public static void retrieveAllCheckListTasks(Context context, String checkListId, FirebaseDbCallbackInterface firebaseDbCallbackInterface) {
        String checkListTaskReference = checkListId+"_tasks";
        getReference(Common.CL_TX_CHECKLISTS).child(checkListId).child(checkListTaskReference)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long count = snapshot.getChildrenCount();
                        if (count > 0) {
                            firebaseDbCallbackInterface.onComplete(snapshot.getChildren());
                        }
                        else {
                            firebaseDbCallbackInterface.onComplete(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseDbCallbackInterface.onCancel();
                    }
                });
    }

    public static void deleteCheckList(Context context, HomeCheckListModel homeCheckListModel, FirebaseDbCallbackInterface firebaseDbCallbackInterface) {
        String checkListIdReference = String.valueOf(homeCheckListModel.getCheckListId());
        getReference(Common.CL_TX_CHECKLISTS).child(checkListIdReference).
                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDbCallbackInterface.onComplete(null);
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        firebaseDbCallbackInterface.onError();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDbCallbackInterface.onCancel();
                    }
                });
    }

    public static void markTaskDone(Context context, String checkListId, EditTaskModel editTask, Boolean isDone, FirebaseDbCallbackInterface firebaseDbCallbackInterface) {
        String checkListTaskReference = checkListId+"_tasks";
        getReference(Common.CL_TX_CHECKLISTS).child(checkListId).child(checkListTaskReference)
                .child(String.valueOf(editTask.getTask_id())).child("taskDone").setValue(isDone)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDbCallbackInterface.onComplete(task);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDbCallbackInterface.onError();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        firebaseDbCallbackInterface.onCancel();
                    }
                });
    }

    public static void addUserToDb(Context context, FirebaseUser firebaseUser, User user, FirebaseDbCallbackInterface firebaseDbCallbackInterface) {
        getReference(Common.CL_TX_USERS).child(firebaseUser.getUid()).setValue((user)).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDbCallbackInterface.onComplete(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDbCallbackInterface.onError();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        firebaseDbCallbackInterface.onCancel();
                    }
                });
    }

    public static void getUserFromDb(Context context, String user_id, FirebaseDbCallbackInterface firebaseDbCallbackInterface){
        getReference(Common.CL_TX_USERS).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                    PreferenceHelper.putString(context, Common.C_KEY_APP_LOGIN_VALUES, jsonObject.toString());
                    firebaseDbCallbackInterface.onComplete(null);
                }
                catch (Exception e) {
                    firebaseDbCallbackInterface.onCancel();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                firebaseDbCallbackInterface.onCancel();
            }
        });
    }


    /* It will be used when method is called by other activity and listner will be use
       to show progess dialog or other thing.  */
    public interface FirebaseDbCallbackInterface {
        void onComplete(Object object);
        void onError();
        void onCancel();
    }
}
