package mobisocial.pictureperfect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import mobisocial.socialkit.Obj;
import mobisocial.socialkit.musubi.DbFeed;
import mobisocial.socialkit.musubi.DbIdentity;
import mobisocial.socialkit.musubi.DbObj;
import mobisocial.socialkit.musubi.Musubi;
import mobisocial.socialkit.obj.MemObj;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity{

	private static final String TAG = "Locator";
	
	private static final String ACTION_CREATE_FEED = "musubi.intent.action.CREATE_FEED";


	private static final int REQUEST_CREATE_FEED = 1;
	private static final int REQUEST_GET_CONTENT = 3;

	private ArrayList<String> listItems = new ArrayList<String>();

	    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	private ArrayAdapter<String> adapter;
	    
	private Uri feedUri = null;
	private final String KEY_FEED_URI = "feedUri";
	
	private TextView uriPresenter;
	
	private List<String> channels = new ArrayList<String>();
	
	private ChannelList channel_list; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		uriPresenter = (TextView) findViewById(R.id.uriPresenter);
		String channelsString = getPreferences(android.content.Context.MODE_PRIVATE).getString("CHANNELS", null);
		
		if(channelsString != null){
			Log.d(TAG, "HERE IS URI String: " + channelsString);
		
			StringTokenizer tokens = new StringTokenizer(channelsString, ":");
			while(tokens.hasMoreTokens()){
				channels.add(tokens.nextToken());
			}
		}
			
		
//		if(channelsString.contains(",")){
//			channels = Arrays.asList(channelsString.split(","));
//		} else {
//			channels = Arrays.asList(channelsString.split(""));
//		}
		
		Log.d(TAG, "HERE IS URI ARRAY" + channels.toString());
	/*	Iterator<String> it = channels.iterator();
		while(it.hasNext())
		{
			Log.d(TAG, "HERE IS FEEDURI" + it.next());
		}*/
		

		String uri = getPreferences(android.content.Context.MODE_PRIVATE).getString(KEY_FEED_URI, null);
		if (uri != null) {
			feedUri = Uri.parse(uri);
			uriPresenter.setText(uri);
		}
		
    
	}

	@Override
	public void onResume() {
		IntentFilter iff = new IntentFilter();
		iff.addAction("mobisocial.intent.action.DATA_RECEIVED");
		this.registerReceiver(this.messageReceiver, iff);
		super.onResume();
	}
	
	@Override
	public void onPause() {
		this.unregisterReceiver(this.messageReceiver);
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.create_feed:
			if (!Musubi.isMusubiInstalled(this)) {
				Log.d(TAG, "Musubi is not installed.");
				return super.onOptionsItemSelected(item);
			}
		
			Intent intent = new Intent(ACTION_CREATE_FEED);
			//Intent intent = new Intent(ACTION_EDIT_FEED);
			//intent.setData(feedUri);
			//startActivityForResult(intent, REQUEST_EDIT_FEED);
			startActivityForResult(intent, REQUEST_CREATE_FEED);
			return true;
		case R.id.view_channels:
			String channelsString = getPreferences(android.content.Context.MODE_PRIVATE).getString("CHANNELS", null);
			Log.d(TAG, "HERE: " + channelsString);
			
			
		default:
			return super.onOptionsItemSelected(item);
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
			uriPresenter.setText(feedUri.toString());
			
			channels.add(feedUri.toString());
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < channels.size(); i++) {
			    sb.append(channels.get(i)).append(",");
			}
			getPreferences(android.content.Context.MODE_PRIVATE).edit().putString("CHANNELS", sb.toString()).apply();
			
			getPreferences(android.content.Context.MODE_PRIVATE).edit().putString(KEY_FEED_URI, feedUri.toString()).apply();
			if (feed == null) {
				Log.d(TAG, "feed is null?!?");
				return;
			}
			
			List<DbIdentity> members = feed.getMembers();
			for (DbIdentity member: members) {
				Log.d(TAG, "member: " + member.getName() + ", " + member.getId());
			}
			
			JSONObject json = new JSONObject();
			try {
				json.put("can't see this", "invisible");
				json.put(Obj.FIELD_HTML, "hi");
			} catch (JSONException e) {
				Log.e(TAG, "json error", e);
				return;
			}
			feed.postObj(new MemObj("socialkit-locator", json));
			Log.d(TAG, "json obj posted: " + json.toString());
			
		} else if (requestCode == REQUEST_GET_CONTENT && resultCode == RESULT_OK){
		
             
			
			
		}
	}
	

	 public void displayChannels(View view) {
		 
		Log.d(TAG, "displayChannel");
		setContentView(R.layout.view_channels);
		
		
			
		 /*	Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), REQUEST_GET_CONTENT);*/
		 
	/*	 Intent photoPickerIntent = new Intent(ACTION_GET_CONTENT);
		 photoPickerIntent.setType("image/*");
		 startActivityForResult(photoPickerIntent, 1);*/
	 }
	 
	 public void returnHome(View view){
		 setContentView(R.layout.activity_main); 
	 }
	 
	 public void generateButtons(View view){
		 View ll = findViewById(R.id.view_channels);
		 Log.d(TAG, ll.toString());
	 }
	 
	
	 
	 public String getPath(Uri uri) {
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
	        if(cursor!=null)
	        {
	            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
	            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
	            int column_index = cursor
	            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	            return cursor.getString(column_index);
	        }
	        else return null;
	    }

	private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null)
				Log.d(TAG, "no intent");
			
			Log.d(TAG, "message received: " + intent);
			
			Uri objUri = (Uri) intent.getParcelableExtra("objUri");
			if (objUri == null) {
				Log.d(TAG, "no object found");
				return;
			}
			Log.d(TAG, "obj uri: " + objUri.toString());
				
			Musubi musubi = Musubi.forIntent(context, intent);
			DbObj obj = musubi.objForUri(objUri);
			
			if (obj == null) {
				Log.d(TAG, "obj is null?");
				return;
			}
			
			if (feedUri == null) {
				feedUri = obj.getContainingFeed().getUri();
				if (feedUri != null) {
					uriPresenter.setText(feedUri.toString());
				}
			}
			
			JSONObject json = obj.getJson();
			if (json == null) {
				Log.d(TAG, "no json attached to obj");
				return;
			}
			Log.d(TAG, "json: " + json);
			
			if (obj.getSender().isOwned()) {
				// do stuff specific to when message is sent by me
				return;
			}
			// Process message
		}
	};
	
}
