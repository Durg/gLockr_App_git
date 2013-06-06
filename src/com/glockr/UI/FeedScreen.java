package com.glockr.UI;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FeedScreen extends ListActivity implements OnClickListener,OnItemSelectedListener
{
	//private Button mFeedButton,mSellButton,mInboxButton,mProfileButton;
	private static final String[] items={"Android","Bluetooth","Chrome","Docs","Email",
        "Facebook","Google","Hungary","Iphone","Korea","Machintosh",
        "Nokia","Orkut","Picasa","Singapore","Turkey","Windows","Youtube"};
	 private TextView userSelection;
	 private String[] mStrings = { "item 1", "item 2", "item 3", "item 4",
				"item 5", "item 6", "item 7", "item 8", "item 9", "item 10",
				"item 11", "item 12", "item 13", "item 14", "item 15", "item 16",
				"item 17", "item 18", "item 19" };
		private String[] mStrings1 = { "item 11", "item 21", "item 31", "item 41",
				"item 5", "item 6", "item 7", "item 8", "item 9", "item 10",
				"item 111", "item 121", "item 131", "item 141", "item 151", "item 161",
				"item 171", "item 181", "item 191" };
		private String[] mStrings2 = { "item 12", "item 22", "item 32", "item 42",
				"item 52", "item 62", "item 72", "item 82", "item 92", "item 102",
				"item 112", "item 122", "item 132", "item 142", "item 152", "item 162",
				"item 172", "item 182", "item 192" };
		private LinkedList<String> mListItems;
		private PullToRefreshListView mPullRefreshListView;
		private ArrayAdapter<String> mAdapter,mAdapter1,mAdapter2;
		private Button button1, button2, button3,mFeedButton,mSellButton,mInboxButton,mProfileButton;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedscreen);
		
		/**
		 * Getting the view from layout through the ids.
		 * 
		 * */
		mFeedButton = (Button)findViewById(R.id.feedbottomfeed);
		mSellButton = (Button)findViewById(R.id.sellbottomfeed);
		mInboxButton = (Button)findViewById(R.id.inboxbottomfeed);
		mProfileButton = (Button)findViewById(R.id.profilebottomfeed);
		
		/**
		 * setting the click listener for the buttons.
		 * 
		 * */
		
		mFeedButton.setOnClickListener(this);
		mSellButton.setOnClickListener(this);
		mInboxButton.setOnClickListener(this);
		mProfileButton.setOnClickListener(this);
		
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {Toast.makeText(FeedScreen.this,
								"End of List!", Toast.LENGTH_SHORT).show();
					}
				});

		final ListView actualListView = mPullRefreshListView.getRefreshableView();

		mListItems = new LinkedList<String>();
		mListItems.clear();
		mListItems.addAll(Arrays.asList(mStrings));
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListItems.clear();
				mListItems.addAll(Arrays.asList(mStrings));
				mAdapter = new ArrayAdapter<String>(FeedScreen.this,
						android.R.layout.simple_list_item_1, mListItems);
				actualListView.setAdapter(mAdapter);
			}
		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListItems.clear();
				mListItems.addAll(Arrays.asList(mStrings1));
				mAdapter1 = new ArrayAdapter<String>(FeedScreen.this,
						android.R.layout.simple_list_item_1, mListItems);
				actualListView.setAdapter(mAdapter1);
			}
		});
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListItems.clear();
				mListItems.addAll(Arrays.asList(mStrings2));
				mAdapter2 = new ArrayAdapter<String>(FeedScreen.this,
						android.R.layout.simple_list_item_1, mListItems);
				actualListView.setAdapter(mAdapter2);

			}
		});

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListItems);
		
		actualListView.setAdapter(mAdapter);
		mPullRefreshListView.setMode(mPullRefreshListView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
				: Mode.BOTH);
		
		
		
		
		
        
		
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addLast("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
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
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == mSellButton)
		{
		 Intent intentSellScreen = new Intent();
		 intentSellScreen.setClass(this, SellScreen.class);
		 startActivity(intentSellScreen);
		}
	}	
	
}


