package com.glockr.UI;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class HowItWorks extends Activity
{
	private WebView mwebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.howitworks);
		
		mwebView = (WebView) findViewById(R.id.webView1);
		mwebView.getSettings().setJavaScriptEnabled(true);
		mwebView.loadUrl("http://www.google.com");
	}	
	
}
