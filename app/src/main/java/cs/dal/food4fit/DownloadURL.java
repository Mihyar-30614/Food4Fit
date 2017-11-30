package cs.dal.food4fit;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
/**
 * Kunal on 27th nov
 */



public class DownloadURL {

    //this will hep us in reading the URL
    public String readUrl(String myUrl) throws IOException
    {
        InputStream inputStream = null;
        String d = "";
        HttpURLConnection urlConnection = null;


        try {
            URL url = new URL(myUrl); //surrounded by try cath as its a unboun
            urlConnection=(HttpURLConnection) url.openConnection(); //open the connection
            urlConnection.connect(); //connected the network

            inputStream = urlConnection.getInputStream();
            //its for getting the input stream from the user
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringbuf = new StringBuffer();

            String sent = "";
            while((sent = br.readLine()) != null)
            {
                stringbuf.append(sent);

            }

            d = stringbuf.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();  //this final will help us in closing the connection
        }
        Log.d("Downloaded URL","Returning data from the google api = "+d);
        return d;
    }
}
