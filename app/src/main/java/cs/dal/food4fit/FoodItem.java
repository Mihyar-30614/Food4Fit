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
    int calories;
    String fat;
    String protein;
    String carbs;
    int quantity;
    int id;
    String img;
    Bitmap photo;

    public FoodItem() { }

    public FoodItem(String name, int calories, String fat,
                    String protein, String carbs, int id) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) { this.calories = calories; }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarbs() { return carbs; }

    public void setCarbs(String carbs) { this.carbs = carbs; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity() {
        this.quantity = quantity;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", calories='" + calories + '\'' +
                ", fat='" + fat + '\'' +
                ", protein='" + protein + '\'' +
                ", carbs='" + carbs + '\'' +
                '}';
    }

}
