package cs.dal.food4fit;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * Created by graceliu on 2017-11-19.
 */
public class TodayFragment extends Fragment {

    public TodayFragment() {
        // Required empty public constructor
    }

    ArrayList<Recipe> BreakfastList = new ArrayList<>();
    ArrayList<Recipe> LunchList = new ArrayList<>();
    ArrayList<Recipe> DinnerList = new ArrayList<>();
    ArrayList<Object> meallist;
    MealPlan mealPlan;
    ListViewAdapter listviewAdapter;
    Context context;
    ListView listview;
    Recipe imageitem;
    ProgressDialog progressDialog;
    final int SECOND_ACTIVITY_RESULT_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        context = getContext();

        // Define a progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Cooking...");

        //initialize the mealplan data structure
        mealPlan = new MealPlan(BreakfastList,LunchList,DinnerList);
        meallist = getML(mealPlan);

        //retrieve the meal plan data from firebase
        retrievedata(getDateString(new Date()));

        //set listview with the mealplan data
        listview = (ListView) view.findViewById(R.id.MealList);
        listviewAdapter = new ListViewAdapter(getContext(), meallist);
        listview.setAdapter(listviewAdapter);

        //set up recipe list onclick listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick (AdapterView< ? > parent, View v, int position, long id){
                if(parent.getItemAtPosition(position)instanceof Recipe){
                    Recipe item = (Recipe) parent.getItemAtPosition(position);

                    //Create intent
                    Intent intent = new Intent(getActivity(), RecipeActivity.class);
                    intent.putExtra("imageID", item.getId());
                    startActivity(intent);

                }
            }
        });

        //set up goCalendar button and listener
        FloatingActionButton goCalendar = (FloatingActionButton)view.findViewById(R.id.goCalendar);
        goCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent to choose date
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivityForResult(intent,SECOND_ACTIVITY_RESULT_CODE);
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SECOND_ACTIVITY_RESULT_CODE is set to 0 on the above code, check request code is 0
        // check that it is the SecondActivity with an OK result
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                final String returnString = data.getStringExtra("dateString");
                final ListView listview = (ListView) getView().findViewById(R.id.MealList);
                TextView textView = (TextView) getView().findViewById(R.id.datetag);
                textView.setText(returnString);

                retrievedata(returnString);

            }
        }
    }

    //change the data structure into a way where listviewadapter would be able to inflate different views for section header and content.
    private ArrayList<Object> getML(MealPlan mealPlan){
        ArrayList<Object> meallist = new ArrayList<>();

        //if the mealplan is not blank, add section header and read the mealplan
        if(mealPlan!=null&&(mealPlan.getDinnerList()!=null||mealPlan.getLunchList()!=null||mealPlan.getBreakfastList()!=null)) {

            ArrayList<Recipe> breakfastlist = mealPlan.getBreakfastList();
            ArrayList<Recipe> lunchlist = mealPlan.getLunchList();
            ArrayList<Recipe> dinnerlist = mealPlan.getDinnerList();

            meallist.add(new String("Breakfast"));
            if(breakfastlist!=null){
                for (int i = 0; i < breakfastlist.size(); i++) {
                    meallist.add(breakfastlist.get(i));
                }
            }


            meallist.add(new String("Lunch"));
            if(lunchlist!=null){
                for (int i = 0; i < lunchlist.size(); i++) {
                    meallist.add(lunchlist.get(i));
                }

            }

            meallist.add(new String("Dinner"));
            if(dinnerlist!=null){
                for (int i = 0; i < dinnerlist.size(); i++) {
                    meallist.add(dinnerlist.get(i));
                }
            }

        }
        //if the mealplan is blank, add section header
        else{
            meallist.add(new String("Breakfast"));
            meallist.add(new String("Lunch"));
            meallist.add(new String("Dinner"));

        }

        return meallist;
    }

    //change date format into string format
    public static String getDateString(Date date) {
        DateFormat format =  new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        return format.format(date);
    }

    //retrieve the meal plan data from firebase
    private void retrievedata(String date){

        FirebaseDatabase firebaseDBInstance = FirebaseDatabase.getInstance();
        DatabaseReference firebaseReference = firebaseDBInstance.getReference("MealCalendar");

        progressDialog.show();


        //get userinformation
        SharedPreferences sharedUser = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String userEmail = sharedUser.getString("ID","default");

        // Attach a listener to read the data at our posts reference
        firebaseReference.child(userEmail).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BreakfastList = new ArrayList<>();
                LunchList = new ArrayList<>();
                DinnerList = new ArrayList<>();

                if(dataSnapshot.getValue()!=null){

                    String Meal = dataSnapshot.getValue().toString();
                    String[] splited = Meal.split("/");
                    int[] ImageID=new int[splited.length];

                    for(int i=0;i<splited.length;i++) {
                        ImageID[i] = Integer.parseInt(splited[i].substring(2));

                        final int tempID = ImageID[i];

                        final Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SpoonacularAPI spoon = new SpoonacularAPI();
                                imageitem = spoon.getRecipeSimple(tempID);

                            }
                        });
                        thread.start();


                        try {
                            Thread.sleep(2000);                 //1000 毫秒，也就是1秒.

                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        if (splited[i].startsWith("B")) {
                            BreakfastList.add(imageitem);
                        }
                        if (splited[i].startsWith("L")) {
                            LunchList.add(imageitem);
                        }
                        if (splited[i].startsWith("D")) {
                            DinnerList.add(imageitem);
                        }
                    }


                }
                progressDialog.dismiss();
                mealPlan = new MealPlan(BreakfastList,LunchList,DinnerList);
                meallist = getML(mealPlan);
                listviewAdapter = new ListViewAdapter(context,meallist);
                listview.setAdapter(listviewAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

    }


}
