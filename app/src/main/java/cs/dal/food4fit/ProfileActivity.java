package cs.dal.food4fit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Mihyar on 11/26/2017.
 * Update User Info
 */

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int CAMERA_PIC_REQUEST = 1337;
    TextView profileName, profilePassword, profileConfirm;
    String name, oldName,facebook, oldEmail, password, confirm, photoUrl, oldPhotoUrl;
    Button updateInfo;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ImageView profilePhoto;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Bitmap image;
    Uri downloadUrl;

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
        mAuth           = FirebaseAuth.getInstance();

        // Get the Current User Info
        sharedPreferences   = this.getSharedPreferences("Login", MODE_PRIVATE);
        oldName        = sharedPreferences.getString("DisplayName",null);
        oldPhotoUrl    = sharedPreferences.getString("Photo",null);
        oldEmail       = sharedPreferences.getString("Email", null);
        facebook     = sharedPreferences.getString("Facebook", null);

        // Populate Layout with User Info
        profileName.setText(oldName);
        // Populate User Photo
        if (oldPhotoUrl != null){
            if(oldPhotoUrl.equals("null")){
                oldPhotoUrl = "http://i.imgur.com/FlEXhZo.jpg?1";
            }
            loadProfilePic(oldPhotoUrl);
        }

        // Facebook can not change info using app
        if (facebook != null){
            if (facebook.equals("True")){
                profilePhoto.setEnabled(false);
                profileName.setEnabled(false);
                profileName.setInputType(InputType.TYPE_NULL);
                profilePassword.setEnabled(false);
                profilePassword.setInputType(InputType.TYPE_NULL);
                profileConfirm.setEnabled(false);
                profileConfirm.setInputType(InputType.TYPE_NULL);
                updateInfo.setEnabled(false);
            }
        }

        // Add listener to Profile Photo
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
    }

    // Receive the image from the Camera
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.profile_photo);
            imageview.setImageBitmap(image);
            uploadImage(image);
        }
    }

    private void uploadImage(Bitmap image) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference profilePicRef = storageRef.child("images/profilePic.jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = profilePicRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    public void updateUserInfo(View view){
        name     = profileName.getText().toString();
        password = profilePassword.getText().toString();
        confirm  = profileConfirm.getText().toString();

        if (!password.equals("") && !confirm.equals("")){
            // Update User Password
            updatePassword();
        }
        if (oldName == null){
            oldName = "";
        }
        if (!oldName.equals(name) || downloadUrl != null){
            // Update User Display Name
            user = mAuth.getCurrentUser();
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(downloadUrl).build();
            user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        user = mAuth.getCurrentUser();
                        editor = sharedPreferences.edit();
                        editor.putString("DisplayName", user.getDisplayName());
                        if (downloadUrl != null){
                            editor.putString("Photo", String.valueOf(downloadUrl));
                        }
                        editor.apply();
                        Toast.makeText(ProfileActivity.this, "Name Update Success!",Toast.LENGTH_SHORT).show();
                        finish();
                        onBackPressed();
                    }else{
                        Toast.makeText(ProfileActivity.this, "Name Update Failed!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Update User Password
    private void updatePassword() {
        mAuth.signOut();
        mAuth.signInWithEmailAndPassword(oldEmail, password)
                .addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.updatePassword(confirm).isSuccessful()){
                                Toast.makeText(ProfileActivity.this, "Password Update Failed!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProfileActivity.this, "Password Update Success!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ProfileActivity.this, "Incorrect Password.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void loadProfilePic (final String link){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    URL newurl = null;
                    try {
                        newurl = new URL(link);
                        Bitmap userPhoto = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                        profilePhoto.setImageBitmap(userPhoto);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
