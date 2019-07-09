/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.quakereport.QueryUtilss;

import java.util.ArrayList;
import java.util.List;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {


    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private String stringUrl= "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10" ;
    private EarthquakeAdapter adapter;
    private  static final int EARTHQUAKE_LOADER_ID = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG , "Test: onCreate() called") ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        //System.out.println("fhisugfi");
        //ArrayList<Earthquake> earthquakes = new ArrayList<>();
        //earthquakes = QueryUtilss.extractEarthquakes() ;
        // Find a reference to the {@link ListView} in the layout



        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        LoaderManager loaderManager = getLoaderManager() ;
        Log.i(LOG_TAG , "TEST: calling initLoader()") ;
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID , null , this) ;

    }

    public Loader<List<Earthquake>> onCreateLoader(int i , Bundle bundle)
    {

        Log.i(LOG_TAG , "TEST: calling onCreateLoader()") ;
        return new EarthquakeLoader(this , stringUrl) ;
    }

    public void onLoadFinished(Loader<List<Earthquake> > loader , List<Earthquake> earthquakes)
    {
        Log.i(LOG_TAG , "TEST: calling onLoadFinished()") ;
        adapter.clear() ;
        if(!earthquakes.isEmpty())
        {
            adapter.addAll(earthquakes) ;
        }
    }

    public void  onLoaderReset(Loader<List<Earthquake>> loader)
    {
        Log.i(LOG_TAG , "Test: calling onLoaderReset")  ;
        adapter.clear() ;
    }
}
