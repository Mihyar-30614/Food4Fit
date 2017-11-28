package cs.dal.food4fit;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.String.valueOf;


/**
 * Created by adamwoodland on 2017-11-21.
 */

public class Recipe {

    String name;
    int id;
    String ingredients;
    String instructions;
    String img;
    Bitmap photo;
    int time;

    public Recipe() { }

    public Recipe(String name, String ingredients, String instructions, Bitmap photo,
                  int time, int serving) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.photo = photo;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                ", time=" + time +
                '}';
    }

}
