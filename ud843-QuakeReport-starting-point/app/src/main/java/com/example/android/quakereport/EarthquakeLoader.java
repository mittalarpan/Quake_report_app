package com.example.android.quakereport;
//package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String mUrl ;
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    public EarthquakeLoader(Context context , String url)
    {
        super(context) ;
        mUrl = url ;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG , "Test : onStartLoading") ;
        forceLoad();
    }

    @Override
    public  List<Earthquake> loadInBackground()
    {
        Log.i(LOG_TAG , "Test : calling loadinBackground") ;
        if(mUrl == null)
        {
            return null ;
        }

        List<Earthquake> res = QueryUtilss.fetchData(mUrl) ;
        return res ;
    }
}
