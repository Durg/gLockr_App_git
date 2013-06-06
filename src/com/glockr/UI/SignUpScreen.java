package com.glockr.UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glockr.adapter.CropOptionAdapter;
import com.glockr.utils.CropOption;
import com.glockr.utils.EmailValidator;
import com.glockr.utils.GPSTracker;

public class SignUpScreen extends Activity implements OnClickListener
{

	private EditText meditEmail, meditPassword,meditFname,meditLname;
	private Button  mcreateButton;//mbackButton
	private TextView mtapText,mtermsText,mprivacyText;
	private String mstrEmail, mstrPassword,mstrFname,mstrLname;
	private ImageView mimageCamera;
	private Uri mImageCaptureUri;
	private ImageView mImageView;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	GPSTracker gps;
	LocationManager locationManager;
	double latitude ;
	double longitude;
	public String  results;
	private String status = "";
	 JSONObject person;
	 JSONObject obj;
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		meditEmail = (EditText)findViewById(R.id.emailtextboxsignup);
		meditPassword = (EditText)findViewById(R.id.passwordsignup);
		meditFname = (EditText)findViewById(R.id.firstnamesignup);
		meditLname = (EditText)findViewById(R.id.lastnamesignup);		
		//mbackButton = (Button)findViewById(R.id.signupback);
		mcreateButton = (Button)findViewById(R.id.signupcreate);		
		mtapText = (TextView)findViewById(R.id.tapheresignup);
		mtermsText = (TextView)findViewById(R.id.termssignup);
		mprivacyText = (TextView)findViewById(R.id.privacypolicysignup);
		mimageCamera = (ImageView)findViewById(R.id.camimagesignup);		
		meditEmail.setOnClickListener(this);
		meditPassword.setOnClickListener(this);
		meditFname.setOnClickListener(this);
		meditLname.setOnClickListener(this);
		//mbackButton.setOnClickListener(this);
		mcreateButton.setOnClickListener(this);
		mtapText.setOnClickListener(this);
		mtermsText.setOnClickListener(this);
		mprivacyText.setOnClickListener(this);
		mimageCamera.setOnClickListener(this);
		
		meditEmail.setText("durgesh@techlites.com");
		meditPassword.setText("123456");
		meditFname.setText("Durgesh");
		meditLname.setText("Mishra");
		
