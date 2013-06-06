package com.glockr.utils;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener 
{



private final Context mContext;

// flag for GPS status
boolean isGPSEnabled = false;
// flag for network status
boolean isNetworkEnabled = false;
// flag for GPS status
boolean canGetLocation = false;
Location location; // location
double latitude ;//= 37.422006; // latitude
double longitude;// = -122.084095; // longitude

// The minimum distance to change Updates in meters
private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

// The minimum time between updates in milliseconds
private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

// Declaring a Location Manager
protected LocationManager locationManager;

public GPSTracker(Context context) {
    Log.i("*******", "Inside constructor---context="+context);
    this.mContext = context;
    getLocation();
}

public Location getLocation() {
    try {
        Log.i("*******", "Inside get Location");
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

       try
       {
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.i("*******", ""+isGPSEnabled);
        }
       catch(Exception e)
       {
    	e.printStackTrace();   
       }
       try
       { 
       // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("*******", ""+isNetworkEnabled);
       }
       catch(Exception e)
       {
    	e.printStackTrace();   
       }
        
        if (!isGPSEnabled)// && !isNetworkEnabled) 
        {
            
        	showSettingsAlert();
        	
             
             
        } 
        else {
            this.canGetLocation = true;
            if (isGPSEnabled) {
                Log.i("*********", "Inside isGPSEnabled");
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        Log.i("*********", locationManager.toString());
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.i("*********", location.toString());
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i("*********", "Lat:"+latitude+"Long:"+longitude);
                        }Log.i("*********", location.toString());
                    }
                }
            }
            if (isNetworkEnabled) {
                Log.i("*********", "Inside isNetworkEnabled");
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0,
                        0, this);
                Log.d("Network", "Network");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services

        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return location;
}

/**
 * Stop using GPS listener
 * Calling this function will stop using GPS in your app
 * */
public void stopUsingGPS(){
    if(locationManager != null){
        locationManager.removeUpdates(GPSTracker.this);
    }       
}

/**
 * Function to get latitude
 * */
public double getLatitude(){
    if(location != null){
        latitude = location.getLatitude();
    }

    // return latitude
    return latitude;
}

/**
 * Function to get longitude
 * */
public double getLongitude(){
    if(location != null){
        longitude = location.getLongitude();
    }

    // return longitude
    return longitude;
}

/**
 * Function to check GPS/wifi enabled
 * @return boolean
 * */
public boolean canGetLocation() {
    return this.canGetLocation;
}

/**
 * Function to show settings alert dialog
 * On pressing Settings button will lauch Settings Options
 * */
public void showSettingsAlert(){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
    // Setting Dialog Title
    alertDialog.setTitle("GPS is settings");
    // Setting Dialog Message
    alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
    // On pressing Settings button
    alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
        }
    });

    // on pressing cancel button
    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        }
    });

    // Showing Alert Message
    alertDialog.show();
}

@Override
public void onLocationChanged(Location location) {
   // Toast.makeText(getApplicationContext(), "Location Changed called", Toast.LENGTH_SHORT).show();
    latitude =location.getLatitude();
    longitude = location.getLongitude();
}

public void onProviderDisabled(String provider) {
}

public void onProviderEnabled(String provider) {
}


public void onStatusChanged(String provider, int status, Bundle extras) {
}

@Override
public IBinder onBind(Intent arg0) {
    return null;
}

	
}
