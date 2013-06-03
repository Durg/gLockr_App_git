package com.glockr.connection;



import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServerProxyService extends IntentService {

	public static final String PARAM_IN_JSON_REQUEST_BODY = "PARAM_IN_JSON_REQUEST_BODY";
	public static final String PARAM_IN_URL = "PARAM_IN_URL";
	public static final String PARAM_IN_HTTP_METHOD = "PARAM_IN_HTTP_METHOD";
	public static final String PARAM_OUT_JSON_RESPONSE_BODY = "PARAM_OUT_JSON_RESPONSE_BODY";
	public static final String PARAM_OUT_ERROR = "PARAM_OUT_ERROR";
	/*public static final String ACTION_DONE = "GENERIC_SERVICE_ACTION_DONE";
	public static final String ACTION_FAILED = "GENERIC_SERVICE_ACTION_FAILED";*/

	private static final String TAG = "ServerProxyService";

	public ServerProxyService() {
		super("ServerProxyService");
		Log.v(TAG, "-----------------Operation in RemoveInterestsService completed");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Context appContext = getApplicationContext();
		boolean actionCompleted = false;
		String secToken = ""; // appContext.getSecurityToken();
		String result = "";
		String serviceUrl = intent.getStringExtra(PARAM_IN_URL);
		String jsonMessageBody = intent.getStringExtra(PARAM_IN_JSON_REQUEST_BODY);
		int httpMethod = intent.getIntExtra(PARAM_IN_HTTP_METHOD, 0);
		/*String actionDone = intent.getStringExtra(ACTION_DONE);
		String actionFailed = intent.getStringExtra(ACTION_FAILED);*/
		String errorMsg = "";
		try 
		{
		result = GlockrHttpConnection.GenericHttpMethod(appContext, httpMethod,serviceUrl, jsonMessageBody);
		actionCompleted = true;
		if (Log.isLoggable(TAG, Log.VERBOSE))
			Log.v(TAG, "Operation in RemoveInterestsService completed");
		} 
		
		catch (Exception e) 
		{
			errorMsg = "Service failed";
			if (e.getMessage() != null)
				errorMsg += " " + e.getMessage();
			Log.e(TAG, errorMsg);
		}

		
	}

}

