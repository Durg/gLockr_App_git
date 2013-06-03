package com.glockr.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GlockrCheckInternetConnection 
{

	public Context context;
	public boolean haveConnectedWifi,haveConnectedMobile;
	
	public GlockrCheckInternetConnection()
	{
	
	}
	
	private boolean checkInternetConnection(Context c) 
	{
		
		context = c;
		 ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);	
	    // test for connection
	    if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
	    {
	    	NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    	for (NetworkInfo ni : netInfo) {
	    	    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	    	        if (ni.isConnected())
	    	            haveConnectedWifi = true;
	    	    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	    	        if (ni.isConnected())
	    	            haveConnectedMobile = true;
	    	}
	    	return haveConnectedWifi || haveConnectedMobile;
	        
	    }
	    else 
	    {
	        //Log.v(TAG, "Internet Connection Not Present");
	        return false;
	    }
	}
	
}
