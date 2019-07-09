package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
public final class QueryUtilss {

    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtilss} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtilss (and an object instance of QueryUtilss is not needed).
     */
    public static final String LOG_TAG = QueryUtilss.class.getSimpleName();
  //  private String jsonresponse ,
    private QueryUtilss()
    {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */

    public static ArrayList<Earthquake> fetchData(String requrl)
    {
        Log.i(LOG_TAG , "Test: calling fetchData") ;
        URL url = createUrl(requrl) ;
        String jsonresponse = null ;
        try
        {
            //Log.i(LOG_TAG , "URLL:") ;
            System.out.println("URLL: " + url);
            jsonresponse = makehttprequest(url) ;
        }
        catch (IOException e)
        {

        }

        ArrayList<Earthquake>  eq = extractEarthquakes(jsonresponse) ;
        return eq ;
    }

    private static URL createUrl(String conv)
    {
        URL url = null ;
        try
        {
            url = new URL(conv) ;
        }
        catch(MalformedURLException e)
        {

        }
        return url ;
    }

    private static String  makehttprequest(URL url) throws IOException
    {
       String jsonresponse = null ;
       HttpURLConnection urlConnection = null ;

        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        finally {
            if(inputStream != null)
            {
                inputStream.close();
            }
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
       return jsonresponse ;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static ArrayList<Earthquake> extractEarthquakes(String samplejsonresponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject root = new JSONObject(samplejsonresponse) ;
            JSONArray arr = root.getJSONArray("features") ;
            for(int i=0;i<arr.length();i++)
            {
                JSONObject obj = arr.getJSONObject(i) ;
                JSONObject prop = obj.getJSONObject("properties")  ;
                String magnitude = prop.getString("mag") ;
                String location = prop.getString("place") ;
                long timee = prop.getLong("time") ;
                long timeInmiliseconds = (long)timee ;
                String url = prop.getString("url");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                Earthquake earthquake = new Earthquake(magnitude, timee, location, url);
                //Earthquake earthquake = new Earthquake(magnitude, timee , location) ;
                earthquakes.add(earthquake) ;

            }

            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtilss", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}