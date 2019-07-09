package com.example.android.quakereport;

public class Earthquake {
    private String mMagnitude  , mLocation , mUrl ;
    private long mTimeInMilliseconds ;

    public Earthquake(String magnitude , long timeInMilliseconds , String location , String url)
    {
        mMagnitude = magnitude ;
        mTimeInMilliseconds = timeInMilliseconds ;
        mLocation = location ;
        mUrl = url ;
    }

    public String getMagnitude()
    {
        return mMagnitude ;
    }

    public String getLocation()
    {
        return mLocation ;
    }

    public long getDate()
    {
        return mTimeInMilliseconds ;
    }

    public String getUrl(){
        return mUrl ;
    }
}
