package cs.dal.food4fit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mihyar on 11/7/2017.
 * Login activity
 */

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goSignup(View view){
        Intent signup_page = new Intent(this,LoginActivity.class);
        startActivity(signup_page);
    }
}
