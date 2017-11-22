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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static cs.dal.food4fit.R.menu.settings;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private DrawerLayout mDrawerLayout;
    private NavigationView leftNavigation;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
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
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn_signup          = (Button) findViewById(R.id.user);

        // Side Menu Navigation Listener
        leftNavigation      = (NavigationView) findViewById(R.id.left_navigation);
        leftNavigation.setNavigationItemSelectedListener(navigationItemSelectedListener);

        sharedPreferences   = this.getSharedPreferences("Login", MODE_PRIVATE);
        String Email        = sharedPreferences.getString("Email",null);
        if (Email != null){
            /*
            TODO
            Show log out
            Show user info in the drawer
            */
        }else{
            Menu menu = this.leftNavigation.getMenu();
            MenuItem item = menu.findItem(R.id.settings_logout);
            item.setVisible(false);
        }
        // Open and Close Side Menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.sideMenu);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            // Drawer completely closed
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                slideOpen = false;
            }
            // Drawer completely open
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                slideOpen = true;
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    // Go to Login Page
    public void goLogin (View view){
        startActivity(new Intent(this,LoginActivity.class));
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
        // Clear saved info
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
