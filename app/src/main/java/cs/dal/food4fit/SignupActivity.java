package cs.dal.food4fit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mihyar on 11/7/2017.
 * Sign up Activity
 */

public class SignupActivity extends AppCompatActivity {

    Button signupButton     = (Button) findViewById(R.id.btn_signup);
//    TextView _loginLink     = (TextView) findViewById(R.id.link_login);
    TextView _nameText      = (TextView) findViewById(R.id.input_name);
    TextView _passwordText  = (TextView) findViewById(R.id.input_password);
    TextView _emailText     = (TextView) findViewById(R.id.input_email);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signup(View view){
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            signupButton.setEnabled(true);
            return;
        }
    }

    public boolean validate(){
        boolean valid = true;
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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
