package com.glockr.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class LauncherScreen extends Activity implements OnClickListener {

	private Button mHowitworks,mSignup,mStartbrowsing;
	private WebView mwebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcherscreen);
		
		mHowitworks = (Button)findViewById(R.id.how_it_works);
		mSignup = (Button)findViewById(R.id.signup_or_signin);
		mStartbrowsing = (Button)findViewById(R.id.start_browsing);		
		mHowitworks.setOnClickListener(this);
		mSignup.setOnClickListener(this);
		mStartbrowsing.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View view) 
	{
		// TODO Auto-generated method stub
		if(view==mHowitworks)
		{
		 /*Intent intentHowitworks = new Intent();
       	 intentHowitworks.setClass(this, HowItWorks.class);
         startActivity(intentHowitworks);	*/
		mwebView = new WebView(this);
		mwebView.getSettings().setJavaScriptEnabled(true);
		mwebView.loadUrl("http://www.google.com");
		}
		else if(view==mSignup)
		{
		 Intent intentSignUp = new Intent();
         intentSignUp.setClass(this, SignUpOptions.class);
         startActivity(intentSignUp);
			
		}
		else if(view==mStartbrowsing)
		{
		 Intent intentFeed = new Intent();
         intentFeed.setClass(this, FeedScreen.class);
         startActivity(intentFeed); 	
		}
		
	}
	
	
	

	    

}
