package cs.dal.food4fit;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

/**
 * Created by Mihyar on 11/7/2017.
 * Sign up Activity
 */

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    Button signupButton;
    ProgressDialog progressDialog;
    TextView passwordText, emailText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton   = (Button) findViewById(R.id.btn_signup);
        passwordText  = (TextView) findViewById(R.id.input_password);
        emailText     = (TextView) findViewById(R.id.input_email);
        mAuth          = FirebaseAuth.getInstance();
    }

    public void signup(final View view){
        Log.d(TAG, "Create Account");
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Account Creation Failed", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String email    = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goLogin(view);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "User Already Exists.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.hide();
                    }
                });
    }

    public boolean validate(){
        boolean valid   = true;
        String email    = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }

    public void goLogin(View view){
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
    }
}
