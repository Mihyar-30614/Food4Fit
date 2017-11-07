package cs.dal.food4fit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mihyar on 11/7/2017.
 */

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
