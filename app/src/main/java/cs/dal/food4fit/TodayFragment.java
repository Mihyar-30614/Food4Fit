package cs.dal.food4fit;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

//    public HashMap<String,Calendar>findMP;



    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ListView listview = (ListView) view.findViewById(R.id.MealList);
        FloatingActionButton goCalendar = (FloatingActionButton)view.findViewById(R.id.goCalendar);
        final int SECOND_ACTIVITY_RESULT_CODE = 0;

        Date testdate = new Date();
//
//        FirebaseDatabase firebaseDBInstance = FirebaseDatabase.getInstance();
//        DatabaseReference firebaseReference = firebaseDBInstance.getReference("MealCalendar");
//
//
//        // Attach a listener to read the data at our posts reference
//        firebaseReference.child("GraceTest").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Calendar calendar = dataSnapshot.getValue(Calendar.class);
////                findMP.put(dataSnapshot.getKey(),calendar);
//                Log.i("testtoday", dataSnapshot.getKey());
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//                Log.i("testtoday", "oncancel");
//            }
//        });


        MainActivity test = (MainActivity)getActivity();
        ArrayList<Object> meallist = getML(getDateString(testdate),test.findMP);

        ListViewAdapter listviewAdapter = new ListViewAdapter(getActivity(),meallist);
        listview.setAdapter(listviewAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick (AdapterView< ? > parent, View v, int position, long id){
                if(parent.getItemAtPosition(position)instanceof ImageItem){
                    ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                    //Create intent
                    Intent intent = new Intent(getActivity(), RecipeActivity.class);
                    intent.putExtra("imageID", item.getImageID());
                    startActivity(intent);
                }
            }
        });

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

                TextView textView = (TextView) getView().findViewById(R.id.datetag);
                textView.setText(returnString);

                MainActivity test = (MainActivity)getActivity();
                ArrayList<Object> meallist = getML(returnString,test.findMP);
                ListViewAdapter listviewAdapter = new ListViewAdapter(getActivity(),meallist);
                ListView listview = (ListView) getView().findViewById(R.id.MealList);
                listview.setAdapter(listviewAdapter);

            }
        }
    }

    private ArrayList<Object> getML(String datestring, HashMap<String,Calendar> findMP){
        ArrayList<Object> meallist = new ArrayList<>();

        if(findMP.get(datestring)!=null) {
            Calendar calendar = findMP.get(datestring);
            MealPlan mealPlan = calendar.getMealPlan();
            ArrayList<ImageItem> breakfastlist = mealPlan.getBreakfastList();
            ArrayList<ImageItem> lunchlist = mealPlan.getLunchList();
            ArrayList<ImageItem> dinnerlist = mealPlan.getDinnerList();

            meallist.add(new String("Breakfast"));
            for (int i = 0; i < breakfastlist.size(); i++) {
                meallist.add(breakfastlist.get(i));
            }

            meallist.add(new String("Lunch"));
            for (int i = 0; i < lunchlist.size(); i++) {
                meallist.add(lunchlist.get(i));
            }

            meallist.add(new String("Dinner"));
            for (int i = 0; i < dinnerlist.size(); i++) {
                meallist.add(dinnerlist.get(i));
            }
        }
        else{
            meallist.add(new String("Breakfast"));
            meallist.add(new String("Lunch"));
            meallist.add(new String("Dinner"));

        }

        return meallist;
    }

    public static String getDateString(Date date) {
        DateFormat format =  new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        return format.format(date);
    }


}