		gps = new GPSTracker(SignUpScreen.this);
		
		
		
	}


	@Override
	public void onClick(View view) 
	{
		// TODO Auto-generated method stub
		if(view==mcreateButton)
		{
			mstrEmail = meditEmail.getText().toString();
			mstrPassword = meditPassword.getText().toString();
			mstrFname = meditFname.getText().toString();
			mstrLname = meditLname.getText().toString();
			
			//Log.e("-----At SignUpScreen-----","---Onclickmethod----mstrEmail="+mstrEmail);
			
			if(mstrEmail.equals(null)||mstrPassword.equals(null)||mstrFname.equals(null)||mstrLname.equals(null)||mstrEmail.equals("")||mstrPassword.equals("")||mstrFname.equals("")||mstrLname.equals(""))
			{
			Toast.makeText(this, "Please fill the all edit box",Toast.LENGTH_LONG).show();	
			return;
			}
			else
			{
			
			EmailValidator emailValidator = new EmailValidator();
			boolean isEmail = emailValidator.validate(mstrEmail);
			if(isEmail)
			{
			//Toast.makeText(this, "go for create",Toast.LENGTH_LONG).show();	
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			Log.e("-----At SignUpScreen-----","---Onclickmethod----latitude="+latitude+"---longitude="+longitude);
			
		//	signUp();
			
			Intent intent = new Intent();
			intent.setClass(this, FeedScreen.class);
			startActivity(intent);
			
				
			}
			else
			{
			Toast.makeText(this, "check your email",Toast.LENGTH_LONG).show();		
			//return;
			}
			
			}
			
			
		}
		else if(view==mtapText)
		{
		Intent intentTap = new Intent();
		intentTap.setClass(this, LoginScreen.class);
		intentTap.putExtra("CHECK_SCREEN", "1");
		startActivity(intentTap);
		}
		else if(view==mprivacyText)
		{
		WebView mwebView = new WebView(this);
		mwebView.getSettings().setJavaScriptEnabled(true);
		mwebView.loadUrl("http://www.google.com");
		}
		else if(view==mtermsText)
		{
		WebView mwebView = new WebView(this);
		mwebView.getSettings().setJavaScriptEnabled(true);
		mwebView.loadUrl("http://www.google.com");
		}
		/*else if(view==mbackButton)
		{
			Intent intentSignupoptions = new Intent();
			intentSignupoptions.setClass(this, SignUpOptions.class);
			startActivity(intentSignupoptions);
		}*/
		else if(view == mimageCamera)
		{
			takingPhotoThroughVamera();	
		}
		
	}
	
	private void signUp() 
	{	
	      Thread t = new Thread() {

	            public void run() {
	                Looper.prepare(); //For Preparing Message Pool for the child Thread
	                HttpClient client = new DefaultHttpClient();
	                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit                
	                HttpResponse response;
	                JSONObject json = new JSONObject();
	                InputStream in = null;
	                try {
	                    HttpPost post = new HttpPost("http://192.168.0.14/magento/new_customer.php");
	                    post.setHeader("Content-type", "application/json");
	                    json.put("customer_email",mstrEmail);
	        			json.put("customer_password", mstrPassword);
	        			json.put("customer_fname",mstrFname);
	        			json.put("customer_lname",mstrLname);
	        			json.put("latitude",latitude);
	        			json.put("longitude",longitude);	        			
	        			
	        			String jsonString = json.toString();	                    
	        			StringEntity se = new StringEntity( json.toString(),HTTP.UTF_8);  
	                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                    post.setEntity(se);
	                    response = client.execute(post);
	                    //String temp = EntityUtils.toString(response.getEntity());
	                  // Log.e("--------------json string--------------------params=-","--------param=="+params);
	                    Log.e("-----At SignUpScreen-----","-signUp method----response1="+response.getAllHeaders()+"---response2="+response.getEntity().getContentLength()
	                    		+"--response3="+response.getParams()+"---response4="+response.getLocale()+"----response5="+response.getStatusLine());

	                   
	                    if(response!=null)
	                    {	                    	
	                        in = response.getEntity().getContent(); //Get the data in the entity                       
	                       
	                    }

	                } catch(Exception e) {
	                    e.printStackTrace();
	                    //createDialog("Error", "Cannot Estabilish Connection");
	                }
	                try
	                {
	                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	                StringBuilder sb = new StringBuilder();
	                String line = null;
	                while ((line = reader.readLine()) != null) 
	                {
	                        sb.append(line + "\n");//line + "\n"
	                }
	                in.close();
	               results=sb.toString();
	               Log.e("-----At SignUpScreen-----","-signUp method----results="+results);
	               //String str="";
	               
	               try {
	            	    obj = new JSONObject(results);	            	   
	            	   Log.e("-----At SignUpScreen-----","-signUp method----obj="+ obj.getString("message"));
	            	  
	   				

	   			} catch (Exception e)
	   			{
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	               
	               Log.e("111111111111","11111111111111111");
					AlertDialog.Builder alert = new AlertDialog.Builder(SignUpScreen.this);
		           // alert.setTitle("Successfull Registration"); 
		            alert.setMessage(obj.getString("message"));     //results        
		            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton)
		            {             
		             Intent intent = new Intent(); 
		             intent.setClass(SignUpScreen.this, FeedScreen.class);
		             startActivity(intent);
		             finish();
		            } 
		        }); 
		            
		            AlertDialog alertDialog = alert.create();
		            alertDialog.show();	                
	               Log.e("-----At SignUpScreen-----","-signUp method----results="+results);	               
	               }
	                catch(Exception e)
	                {
	                	
	                }

	                Looper.loop(); //Loop in the message queue
	            }
	        };
	        t.start(); 
				
		}
	
  public void takingPhotoThroughVamera ()
  {
	 final String [] items =new String [] {"Take from camera", "Select from gallery"};				
	 ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
	 AlertDialog.Builder builder = new AlertDialog.Builder(this);		
		builder.setTitle("Select Image");
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() 
		{
			public void onClick( DialogInterface dialog, int item ) 
			{ 
				//pick from camera
				if (item == 0) 
				{
				 Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
				 intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
				try {
					 intent.putExtra("return-data", true);
					 startActivityForResult(intent, PICK_FROM_CAMERA);
					}
				catch (ActivityNotFoundException e) 
				{
				  e.printStackTrace();
				}
				}
				else 
				{ //pick from file
					Intent intent = new Intent();					
	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);	                
	                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		} );
	 
	final AlertDialog dialog = builder.create();
	dialog.show();
	mImageView  = (ImageView) findViewById(R.id.iv_photo);
	
	}
	 
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) 
   {
 	    if (resultCode != RESULT_OK) return; 	   
 	    switch (requestCode) 
 	    {
 		    case PICK_FROM_CAMERA:
 		    	doCrop(); 		    	
 		    	break;
 		    	
 		    case PICK_FROM_FILE: 
 		    	mImageCaptureUri = data.getData();
 		    	
 		    	doCrop();
 	    
 		    	break;	    	
 	    
 		    case CROP_FROM_CAMERA:	    	
 		        Bundle extras = data.getExtras(); 	
 		        if (extras != null) 
 		        {	        	
 		          Bitmap photo = extras.getParcelable("data"); 		            
 		          mImageView.setImageBitmap(photo);
 		        } 	
 		        File f = new File(mImageCaptureUri.getPath()); 		        
 		        if (f.exists()) f.delete(); 	
 		        break;
 	    }
 	}
  
  private void doCrop()
  {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();  	
  	    Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");      
        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );      
        int size = list.size();      
        if (size == 0) 
        {	        
      	Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();      	
          return;
        } 
        else 
        {
      	  intent.setData(mImageCaptureUri);          
          intent.putExtra("outputX", 200);
          intent.putExtra("outputY", 200);
          intent.putExtra("aspectX", 1);
          intent.putExtra("aspectY", 1);
          intent.putExtra("scale", true);
          intent.putExtra("return-data", true);
          
      	if (size == 1) 
      	{
      	 Intent i = new Intent(intent);
	     ResolveInfo res = list.get(0);
	     i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
	     startActivityForResult(i, CROP_FROM_CAMERA);
      	} 
      	else {
		        for (ResolveInfo res : list) 
		        {
		         final CropOption co = new CropOption();
		         co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
		         co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
		         co.appIntent= new Intent(intent);
		         co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
		         cropOptions.add(co);
		        }
	        
		        CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
		        
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Choose Crop App");
		        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
		            public void onClick( DialogInterface dialog, int item ) {
		                startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
		            }
		        });
	        
		        builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
		            @Override
		            public void onCancel( DialogInterface dialog ) {
		               
		                if (mImageCaptureUri != null ) {
		                    getContentResolver().delete(mImageCaptureUri, null, null );
		                    mImageCaptureUri = null;
		                }
		            }
		        } );
		        
		        AlertDialog alert = builder.create();
		        
		        alert.show();
      	}
      }
	}
}
