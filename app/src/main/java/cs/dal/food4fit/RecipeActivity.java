package cs.dal.food4fit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static cs.dal.food4fit.TodayFragment.getDateString;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = "RecipeActivity";

    private RecipePageAdapter mRecipePageAdapter;

    private ViewPager mViewPager;
    private Recipe imageitem;
    private ArrayList<FoodItem> ingredients;
    private String directions = "";
    private String foods = "";
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe1);

        mRecipePageAdapter = new RecipePageAdapter(getSupportFragmentManager());

        // Set up the RecipePager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mRecipePageAdapter.addFragment(new DirectionsFragment(), "Directions");
        mRecipePageAdapter.addFragment(new IngredientsFragment(), "Ingredients");
        mViewPager.setAdapter(mRecipePageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //get the chosen RecipeID from the previous view
        final int imageID = getIntent().getIntExtra("imageID",0);

        //get Recipe data from Spoonacular API using unique ID
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SpoonacularAPI spoon = new SpoonacularAPI();
                imageitem = spoon.getRecipe(imageID);
            }
        });
        thread.start();

        //wait until recipe data has been retrieved.
        try {
            Thread.sleep(3000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //set the Recipe image
        ImageView imageView = (ImageView) findViewById(R.id.headerImage);
        imageView.setImageBitmap(imageitem.getPhoto());

        //put the selected recipe information to sharepreference so that the section adapter would get these information
        sharedata();

        //set up addmeal button and listener
        FloatingActionButton addMeal = (FloatingActionButton)findViewById(R.id.addmeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent to choose date
                Intent intent = new Intent(RecipeActivity.this, AddMealActivity.class);
                intent.putExtra("ImageID", imageID);
                startActivity(intent);

            }
        });

        //set up timer button and listener
        FloatingActionButton timer = (FloatingActionButton)findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, TimerActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    //put the selected recipe information to sharepreference so that the section adapter would get these information
    private void sharedata(){

        ingredients = imageitem.getIngredients();
        directions = imageitem.getInstructions();

        for(int i = 0; i<ingredients.size();i++){
            FoodItem foodItem =ingredients.get(i);
            foods=foods+foodItem.getName()+"!";
        }

        SharedPreferences sharedRecipe = getSharedPreferences("Recipe", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedRecipe.edit();
        editor.putString("ing",foods);
        editor.putString("dir",directions);
        editor.commit();
    }

}
