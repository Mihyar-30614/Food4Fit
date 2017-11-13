package cs.dal.food4fit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mihyar on 11/7/2017.
 * Login activity
 */

public class LoginActivity extends AppCompatActivity{
    // Declare Page Content
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Button loginButton;
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;
    TextView passwordText, emailText, forgotText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Generate Signature code
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "cs.dal.food4fit",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        */
        // Initialize Page Content
        loginButton   = (Button)   findViewById(R.id.btn_login);
        passwordText  = (TextView) findViewById(R.id.input_password);
        emailText     = (TextView) findViewById(R.id.input_email);
        forgotText    = (TextView) findViewById(R.id.link_forgot);
        mAuth          = FirebaseAuth.getInstance();

        facebookLoginButton = (LoginButton) findViewById(R.id.login_button);
        facebookLoginButton .setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        // Callback registration

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
               // App code
            }
        });
    }

    // Facebook Login Result return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    // Login Functionality
    public void login (View view){
        Log.d(TAG, "Login");

        // Show Progress Dialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // Get the content of the input
        String email    = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // call login function
        userLogin(email, password);
    }

    // Login function
    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, go to Home Page
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()){
                                // If User is Verified, go to Home Page
                                Intent homePage = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(homePage);
                            }else{
                                // If User is not Verified, Show text to the user
                                forgotText.setText("Email is not verified, please verify your email");
                                forgotText.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Incorrect Email or Password.",
                                    Toast.LENGTH_SHORT).show();
                            forgotText.setVisibility(View.VISIBLE);
                        }
                        // Hide Progress Dialog
                        progressDialog.hide();
                    }
                });
    }

    // Go to Signup page
    public void goSignup(View view){
        Intent signup_page = new Intent(this,LoginActivity.class);
        startActivity(signup_page);
    }

    // Go to Password Reset Page
    public void forgotPassword (View view){
        Intent forgot_page = new Intent(this, forgotActivity.class);
        startActivity(forgot_page);
    }
}
