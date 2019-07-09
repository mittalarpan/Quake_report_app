package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context , List<Earthquake> earthquakes){
        super(context , 0 , earthquakes);
    }

   // @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        View listItemView = convertView ;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent , false)  ;
        }
        Earthquake currentEarthQuake = getItem(position) ;

        TextView currentDate =  (TextView)listItemView.findViewById(R.id.datte) ;
        Date dateObject = new Date(currentEarthQuake.getDate()) ;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        currentDate.setText(dateToDisplay) ;

        TextView currentTime = (TextView)listItemView.findViewById(R.id.time) ;
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a") ;
        String timeToDisplay = timeFormatter.format(dateObject);
        currentTime.setText(timeToDisplay);

        String org = currentEarthQuake.getLocation() ;
        String chk = "of" , primaryLocation , offset ;
        if(org.contains(chk))
        {
            String[] parts = org.split(chk) ;
            primaryLocation = parts[1] ;
            offset = parts[0] + chk ;
        }

        else
        {
            primaryLocation = org ;
            offset = "Near the" ;
        }

        TextView primaryLocationView =  (TextView)listItemView.findViewById(R.id.primary_location) ;
        primaryLocationView.setText(primaryLocation) ;

        TextView offsetView =  (TextView)listItemView.findViewById(R.id.location_offset) ;
        offsetView.setText(offset) ;

        TextView currentMagnitude =  (TextView)listItemView.findViewById(R.id.magnitude) ;
        currentMagnitude.setText(currentEarthQuake.getMagnitude());

        return listItemView ;
    }
}
