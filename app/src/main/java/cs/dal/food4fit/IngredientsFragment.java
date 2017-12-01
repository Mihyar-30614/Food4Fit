package cs.dal.food4fit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by graceliu on 2017-11-20.
 */

public class IngredientsFragment extends Fragment {
    private static final String TAG = "IngredientsFragment";

    private String foodstring;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients,container,false);

        SharedPreferences sharedRecipe = getActivity().getSharedPreferences("Recipe", MODE_PRIVATE);
        foodstring = sharedRecipe.getString("ing","default");
        String[] splited = foodstring.split("!");

        ArrayList<String> foodname = new ArrayList<String>();

        for(int i=0;i<splited.length;i++){
            int t =i+1;
            foodname.add(t+". "+splited[i]);
        }

        ListView listView1 = (ListView) view.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, foodname);
        listView1.setAdapter(adapter);

        return view;
    }
}
