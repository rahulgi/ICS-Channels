package com.example.channellist;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import mobisocial.socialkit.Obj;
import mobisocial.socialkit.musubi.DbFeed;
import mobisocial.socialkit.musubi.DbIdentity;
import mobisocial.socialkit.musubi.Musubi;
import mobisocial.socialkit.obj.MemObj;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.FileA3D.EntryType;
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
	
    private Musubi mMusubi;

	private static final String ACTION_CREATE_FEED = "musubi.intent.action.CREATE_FEED";
	private static final int REQUEST_CREATE_FEED = 1;

	private static final String ACTION_EDIT_FEED = "musubi.intent.action.EDIT_FEED";
	private static final int REQUEST_EDIT_FEED = 2;

	private static final String TAG = "Channel_Creator";
	
	private static final String ADD_TITLE = "member_header";
    private static final String ADD_HEADER = "Channel Members";

	private Uri feedUri = null;
	private String feedText;

	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
	    if (Musubi.isMusubiInstalled(this)) {
            mMusubi = Musubi.getInstance(this);
        }
	    
		try{
			setContentView(R.layout.my_channels);
			
			if (!Musubi.isMusubiInstalled(this)) {
				Log.d(TAG, "Musubi is not installed.");
			}
			
			channel_list = (ListView)findViewById(R.id.channel_list_v);
		
			channel_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    public void onItemClick(AdapterView parent, View v, int position, long id){		
			    	/*if (mMusubi == null) {
			                // get people to the market to install Musubi
			                Log.d(TAG, "Musubi not installed");
			                return; 
			    	}*/
			    	Cursor c = dbChannelHelper.getChannel(id);
			    	String rowId = c.getString(0);
			    	String retrieved_uri = c.getString(1);
			    	
			    	Intent intent = new Intent(v.getContext(), ChannelUI.class);
			    	intent.putExtra("feedURI", retrieved_uri);
			    	intent.putExtra("rowId", rowId);
					startActivityForResult(intent,0);
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
			checkForAstros(feed);
			feedText = feed.toString();
			
			dbChannelHelper.insert(channel_name.getText().toString(), feedUri.toString());			
			dbCursor.requery();
			
			channel_name.setText("");
		}
	}
	
	/* Check for any owned astros in the feed members and add the feed to the channel. */
	private void checkForAstros(DbFeed feed) {
		List<DbIdentity> members = feed.getMembers();
		Iterator itr = members.iterator();
		while (itr.hasNext()) {
			DbIdentity member = (DbIdentity) itr.next();
			Cursor astro = dbChannelHelper.getAstroByMusubiID(member.getId());
			if (astro != null && astro.getCount() > 0) {
				dbChannelHelper.addAstroMemberFeed(astro.getLong(astro.getColumnIndex(ChannelHelper.KEY_ROWID)), 
						feed.getUri().toString());
				DbFeed controlFeed = Musubi.getInstance(this).getFeed(Uri.parse(astro.getString(astro.getColumnIndex(ChannelHelper.ASTROS_KEY_CONTROL_FEED))));
				
				JSONObject json = new JSONObject();
	            try {
	                json.put(MainActivity.ASTRO_KEY_ACTION, MainActivity.ASTRO_ACTION_ADD);
	                json.put(MainActivity.ASTRO_KEY_FEED, feed.getUri().toString());
	                
	            } catch (JSONException e) {
	                Log.e(TAG, "json error", e);
	                return;
	            }
	            controlFeed.postObj(new MemObj("channellist", json));
	            
	            dbChannelHelper.setAstroActiveFeed(astro.getLong(astro.getColumnIndex(ChannelHelper.KEY_ROWID)), 
	            		feed.getUri().toString());
	            
	            // aUTOMATICALLY activates new channel on Astro
	            json = new JSONObject();
	            try {
	                json.put(MainActivity.ASTRO_KEY_ACTION, MainActivity.ASTRO_ACTION_ACTIVATE);
	                json.put(MainActivity.ASTRO_KEY_FEED, feed.getUri().toString());
	                
	            } catch (JSONException e) {
	                Log.e(TAG, "json error", e);
	                return;
	            }
	            controlFeed.postObj(new MemObj("channellist", json));
			}
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
			channel_title.setText(c.getString(c.getColumnIndexOrThrow("channel")));
			channel_info.setText(c.getString(c.getColumnIndexOrThrow("feeduri")));
		}
	}
}
