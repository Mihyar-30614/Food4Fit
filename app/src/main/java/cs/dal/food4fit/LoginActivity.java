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

/**
 * Created by Mihyar on 11/7/2017.
 * Login activity
 */

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Button loginButton;
    TextView passwordText, emailText, forgotText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton   = (Button) findViewById(R.id.btn_login);
        passwordText  = (TextView) findViewById(R.id.input_password);
        emailText     = (TextView) findViewById(R.id.input_email);
        forgotText    = (TextView) findViewById(R.id.link_forgot);
        mAuth          = FirebaseAuth.getInstance();
    }

    public void login (View view){
        Log.d(TAG, "Login");

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email    = emailText.getText().toString();
        String password = passwordText.getText().toString();

        userLogin(email, password);
    }

    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent homePage = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(homePage);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Incorrect Email or Password.",
                                    Toast.LENGTH_SHORT).show();
                            forgotText.setVisibility(View.VISIBLE);
                        }
                        progressDialog.hide();
                    }
                });
    }

    public void goSignup(View view){
        Intent signup_page = new Intent(this,LoginActivity.class);
        startActivity(signup_page);
    }

    public void forgotPassword (View view){
        Intent forgot_page = new Intent(this, forgotActivity.class);
        startActivity(forgot_page);
    }
}
