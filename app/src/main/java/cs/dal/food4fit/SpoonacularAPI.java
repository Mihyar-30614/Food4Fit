package cs.dal.food4fit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

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
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by adamwoodland on 2017-11-27.
 */

public class SpoonacularAPI extends AsyncTask<String,Void,Void> {

    String result = "";
    HttpURLConnection connect;
    URL url;
    InputStreamReader reader;
    InputStream in;
    String line;

    @Override
    public Void doInBackground (String... strings) {
        try {
            url = new URL(strings[0]);
            connect = (HttpURLConnection) (url.openConnection());
            connect.setRequestProperty("X-Mashape-Key", "IOtJxVTiiFmshbyxpstTobJhIZ4hp1ZnKsxjsnfrwp60NmBIzv");
            connect.setRequestProperty("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com");
            in = new BufferedInputStream(connect.getInputStream());
            Scanner sReader = new Scanner(new InputStreamReader(in));
            try {
                result = sReader.nextLine();
            } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Recipe> searchRecipe(String name) {
        String type;
        if (name.equals("random")) {
            doInBackground("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=50");
            name = "recipes";
        }
        else {
            name = name.replaceAll("\\s","+");
            doInBackground("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?query=" + name);
            name = "results";
        }
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        try {
            JSONObject jsonObj = new JSONObject(result);
            result = "";
            JSONArray jArr = jsonObj.getJSONArray(name);
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject r = jArr.getJSONObject(i);
                Recipe recipe = generateRecipe(r);
                recipeList.add(recipe);
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    public FoodItem getFoodItem(int id) {
        doInBackground("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/menuItems/" + id);
        FoodItem f = new FoodItem();
        f.setId(id);
        try {
            JSONObject jsonObj = new JSONObject(result);
            result = "";
            f.setName(jsonObj.getString("title"));
            JSONObject nutrition = jsonObj.getJSONObject("nutrition");
            f.setCalories(nutrition.getInt("calories"));
            f.setFat(nutrition.getString("fat"));
            f.setProtein(nutrition.getString("protein"));
            f.setCarbs(nutrition.getString("carbs"));
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return f;
    }

    public Recipe getRecipe(int id) {
        doInBackground("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information");
        Recipe r = new Recipe();
        try {
            JSONObject jsonObj = new JSONObject(result);
            result = "";
            r = generateRecipe(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return r;
    }

    public Recipe generateRecipe(JSONObject j) {
        Recipe r = new Recipe();
        try {
            r.setName(j.getString("title"));
            r.setId(Integer.parseInt(j.getString("id")));
            r.setIngredients(j.getString("extendedIngredients"));
            r.setInstructions(j.getString("instructions"));
            r.setImg(j.getString("image"));
            r.setTime(j.getInt("readyInMinutes"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return r;
    }

}
