package com.glockr.UI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glockr.utils.EmailValidator;

public class LoginScreen extends Activity implements OnClickListener
{

	private EditText meditEmail, meditPassword;
	private Button  mloginButton;//mbackButton
	private TextView mforgotPassword;
	private String mstrEmail, mstrPassword,mstrScreenvalue;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);
		
		Intent intentBundle = getIntent();
		Bundle bundle = intentBundle.getExtras();
		mstrScreenvalue = bundle.getString("CHECK_SCREEN");
		
		
		meditEmail = (EditText)findViewById(R.id.emailtextboxloginscreen);
		meditPassword = (EditText)findViewById(R.id.passwordloginscreen);
		//mbackButton = (Button)findViewById(R.id.loginback);
		mloginButton = (Button)findViewById(R.id.loginlogin);
		mforgotPassword = (TextView)findViewById(R.id.forgotpasswordloginscreen);
		
		meditEmail.setOnClickListener(this);
		meditPassword.setOnClickListener(this);
		//mbackButton.setOnClickListener(this);
		mloginButton.setOnClickListener(this);
		mforgotPassword.setOnClickListener(this);
		
		meditEmail.setText("durgesh@techlites.com");
		meditPassword.setText("123456");
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view==mloginButton)
		{
			mstrEmail = meditEmail.getText().toString();
			mstrPassword = meditEmail.getText().toString();			
			Log.e("-----At SignUpScreen-----","---Onclickmethod----mstrEmail="+mstrEmail);			
			if(mstrEmail.equals(null)||mstrPassword.equals(null)||mstrEmail.equals("")||mstrPassword.equals(""))
			{
			Toast.makeText(this, "Please fill the all edit box",Toast.LENGTH_LONG).show();				
			}
			else
			{
			
			EmailValidator emailValidator = new EmailValidator();
			boolean isEmail = emailValidator.validate(mstrEmail);
			if(isEmail)
			{
				signIn();
			Toast.makeText(this, "go for create",Toast.LENGTH_LONG).show();		
			}
			else
			{
				Toast.makeText(this, "check your email",Toast.LENGTH_LONG).show();		
			}
			
			}
			
		}
		/*else if(view==mbackButton)
		{
			if(mstrScreenvalue.equals("0"))
			{
				Intent intent = new Intent();
				intent.setClass(this, SignUpOptions.class);				
				startActivity(intent);	
				finish();
			}
			else if(mstrScreenvalue.equals("1"))
			{
				Intent intent = new Intent();
				intent.setClass(this, SignUpScreen.class);				
				startActivity(intent);	
				finish();
			}
		}*/
			
			else if(view == mforgotPassword)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(LoginScreen.this);
	            alert.setTitle("Forgot Password"); 
	            alert.setMessage("Please Enter Your Email Here");          
	            final EditText input = new EditText(LoginScreen.this);
	            alert.setView(input);	 
	            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton)
	            {	            
	             String str = input.getEditableText().toString();
	             Toast.makeText(LoginScreen.this,str,Toast.LENGTH_LONG).show();                
	            	forgotHttpConnection(str);
	            } 
	        }); 
	            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	               
	                  dialog.cancel();
	              }
	        }); 
	            AlertDialog alertDialog = alert.create();
	            alertDialog.show();	           
	     }
		
		
	}		
		
	
	private void signIn() 
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
	                    HttpPost post = new HttpPost("http://192.168.0.9/magento/new_customer.php");
	                    post.setHeader("Content-type", "application/json");
	                    json.put("customer_email",mstrEmail);
	        			json.put("customer_password", mstrPassword);
	        			
	        			String jsonString = json.toString();
	                    
	                    /*List<NameValuePair> params = new ArrayList<NameValuePair>();
	                    params.add(new BasicNameValuePair("customer_email", mstrEmail));
	                    params.add(new BasicNameValuePair("customer_fname", mstrFname));
	                    params.add(new BasicNameValuePair("customer_lname", mstrLname));
	                    post.setEntity(new UrlEncodedFormEntity(params));*/
	                    
	                    
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
	               String  results=sb.toString();
	               
	               Log.e("-----At login screen-----","-signUp method----results="+results);
	               
	               }
	                catch(Exception e)
	                {
	                	
	                }

	                Looper.loop(); //Loop in the message queue
	            }
	        };
	        t.start(); 
		}
	
	public void forgotHttpConnection(String email)
	{
		String jsonString = "";
		String result = "";		
		String url = "http://192.168.0.14/magento/forgotpwd.php?customer_email="+email;
		
				
	   try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
     	    HttpResponse httpResponse = httpClient.execute(httpGet);
		    HttpEntity httpEntity = httpResponse.getEntity();
	        result = EntityUtils.toString(httpEntity);
			}
			catch(Exception e)
			{
			e.printStackTrace();	
			}		
			
			Log.e("-----At SigninScreen-----","-forgotHttpConnection method----result="+result+"----email="+email);	
	}

}
