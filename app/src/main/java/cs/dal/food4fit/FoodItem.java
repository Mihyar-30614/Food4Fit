package cs.dal.food4fit;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by adamwoodland on 2017-11-21.
 */

public class FoodItem {

    String name;
    int id;
    String img;

    public FoodItem() { }

    public FoodItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }


    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                "img='" + img + '\'' +
                '}';
    }

}
