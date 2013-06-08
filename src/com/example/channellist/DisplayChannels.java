package com.example.channellist;

import mobisocial.socialkit.musubi.DbFeed;
import mobisocial.socialkit.musubi.Musubi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayChannels extends Activity {
	
	/*Database Helper*/
	private ChannelAdapter chAdapter = null;
	private ChannelHelper dbChannelHelper = null;
	private Cursor dbCursor = null;
	private EditText channel_name; 	
	private ListView channel_list; 
	
	private static final String ACTION_CREATE_FEED = "musubi.intent.action.CREATE_FEED";
	private static final int REQUEST_CREATE_FEED = 1;

	private static final String TAG = "Channel_Creator";

	private Uri feedUri = null;
	private String feedText;

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
		try{
			setContentView(R.layout.my_channels);
			
			if (!Musubi.isMusubiInstalled(this)) {
				Log.d(TAG, "Musubi is not installed.");
			}
			
			channel_list = (ListView)findViewById(R.id.channel_list_v);
		
			channel_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    public void onItemClick(AdapterView parent, View v, int position, long id){
			    //	Intent intent = new Intent(v.getContext(), ChannelUI.class);
//					startActivityForResult(intent,0);
			    }
			});
			
			channel_name = (EditText)findViewById(R.id.channel_name_field);
			

			/*Load Database of Channels */
			dbChannelHelper = new ChannelHelper(this);
			dbCursor = dbChannelHelper.getAll(); 
			startManagingCursor(dbCursor); 
			chAdapter = new ChannelAdapter(dbCursor); 
			channel_list.setAdapter(chAdapter);
			
		} catch (Exception e) {
			Log.e("ERROR", "ERROR AT: " + e.toString()); 
			e.printStackTrace(); 
		}
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		dbChannelHelper.close(); 
	}
	
	@SuppressWarnings("deprecation")
	public void addChannel (View v){
		try {	
			if(channel_name.getText().length() != 0){
				Intent intent = new Intent(ACTION_CREATE_FEED);
				startActivityForResult(intent, REQUEST_CREATE_FEED);
			}

			
		} catch (Exception e) {
			Log.e("ERROR", "ERROR AT: " + e.toString()); 
			e.printStackTrace(); 
		}	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CREATE_FEED && resultCode == RESULT_OK) {
			if (data == null || data.getData() == null) {
				return;
			}
			
			feedUri = data.getData();
			Log.d(TAG, "feedUri: " + feedUri.toString());
			
			Musubi musubi = Musubi.getInstance(this);
			
			DbFeed feed = musubi.getFeed(feedUri);
			feedText = feed.toString();
			
			dbChannelHelper.insert(channel_name.getText().toString(), feedUri.toString());			
			dbCursor.requery();
			
			channel_name.setText("");
		}
	}
		
	
	class ChannelAdapter extends CursorAdapter { 
		ChannelAdapter(Cursor c){
			super(DisplayChannels.this,c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			ChannelHolder_C chHolder = (ChannelHolder_C)row.getTag();
			chHolder.populateFrom(c, dbChannelHelper); 
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater(); 
			View row = inflater.inflate(R.layout.channel_list_item, parent, false);
			ChannelHolder_C holder = new ChannelHolder_C(row); 
			row.setTag(holder); 
			return(row);
		}		
	}
	
	class ChannelHolder_C{
		public TextView channel_title = null, channel_info = null;
		
		ChannelHolder_C(View row){
		 channel_title = (TextView) row.findViewById(R.id.channel_title);
		 channel_info = (TextView) row.findViewById(R.id.channel_sub);
		}
		
		void populateFrom(Cursor c, ChannelHelper r){
			channel_title.setText(r.getName(c));
			//channel_title.setText(c.getString(c.getColumnIndexOrThrow("feed_uri")));
			//	c.moveToFirst();
				Log.d("MAIN", c.getColumnCount() + " AND " + c.getColumnName(1) + " AND " + c.getColumnName(1));
		}
	}
}
