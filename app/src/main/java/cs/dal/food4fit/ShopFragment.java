package cs.dal.food4fit;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by graceliu on 2017-11-19.
 */
public class ShopFragment extends Fragment {
    ImageView iv;
    private GridView gridView;
    boolean[] viewitemchecked;
    TextView Selected;
    ArrayList<Integer> userproduct = new ArrayList<>();
    Button itemused;
    String[] listItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        super.onCreate(savedInstanceState);
        iv = (ImageView)view.findViewById(R.id.iv);
        iv.setImageResource(R.drawable.dq);
        itemused = (Button) view.findViewById(R.id.button1);
        Selected = (TextView) view.findViewById(R.id.textitem);
        listItems = getResources().getStringArray(R.array.shop);
        viewitemchecked = new boolean[listItems.length];
        itemused.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder exchange = new AlertDialog.Builder(ShopFragment.this.getContext());
                exchange.setTitle(R.string.dialog);
                exchange.setMultiChoiceItems(listItems, viewitemchecked, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            userproduct.add(position);
                        } else {
                            userproduct.remove((Integer.valueOf(position)));
                        }
                    }
                });
                iv.setImageDrawable(null);
                //ix.setImageDrawable(null);
                exchange.setNeutralButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < viewitemchecked.length; i++) {
                            viewitemchecked[i] = false;
                            userproduct.clear();
                            Selected.setText("");
                        }
                        iv.setImageResource(R.drawable.dq);
                        //ix.setImageResource(R.drawable.dp2);
                    }
                });
                exchange.setCancelable(false);
                exchange.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < userproduct.size(); i++) {
                            item = item + listItems[userproduct.get(i)];
                            if (i != userproduct.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        Selected.setText(item);
                    }
                });


                AlertDialog shopDialog = exchange.create();
                shopDialog.show();
            }
        });
        return view;


    }

}

