package cs.dal.food4fit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by adamwoodland on 2017-11-21.
 */

public class Recipe {

    String name;
    HashMap<FoodItem,Integer> ingredients;
    ArrayList<String> instructions;
    ImageItem photo;
    int time;
    int serving;
    URL url;
    HttpsURLConnection connect;
    InputStreamReader reader;
    InputStream in;

    public Recipe(String name, HashMap<FoodItem,Integer> ingredients, ArrayList<String> instructions, ImageItem photo, int time,
                  int serving) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.photo = photo;
        this.time = time;
        this.serving = serving;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<FoodItem, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<FoodItem, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public ImageItem getPhoto() {
        return photo;
    }

    public void setPhoto(ImageItem photo) {
        this.photo = photo;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                ", photo=" + photo +
                ", time=" + time +
                ", serving=" + serving +
                '}';
    }

    public void getItem(String n) {
        String result = null;
        try {
            url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?query=" +
                    n);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connect = (HttpsURLConnection) (url.openConnection());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedInputStream(connect.getInputStream());
            reader = new InputStreamReader(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = bReader.readLine()) != null)
                sb.append(line + "\n");
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        name = result;
    }

}
