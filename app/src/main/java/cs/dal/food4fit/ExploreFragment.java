package cs.dal.food4fit;


import android.content.Context;
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
import android.widget.GridView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Context context;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        super.onCreate(savedInstanceState);

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SpoonacularAPI spoon = new SpoonacularAPI();
                Recipe r = spoon.getRecipe(19619);
            }
        });
        thread.start();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView< ? > parent, View v, int position, long id){

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(getActivity(), RecipeActivity.class);

                intent.putExtra("imageID", item.getImageID());

                startActivity(intent);

            }
        });

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
