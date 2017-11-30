package cs.dal.food4fit;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * created by  Kunal on nov 27
 */

class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    String link;
    private String color;

    @Override
    protected String doInBackground(Object... objects){
        mMap = (GoogleMap)objects[0];
        link = (String)objects[1];
        //color = (String) objects[2];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readUrl(link);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){

        List<HashMap<String, String>> nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d("nearbyplacesdata","called parse method");
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        for(int i = 0; i < nearbyPlaceList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lati = Double.parseDouble(googlePlace.get("lat"));
            double longi = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lati, longi);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            //
            // Log.d(TAG, "showNearbyPlaces: "+ R.id.costco);

                Log.d("nearbyplacesdata","called parse method costco" + R.id.costco);

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            //if (googlePlace.get("name") == "Atlantic Superstore" || googlePlace.get("name") == "Sobeys Express" ){//|| wal.contains(googlePlace.get("name"))) {
            // at this point if was trying to show only the above mentioned places but unfortunately no success
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

            //}
        }
    }
}
