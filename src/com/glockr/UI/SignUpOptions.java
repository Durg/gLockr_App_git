package com.glockr.UI;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.glockr.connection.GlockrHttpConnection;
import com.glockr.utils.LoginParam;

public class SignUpOptions extends Activity implements OnClickListener 
{

	private Button mFacebookbutton, mSignupButton;
	private TextView mTapTextview;
	AsyncFacebookRunner mAsyncRunner;
	private static String APP_ID = "136520886477050";
	private Facebook facebook = new Facebook(APP_ID);
	private SharedPreferences mPrefs;
	boolean boll = false;
	HashMap<String, String> map;
	static String access_token;
	static String first_name;
	static String email;
	static String last_name;
	static String username;
	static String id;
	boolean bool = false;
	HashMap<String, String> hashmap;
	LoginParam param;	
	String api_key="1234567890";
	String version;
	int version_code;
	String imei;
	String response="";
	Handler handler;
	String jsondata;
	String jsonString;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signupoptions);
		mFacebookbutton = (Button)findViewById(R.id.btnfacebooklogin);
		mSignupButton = (Button)findViewById(R.id.btnloginwithemailt);
		mTapTextview = (TextView)findViewById(R.id.taphere);
		
		mFacebookbutton.setOnClickListener(this);
		mSignupButton.setOnClickListener(this);
		mTapTextview.setOnClickListener(this);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		
		param = new LoginParam();
		
		
	}

	

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view==mFacebookbutton)
		{

			Log.d("Image Button", "button Clicked");
			loginToFacebook();
			Log.e("-----At Signupoptionscreen------","-------boll="+boll);
			if (boll) 
			{
				map = getProfileInformation();			
				Log.e("-----At Signupoptionscreen------","-------under if1");

			}
			
		
			
		}
		else if(view==mSignupButton)
		{
		 Intent intentSignUp = new Intent();
		 intentSignUp.setClass(this, SignUpScreen.class);
		 startActivity(intentSignUp);	
		}
		else if(view==mTapTextview)
		{
		Intent intentTap = new Intent();
		intentTap.setClass(this, LoginScreen.class);
		intentTap.putExtra("CHECK_SCREEN", "0");
		startActivity(intentTap);
		
		
			
		}
		
	}
	
	/**
	 * Function to login into facebook
	 * */
	@SuppressWarnings("deprecation")
	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);

			// btnFbLogin.setVisibility(View.INVISIBLE);

			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();

						
							

						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}

		boll = true;
	}
	
	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	@SuppressWarnings("deprecation")
	public HashMap<String, String> getProfileInformation()
	{
		hashmap = new HashMap<String, String>();
		String response="";
		System.out.println("getProfileInformation>>>>>>>");
		final String[] PERMISSIONS = new String[] { "email" };
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				System.out.println("in on complete>>>>>>");
				Log.d("Profile", response);
				String json = response;

				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);

					id = profile.getString("id");
					hashmap.put("id", id);
					first_name = profile.getString("first_name");
					hashmap.put("first_name", first_name);

					email = profile.getString("email");
					hashmap.put("email", email);

					username = profile.getString("username");
					hashmap.put("username", email);

					last_name = profile.getString("last_name");
					hashmap.put("last_name", last_name);
					
				Log.e("-----At Signupoptionscreen------","-------first_name="+first_name+"----id="+id+"----email="+email+"---username="+username);	
					

					if(id!=null||first_name!=null||last_name!=null||email!=null||username!=null){
						
						Log.e("-----At Signupoptionscreen------","--getProfileInformation-method----under if"+"------param="+param);
						
						param.setEmail(email);
						param.setFirst_name(first_name);
						param.setSNID(id);
						param.setLast_name(last_name);
						param.setSNUsername(username);
						param.setAppKey(APP_ID);
						param.setSNName("fb");
						
					}
					

					String access_token = facebook.getAccessToken();
					param.setSNAccessToken(access_token);
					long AccessExpires = facebook.getAccessExpires();
					String appId = facebook.getAppId();
					
						System.out.println("status >>>>>>>>>>>..." + response);
						if(response.equalsIgnoreCase("success")){
							
							System.out.println("response is>>>>>>>"+response);
							runOnUiThread(new Runnable() {
				                 public void run() {
//				                     Toast.makeText(SignUpOptions.this,"Successfully Registered!!!",Toast.LENGTH_LONG).show();
//				                     Intent intent=new Intent(SignUpOptions.this,FeedScreen.class);
//				                     startActivity(intent);
				                }
				            });
						}
						else{
							System.out.println("response is>>>>>>>"+response);
							runOnUiThread(new Runnable() {
				                 public void run() {
//				                     Toast.makeText(SignUpOptions.this,"Problem in Registeration!!!!",Toast.LENGTH_LONG).show();
				                }
				            });
						}


				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
		bool = true;
		return hashmap;
	}
	
	/*public String registerTogLockr(LoginParam param) {
		this.param=param;
		String response = "";
		String sn_account = "";
		String status = "";
		String rallee_uri="";
		String rallee_password="";
		jsondata = makejson(param);
		if (!jsondata.equalsIgnoreCase("")||jsondata!=null) {

			try {
		
				response = GlockrHttpConnection.GenericHttpMethod(this,GlockrHttpConnection.POST_METHOD,"www.google.com", param.getSNAccessToken(),jsondata);				

				JSONObject status1 = new JSONObject(response.toString());				
				status = status1.getString("status");
				if (status.equalsIgnoreCase("success")) {
					JSONObject data = status1.getJSONObject("data");
					String id = data.getString("id");
					rallee_uri = data.getString("rallee_uri");
					rallee_password=data.getString("rallee_password");



					

				} else {

				}
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}*/
	
	
}
