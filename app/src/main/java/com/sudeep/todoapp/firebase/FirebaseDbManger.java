package com.sudeep.todoapp.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbManger {
    // Get instance of firebaseDb to access it from app
    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

//    rootRef = FirebaseDatabase.getInstance().getReference();
//    private DatabaseReference rootRef;
//    DatabaseReference ref = rootRef.child("CheckList");

    /*
    MAP OF ACCESSING DB
    FirebaseDatabase instance
        DatabaseReference rootRef (parent)
            DatabaseReference ref = rootRef.child("CheckList") (childs) (datasnapshots)
    or
    FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance().getReference()
    DatabaseReference root = firebaseDb.child();
    */

//    ref will contain all the data of CheckList

    // To get data from parent child reference (Dynamically geting refrence)
    public static DatabaseReference getReference(String reference) {
        return firebaseDatabase.getReference(reference);
    }



    public interface FirebaseDbCallbackInterface {
        void onComplete(Object object);
        void onError();
        void onCancel();
    }
}
