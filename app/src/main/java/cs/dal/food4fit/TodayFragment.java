package cs.dal.food4fit;


import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {


    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_today, container, false);

        ListView listview = (ListView) view.findViewById(R.id.MealList);

        ListViewAdapter listviewAdapter = null;


        MealPlan mealPlan = new MealPlan(getData(),getData(),getData());
        ArrayList<ImageItem> breakfastlist = mealPlan.getBreakfastList();
        ArrayList<ImageItem> lunchlist = mealPlan.getLunchList();
        ArrayList<ImageItem> dinnerlist = mealPlan.getDinnerList();

        ArrayList<Object> meallist = new ArrayList<>();

        meallist.add(new String("Breakfast"));
        for(int i =0;i<breakfastlist.size();i++){
            meallist.add(breakfastlist.get(i));
        }

        meallist.add(new String("Lunch"));
        for(int i =0;i<lunchlist.size();i++){
            meallist.add(lunchlist.get(i));
        }

        meallist.add(new String("Dinner"));
        for(int i =0;i<dinnerlist.size();i++){
            meallist.add(dinnerlist.get(i));
        }

        listviewAdapter = new ListViewAdapter(getActivity(),meallist);
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

        // Inflate the layout for this fragment
        return view;


    }


//    private ArrayList<MealPlan> getMealPlan() throws ParseException {
//        final ArrayList<MealPlan> mealPlans = new ArrayList<>();
//        TypedArray date = getResources().obtainTypedArray(R.array.date);
//        for (int i = 0; i < date.length(); i++) {
//            DateFormat format =  new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//            mealPlans.add(new MealPlan(getData(),getData(),getData()));
//        }
//        return mealPlans;
//    }



    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap1 = BitmapFactory.decodeStream(getResources().openRawResource(imgs.getResourceId(i,-1)));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap1, "Image#" + i,imgs.getResourceId(i, -1)));
        }
        return imageItems;
    }

//    private ArrayList<ImageItem> getBFData() {
//        final ArrayList<ImageItem> imageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i,imgs.getResourceId(i, -1)));
//        }
//        return imageItems;
//    }
//
//    private ArrayList<ImageItem> getLNData() {
//        final ArrayList<ImageItem> imageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length()-1; i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i,imgs.getResourceId(i, -1)));
//        }
//        return imageItems;
//    }
//
//    private ArrayList<ImageItem> getDNData() {
//        final ArrayList<ImageItem> imageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length()-2; i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i,imgs.getResourceId(i, -1)));
//        }
//        return imageItems;
//    }



}
