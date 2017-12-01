package cs.dal.food4fit;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
    private ArrayList<Recipe> r = new ArrayList<Recipe>();
    private boolean threadtag= true;
    ProgressDialog progressDialog;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_explore, container, false);

        super.onCreate(savedInstanceState);


        // Define a progress dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Cooking...");

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, r);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Recipe item = (Recipe) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(getActivity(), RecipeActivity.class);

                intent.putExtra("imageID", item.getId());

                startActivity(intent);

            }
        });

        progressDialog.show();

        class load extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                SpoonacularAPI spoon = new SpoonacularAPI();
                ArrayList<Recipe> r = spoon.searchRecipe("random");
                gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, r);

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                gridView.setAdapter(gridAdapter);
                progressDialog.dismiss();
            }
        }

        new load().execute("");




        return view;

    }

}

//
//    private ArrayList<ImageItem> getData() {
//        final ArrayList<ImageItem> imageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i,imgs.getResourceId(i, -1)));
//        }
//        return imageItems;
//    }

