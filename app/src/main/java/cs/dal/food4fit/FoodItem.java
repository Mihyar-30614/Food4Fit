package cs.dal.food4fit;

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

public class FoodItem extends AsyncTask<String,Void,Void> {

    String name;
    String description;
    String fGroup;
    String carbFactor;
    String fatFactor;
    String proteinFactor;
    int quantity;
    URL url;
    HttpURLConnection connect;
    InputStream in;
    InputStreamReader reader;

    public FoodItem() { }

    @Override
    protected Void doInBackground(String... strings) {
        String result = null;
        try {
            url = new URL("https://api.nal.usda.gov/ndb/list?format=xml&q=" + strings[0] + "&sort=n&api_key=" +
                    "zv4fwt94h3njadPKyGHxuAKQkSm7HSpGAzdwehsx");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connect = (HttpURLConnection)(url.openConnection());
            //connect.setRequestProperty("X-Mashape-Key","IOtJxVTiiFmshbyxpstTobJhIZ4hp1ZnKsxjsnfrwp60NmBIzv");
            //connect.setRequestProperty("X-Mashape-Host","spoonacular-recipe-food-nutrition-v1.p.mashape.com");
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
        return null;
    }

    public FoodItem(String name, String description, String fGroup, String carbFactor, String fatFactor,
                    String proteinFactor, int quantity) {
        this.name = name;
        this.description = description;
        this.fGroup = fGroup;
        this.carbFactor = carbFactor;
        this.fatFactor = fatFactor;
        this.proteinFactor = proteinFactor;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getfGroup() {
        return fGroup;
    }

    public void setfGroup(String fGroup) {
        this.fGroup = fGroup;
    }

    public String getCarbFactor() {
        return carbFactor;
    }

    public void setCarbFactor(String carbFactor) {
        this.carbFactor = carbFactor;
    }

    public String getFatFactor() {
        return fatFactor;
    }

    public void setFatFactor(String fatFactor) {
        this.fatFactor = fatFactor;
    }

    public String getProteinFactor() {
        return proteinFactor;
    }

    public void setProteinFactor(String proteinFactor) {
        this.proteinFactor = proteinFactor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity() {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fGroup='" + fGroup + '\'' +
                ", carbFactor='" + carbFactor + '\'' +
                ", fatFactor='" + fatFactor + '\'' +
                ", proteinFactor='" + proteinFactor + '\'' +
                '}';
    }

    public void getItem(String n) { doInBackground(n); }

}
