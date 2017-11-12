package cs.dal.food4fit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mihyar on 11/11/2017.
 * This activity is to reset password for users
 */

public class forgotActivity extends AppCompatActivity {

    private static final String TAG = "forgotActivity";
    private FirebaseAuth mAuth;
    TextView emailText, resetFail;
    Button btn_reset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        emailText = (TextView) findViewById(R.id.input_email_reset);
        resetFail = (TextView) findViewById(R.id.text_resetFail);
        btn_reset = (Button)   findViewById(R.id.btn_reset);
        mAuth     = FirebaseAuth.getInstance();
    }

    public void resetPassword (View view){
        final String email    = emailText.getText().toString();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(forgotActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "ResetEmail:success");
                    Toast.makeText(forgotActivity.this, "Reset Link was sent to " + email,
                            Toast.LENGTH_SHORT).show();
                    resetFail.setVisibility(View.INVISIBLE);
                    goLogin();
                }else{
                    Log.d(TAG, "ResetEmail:failure");
                    resetFail.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void goLogin(){
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
    }
}
