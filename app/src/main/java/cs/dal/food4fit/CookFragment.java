package cs.dal.food4fit;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookFragment extends Fragment {

    int ImageID;
    Recipe imageitem;
    ArrayList<String> directions = new ArrayList<String>();
    ListView listView3;
    ArrayAdapter<String> adapter;
    int timetag;

    public CookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook, container, false);


//
//        listView3 = (ListView) view.findViewById(R.id.cooklist);
//        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, directions);
//        listView3.setAdapter(adapter);


//        FirebaseDatabase firebaseDBInstance = FirebaseDatabase.getInstance();
//        DatabaseReference firebaseReference = firebaseDBInstance.getReference("MealCalendar");
//
//        final Date date = new Date();
//
//
//
//
//        // Attach a listener to read the data at our posts reference
//        firebaseReference.child("Grace").child(getDateString(date)).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.getValue()!=null){
//
//                    String Meal = dataSnapshot.getValue().toString();
//                    int firstmealtag;
//                    if(Meal.contains("B")){
//                        firstmealtag = 1;
//                    }else{
//                        if(Meal.contains("L")){
//                            firstmealtag = 2;
//                        }else{
//                            firstmealtag = 3;
//                        }
//                    }
//
//                    String[] splited = Meal.split("/");
//
//                    ArrayList<String> BFList = new ArrayList<String>();
//                    ArrayList<String> LNList = new ArrayList<String>();
//                    ArrayList<String> DNList = new ArrayList<String>();
//
//                    for(int i=0;i<splited.length;i++) {
//
//                        if(splited[i].startsWith("B")){
//                            BFList.add(splited[i]);
//                        }
//
//                        if(splited[i].startsWith("L")){
//                            LNList.add(splited[i]);
//                        }
//
//                        if(splited[i].startsWith("N")){
//                            DNList.add(splited[i]);
//                        }
//                    }
//
//                    if(date.getHours()<10){
//                        timetag =1;
//
//                    }else if(date.getHours()<13){
//                        timetag =2;
//
//                        }
//                    }else {
//                        timetag =3;
//                    }
//
//                }
//
//                final Thread thread1 = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        SpoonacularAPI spoon = new SpoonacularAPI();
//                        imageitem = spoon.getRecipe(ImageID);
//
//                    }
//                });
//                thread1.start();
//
//                try {
//                    Thread.sleep(3000);                 //1000 毫秒，也就是1秒.
//
//                } catch(InterruptedException ex) {
//                    Thread.currentThread().interrupt();
//                }
//
//
//
//                String dir = imageitem.getInstructions();
//
//                String split[] = dir.split("(?<=[^ ]\\.) (?=[^a-z])");
//
//                for(int i=0;i<split.length;i++){
//                    int t =i+1;
//                    directions.add(t+". "+split[i]);
//                }
//                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, directions);
//                listView3.setAdapter(adapter);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//
//        });

        return view;
    }



    public static String getDateString(Date date) {
        DateFormat format =  new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        return format.format(date);
    }

}
