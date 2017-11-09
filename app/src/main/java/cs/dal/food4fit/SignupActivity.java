package cs.dal.food4fit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * Created by Mihyar on 11/7/2017.
 * Sign up Activity
 */

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    Button signupButton;
//    TextView _nameText, _passwordText, _emailText;
    TextView _passwordText, _emailText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton     = (Button) findViewById(R.id.btn_signup);
//        _nameText      = (TextView) findViewById(R.id.input_name);
        _passwordText  = (TextView) findViewById(R.id.input_password);
        _emailText     = (TextView) findViewById(R.id.input_email);
    }

    public void signup(View view){
        Log.d(TAG, "Create Account");
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            signupButton.setEnabled(true);
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

//        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
    }

    public boolean validate(){
        boolean valid = true;
//        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

//        if (name.isEmpty() || name.length() < 3) {
//            _nameText.setError("at least 3 characters");
//            valid = false;
//        } else {
//            _nameText.setError(null);
//        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    public void goLogin(View view){
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
    }
}
