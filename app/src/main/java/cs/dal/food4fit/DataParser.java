package cs.dal.food4fit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Kunal  on 27th nov
 *  i took help from the youtube tutorials
 *  here is the lnik for that
 *  https://www.youtube.com/watch?v=_Oljjn1fIAc
 */
// the data paerser class parese the data tha's coming from the google api
public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlejson)
    {
        HashMap<String, String> gdata = new HashMap<>();
        //its a hash map for mapping the data witht the latitude and the

        String vicecity= "EMPTY";
        String lati= "";
        String longi="";
        String ref="";
        String placeName = "EMPTY";

        Log.d("DataParser","jsonobject ="+googlejson.toString());


        try {
            if (!googlejson.isNull("name")) //if the data is name field is not null then place name string will have the data of that  location
            {
                placeName = googlejson.getString("name");
            }
            if (!googlejson.isNull("vicinity"))  //the vicinity tag is for the nearby aresi.e if its close to any place
            {
                vicecity = googlejson.getString("vicinity");
            }

            lati = googlejson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            //it will store latitude cooordinate of the place
            longi = googlejson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //it will show the longitude coordinate of the place
            ref = googlejson.getString("reference");
            //for the refrence that is coming from the google api


            gdata.put("lat", lati);
            gdata.put("lng", longi);
            gdata.put("reference", ref);
            gdata.put("place_name", placeName);
            gdata.put("vicinity", vicecity);
            //now the hash map object contains these elements lat lng reference place_name vicinity and other things

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return gdata;

    }

    //it's the main fuction that helps in showing the data
    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placelist = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i = 0; i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placelist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placelist;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
