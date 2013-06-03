package com.glockr.connection;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ServiceHelper {
	

	
	private static void startService(Context context,int httpMethod, String url, String requestBody) 
	{
		Intent scvIntent = new Intent(context, ServerProxyService.class);
		scvIntent.putExtra(ServerProxyService.PARAM_IN_URL, url);
		scvIntent.putExtra(ServerProxyService.PARAM_IN_HTTP_METHOD, httpMethod);
		scvIntent.putExtra(ServerProxyService.PARAM_IN_JSON_REQUEST_BODY,requestBody);
		/*scvIntent.putExtra(ServerProxyService.ACTION_DONE, actionDoneName);
		scvIntent.putExtra(ServerProxyService.ACTION_FAILED, actionFailedName);*/
		context.startService(scvIntent);
		
		Log.e("-----At Service helper class-----","---startRegistration-222---"+context.startService(scvIntent)+"----context"+context+"---scvIntent="+scvIntent);
		
	}
	
	
	public static void startRegistration(Context context, String jsonString) 
	{
		//String url = context.getString(R.string.mwServer);
		String url = "http://192.168.0.7/magento/new_customer.php";
		startService(context,GlockrHttpConnection.POST_METHOD,url, jsonString);
	
		Log.e("-----At Service helper class-----","---startRegistration----");
	
	
	}

	
	
	
}

