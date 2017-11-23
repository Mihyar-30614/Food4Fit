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

import java.util.ArrayList;


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

        ListView BFlistview = (ListView) view.findViewById(R.id.BreakfastList);
//        ListView Lnlistview = (ListView) view.findViewById(R.id.LunchList);
//        ListView Dnlistview = (ListView) view.findViewById(R.id.DinnerList);
        GridViewAdapter listViewAdapter = new GridViewAdapter(getActivity(), R.layout.list_item_layout, getData());
        BFlistview.setAdapter(listViewAdapter);
//        Lnlistview.setAdapter(listViewAdapter);
//        Dnlistview.setAdapter(listViewAdapter);


        BFlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView< ? > parent, View v, int position, long id){

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(getActivity(), RecipeActivity.class);

                intent.putExtra("imageID", item.getImageID());

                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return view;


    }


    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i,imgs.getResourceId(i, -1)));
        }
        return imageItems;
    }

}
