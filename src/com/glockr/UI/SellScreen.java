package com.glockr.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SellScreen extends Activity implements OnClickListener,OnItemSelectedListener
{
	//private Button mFeedButton,mSellButton,mInboxButton,mProfileButton;
	private static final String[] items={"Android","Bluetooth","Chrome","Docs","Email",
        "Facebook","Google","Hungary","Iphone","Korea","Machintosh",
        "Nokia","Orkut","Picasa","Singapore","Turkey","Windows","Youtube"};
	 private TextView userSelection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellscreen);
		
		/**
		 * Getting the view from layout through the ids.
		 * 
		 * */
		
		/*mFeedButton = (Button)findViewById(R.id.feedbottomfeed);
		mSellButton = (Button)findViewById(R.id.sellbottomfeed);
		mInboxButton = (Button)findViewById(R.id.inboxbottomfeed);
		mProfileButton = (Button)findViewById(R.id.profilebottomfeed);*/
		
		/**
		 * setting the click listener for the buttons.
		 * 
		 * */
		
		/*mFeedButton.setOnClickListener(this);
		mSellButton.setOnClickListener(this);
		mInboxButton.setOnClickListener(this);
		mProfileButton.setOnClickListener(this);*/
		userSelection=(TextView)findViewById(R.id.user_selection);
		Spinner my_spin=(Spinner)findViewById(R.id.my_spinner);
        my_spin.setOnItemSelectedListener(this);
        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_spin.setAdapter(aa);
        
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		/*if(view == mSellButton)
		{
		
		}*/
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		// TODO Auto-generated method stub
		userSelection.setText(items[arg2]);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}	
	
}
