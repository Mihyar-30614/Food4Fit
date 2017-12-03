package cs.dal.food4fit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by graceliu on 2017-11-20.
 */

public class DirectionsFragment extends Fragment {
    private static final String TAG = "DirectionsFragment";

    private String dir;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directions,container,false);

        //get DirectionsData from RecipeActivity
        SharedPreferences sharedRecipe = getActivity().getSharedPreferences("Recipe", MODE_PRIVATE);
        dir = sharedRecipe.getString("dir","default");

        //Split Directions into several steps.
        String split[] = dir.split("(?<=[^ ]\\.) (?=[^a-z])");

        ArrayList<String> directions = new ArrayList<String>();

        //put directions with step number into directionsArray
        for(int i=0;i<split.length;i++){
            int t =i+1;
            directions.add(t+". "+split[i]);
        }

        //show direction step in the listview
        ListView listView2 = (ListView) view.findViewById(R.id.dirlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, directions);
        listView2.setAdapter(adapter);

        return view;
    }
}
