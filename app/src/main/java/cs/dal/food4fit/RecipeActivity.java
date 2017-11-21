package cs.dal.food4fit;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = "RecipeActivity";

    private RecipePageAdapter mRecipePageAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe1);

        Log.d(TAG, "onCreate: Starting.");

    mRecipePageAdapter = new RecipePageAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    setupViewPager(mViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
}

    private void setupViewPager(ViewPager viewPager) {
        RecipePageAdapter adapter = new RecipePageAdapter(getSupportFragmentManager());
        adapter.addFragment(new IngredientsFragment(), "Ingredients");
        adapter.addFragment(new DirectionsFragment(), "Directions");
        viewPager.setAdapter(adapter);
    }
}
