package com.example.channellist;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.channellist.DisplayChannels.ChannelAdapter;
import com.example.channellist.DisplayChannels.ChannelHolder_C;

import mobisocial.socialkit.Obj;
import mobisocial.socialkit.musubi.DbFeed;
import mobisocial.socialkit.musubi.DbIdentity;
import mobisocial.socialkit.musubi.Musubi;
import mobisocial.socialkit.obj.MemObj;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayAstros extends Activity {

	/*Database Helper*/
	private ChannelAdapter chAdapter = null;
	private ChannelHelper dbChannelHelper = null;
	private Cursor dbCursor = null;
	
	private EditText astroName;
	private ListView astroList;
	private Button addAstro;
	
	private static final String ACTION_CREATE_FEED = "musubi.intent.action.CREATE_FEED";
	private static final int REQUEST_CREATE_FEED = 1;
	
	private static final String TAG = "Astro_Adder";
	
	private Uri feedUri = null;
	private String feedText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_astros);
		
		if (!Musubi.isMusubiInstalled(this))
			Log.d(TAG, "Musubi is not installed");
		
		initializeView();

		/*Load Database of Channels */
		dbChannelHelper = new ChannelHelper(this);
		dbCursor = dbChannelHelper.fetchAllAstros(); 
		startManagingCursor(dbCursor); 
		chAdapter = new ChannelAdapter(dbCursor); 
		astroList.setAdapter(chAdapter);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		dbChannelHelper.close(); 
	}
	
	DbFeed raar;
	
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
			raar = feed;
			
			feedText = feed.toString();
			
			List<DbIdentity> members = feed.getMembers();
			DbIdentity astro = members.get(0);
			if (astro.isOwned())
				astro = members.get(1);
			
				
			dbChannelHelper.addAstro(astroName.getText().toString(), astro.getId(), feedUri.toString());			
			dbCursor.requery();
		}
	}
	
	private void initializeView() {
		astroList = (ListView) findViewById(R.id.astro_list);
		astroList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				JSONObject json = new JSONObject();
	            try {
	                json.put("can't see this", "invisible");
	                json.put(Obj.FIELD_HTML, "hi");
	                
	            } catch (JSONException e) {
	                Log.e(TAG, "json error", e);
	                return;
	            }
	            raar.postObj(new MemObj("socialkitdemo", json));
			}
		});
		astroName = (EditText) findViewById(R.id.astro_name_field);
		addAstro = (Button) findViewById(R.id.add_astro_button);
		addAstro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {	
					if(astroName.getText().length() != 0){
						Intent intent = new Intent(ACTION_CREATE_FEED);
						startActivityForResult(intent, REQUEST_CREATE_FEED);
					}

				} catch (Exception e) {
					Log.e("ERROR", "ERROR AT: " + e.toString()); 
					e.printStackTrace(); 
				}	

			}
		});
	}
	
	class ChannelAdapter extends CursorAdapter { 
		ChannelAdapter(Cursor c){
			super(DisplayAstros.this,c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			ChannelHolder_C chHolder = (ChannelHolder_C)row.getTag();
			chHolder.populateFrom(c, dbChannelHelper); 
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater(); 
			View row = inflater.inflate(R.layout.astro_list_item, parent, false);
			ChannelHolder_C holder = new ChannelHolder_C(row); 
			row.setTag(holder); 
			return(row);
		}		
	}
	
	class ChannelHolder_C{
		public TextView astro_name = null;
		
		ChannelHolder_C(View row){
		 astro_name = (TextView) row.findViewById(R.id.astro_name);
		}
		
		void populateFrom(Cursor c, ChannelHelper r){
			astro_name.setText(r.getAstroName(c));
		//	channel_info.setText(feedUri.toString()); 
		}
	}	
}
