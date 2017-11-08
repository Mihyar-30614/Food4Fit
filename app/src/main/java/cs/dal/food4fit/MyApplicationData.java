package cs.dal.food4fit;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Mihyar on 11/7/2017.
 * My Application Data Activity
 * To get link to Firebase
 */

public class MyApplicationData extends Application{
    public DatabaseReference firebaseReference;
    public FirebaseDatabase firebaseDBInstance;
}
