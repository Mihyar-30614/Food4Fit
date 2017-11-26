package cs.dal.food4fit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mihyar on 11/26/2017.
 * Update User Info
 */

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profilePassword, profileConfirm;
    String name, oldName, password, confirm, photoUrl, oldPhotoUrl;
    Button updateInfo;
    ImageView profilePhoto;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Layout Content
        profilePhoto    = (ImageView) findViewById(R.id.profile_photo);
        profileName     = (TextView) findViewById(R.id.profile_name);
        profilePassword = (TextView) findViewById(R.id.profile_password);
        profileConfirm  = (TextView) findViewById(R.id.profile_confirm);
        updateInfo      = (Button)   findViewById(R.id.btn_profile_update);

        // Get the Current User Info
        sharedPreferences   = this.getSharedPreferences("Login", MODE_PRIVATE);
        oldName        = sharedPreferences.getString("DisplayName",null);
        oldPhotoUrl    = sharedPreferences.getString("Photo",null);

        // Populate Layout with User Info
        profileName.setText(oldName);
        // TODO populate User Photo

    }

    public void updateUserInfo(View view){
        name     = profileName.getText().toString();
        password = profilePassword.getText().toString();
        confirm  = profileConfirm.getText().toString();

        if (!oldName.equals(name) || (!password.equals("") && !confirm.equals(""))){
            // TODO Update User Info
        }
    }
}
