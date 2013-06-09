package com.example.channellist;

import mobisocial.socialkit.musubi.Musubi;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ChannelUI extends Activity {
	
	private static final String ACTION_EDIT_FEED = "musubi.intent.action.EDIT_FEED";
	private static final int REQUEST_EDIT_FEED = 2;
	
	private static final String ADD_TITLE = "member_header";
    private static final String ADD_HEADER = "Channel Members";
	
	private static final String TAG = "Channel_UI"; 
	
	private Uri FeedUri = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.channel_ui);		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.channel_menu, menu);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String uri_string = extras.getString("feedURI");
		    FeedUri = Uri.parse(uri_string); 
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.add_members:
			if (!Musubi.isMusubiInstalled(this)) {
				Log.d(TAG, "Musubi is not installed.");
				return super.onOptionsItemSelected(item);
			}
			
			Intent intent = new Intent(ACTION_EDIT_FEED);
			intent.putExtra(ADD_TITLE, ADD_HEADER);   	
	    	intent.setData(FeedUri);
			startActivityForResult(intent, REQUEST_EDIT_FEED);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	public void onResume() {
		/*IntentFilter iff = new IntentFilter();
		iff.addAction("mobisocial.intent.action.DATA_RECEIVED");
		this.registerReceiver(this.messageReceiver, iff);*/
		super.onResume();
	}
	
	
	
}
