package cs.dal.food4fit;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static cs.dal.food4fit.R.menu.settings;
import static cs.dal.food4fit.TodayFragment.getDateString;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private DrawerLayout mDrawerLayout;
    private NavigationView leftNavigation;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    ImageView profilePic;
    TextView profileName, profileEmail;
    boolean slideOpen;
    Button btn_signup;

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Context context;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    mTextMessage.setText(R.string.title_today);
                    TodayFragment todayFragment = new TodayFragment();
                    android.support.v4.app.FragmentTransaction todayTransction = getSupportFragmentManager().beginTransaction();
                    todayTransction.replace(R.id.content, todayFragment).commit();
                    return true;
                case R.id.navigation_explore:
                    mTextMessage.setText(R.string.title_explore);
                    ExploreFragment exploreFragment = new ExploreFragment();
                    android.support.v4.app.FragmentTransaction exploreTransction = getSupportFragmentManager().beginTransaction();
                    exploreTransction.replace(R.id.content, exploreFragment).commit();
                    return true;
                case R.id.navigation_cook:
                    mTextMessage.setText(R.string.title_cook);
                    CookFragment cookFragment = new CookFragment();
                    android.support.v4.app.FragmentTransaction cookTransction = getSupportFragmentManager().beginTransaction();
                    cookTransction.replace(R.id.content, cookFragment).commit();
                    return true;
                case R.id.navigation_shop:
                    mTextMessage.setText(R.string.title_shop);
                    ShopFragment shopFragment = new ShopFragment();
                    android.support.v4.app.FragmentTransaction shopTransction = getSupportFragmentManager().beginTransaction();
                    shopTransction.replace(R.id.content, shopFragment).commit();
                    return true;
            }
            return false;
        }

    };

    // Side menu Navigation Listener
    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.settings_logout:
                    signOut();
                    return true;
                case R.id.settings_profile:
                    goProfile();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //set default page to todayFragment view
        TodayFragment todayFragment = new TodayFragment();
        android.support.v4.app.FragmentTransaction todayTransction = getSupportFragmentManager().beginTransaction();
        todayTransction.replace(R.id.content, todayFragment).commit();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Recipe r = new Recipe();
                    r.getItem("pizza");
                    String item = r.getName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_signup          = (Button) findViewById(R.id.user);

        // Side Menu Navigation Listener
        leftNavigation      = (NavigationView) findViewById(R.id.left_navigation);
        leftNavigation.setNavigationItemSelectedListener(navigationItemSelectedListener);

        // Handle Side menu
        menuController();

        // Open and Close Side Menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.sideMenu);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            // Drawer completely closed
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                slideOpen = false;
                // Handle Side menu
                menuController();
            }
            // Drawer completely open
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                slideOpen = true;
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        MealPlan mealPlan1 = new MealPlan(getData(R.array.image_ids),getData(R.array.image_ids),getData(R.array.image_ids));
        MealPlan mealPlan2 = new MealPlan(getData(R.array.image_idsln),getData(R.array.image_idsln),getData(R.array.image_idsln));
        MealPlan mealPlan3 = new MealPlan(getData(R.array.image_idsdn),getData(R.array.image_idsdn),getData(R.array.image_idsdn));
        findMP = getCalendar(mealPlan1,mealPlan2,mealPlan3);
    }

    public HashMap<String,Calendar> findMP = new HashMap<String, Calendar>();
//    public FirebaseDatabase firebaseDBInstance = FirebaseDatabase.getInstance();
//    public DatabaseReference firebaseReference = firebaseDBInstance.getReference("MealCalendar");


    //make some raw data
    private ArrayList<ImageItem> getData(int imagearray) {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(imagearray);

        //R.array.image_ids
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap1 = BitmapFactory.decodeStream(getResources().openRawResource(imgs.getResourceId(i,-1)));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap1, "Image#" + i,imgs.getResourceId(i, -1)));
        }
        return imageItems;
    }

    private HashMap<String,Calendar> getCalendar(MealPlan mealPlan1,MealPlan mealPlan2, MealPlan mealPlan3){

        final HashMap<String,Calendar> findMP = new HashMap<>();
        TypedArray date = getResources().obtainTypedArray(R.array.date);

        final Calendar calendar1 = new Calendar("GraceTest",getDateString(new Date()),mealPlan1);
        final Calendar calendar2 = new Calendar("GraceTest",date.getString(0),mealPlan2);
        final Calendar calendar3 = new Calendar("GraceTest",date.getString(1),mealPlan3);

        findMP.put(getDateString(new Date()),calendar1);
        findMP.put(date.getString(0),calendar2);
        findMP.put(date.getString(1),calendar3);
//
//        firebaseReference.child(getDateString(new Date())).setValue(calendar1);
//        firebaseReference.child(date.getString(0)).setValue(calendar2);
//        firebaseReference.child(date.getString(1)).setValue(calendar3);


        return findMP;
    }

    // Handle changes to the left side menu
    private void menuController() {
        // Get Handle of the Header
        Menu menu            = this.leftNavigation.getMenu();
        MenuItem logout      = menu.findItem(R.id.settings_logout);
        MenuItem account     = menu.findItem(R.id.settings_profile);
        View header          = leftNavigation.getHeaderView(0);
        profilePic           = (ImageView) header.findViewById(R.id.UserProfilePhoto);
        profileName          = (TextView) header.findViewById(R.id.profileName);
        profileEmail         = (TextView) header.findViewById(R.id.profileEmail);

        sharedPreferences   = this.getSharedPreferences("Login", MODE_PRIVATE);
        String email        = sharedPreferences.getString("Email",null);
        String name         = sharedPreferences.getString("DisplayName",null);
        String photo        = sharedPreferences.getString("Photo",null);

        if (email != null){
            // Show user info in the drawer
            profileEmail.setText(email);
            if (name != null){
                profileName.setText(name);
            }
            if (photo.equals("null")){
                photo = "http://i.imgur.com/FlEXhZo.jpg?1";
            }
            loadProfilePic(photo);
        }else{
            // Hide Menu Element
            logout.setVisible(false);
            account.setVisible(false);
            profilePic.setVisibility(View.INVISIBLE);
            profileEmail.setVisibility(View.INVISIBLE);
            profileName.setVisibility(View.INVISIBLE);
        }
    }

    private void loadProfilePic(final String photo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    URL newurl = null;
                    try {
                        newurl = new URL(photo);
                        Bitmap userPhoto = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                        profilePic.setImageBitmap(userPhoto);
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

    // Go to Login Page
    public void goLogin (View view){
        startActivity(new Intent(this,LoginActivity.class));
    }

    // Go to User Profile
    public void goProfile (){
        startActivity(new Intent(this, ProfileActivity.class));
    }

    // Open and close Navigation Bar using icon
    public void navigationBar (View view){
        if (slideOpen){
            mDrawerLayout.closeDrawer(Gravity.START);
        }else{
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }

    // Close the navigation Bar when click back
    public void onBackPressed (){
        if (slideOpen){
            mDrawerLayout.closeDrawer(Gravity.START);
        }else{
            super.onBackPressed();
        }
    }

    // Sign out
    public void signOut (){
        // Sign out user
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        // Clear saved info
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        // Close the side menu
        onBackPressed();
        // Recreate the content
        menuController();
    }
}
