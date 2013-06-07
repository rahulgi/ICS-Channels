package com.example.channellist;

import mobisocial.socialkit.musubi.DbFeed;
import mobisocial.socialkit.musubi.Musubi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChannelUI extends Activity {
	
    private static final String TAG = "ChannelUI";
	
    private Musubi mMusubi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void EditChMembers(View v){
		if (mMusubi == null) {
            // get people to the market to install Musubi
            Log.d(TAG, "Musubi not installed");
          /*  new InstallMusubiDialogFragment().show(getSupportFragmentManager(), null);*/
            return; 
        }
        Log.d(TAG, "trying to add followers ");
        String action = ACTION_CREATE_FEED;
        int request = REQUEST_CREATE_FEED;
        Uri feedUri = null;
        MFeed feedEntry = mFeedManager.getFeed(EntryType.App); // TODO: make this generic
        if (feedEntry != null) {
            feedUri = feedEntry.feedUri;
        }
        if (feedUri != null) {
            DbFeed feed = mMusubi.getFeed(feedUri);
            if (feed != null) {
                action = ACTION_EDIT_FEED;
                request = REQUEST_EDIT_FEED;
            } else {
                // Delete broken feed entries
                mFeedManager.deleteFeed(EntryType.App); // TODO: make this generic
            }
        }
        Intent intent = new Intent(action);
        if (feedUri != null) {
            intent.setData(feedUri);
            intent.putExtra(ADD_TITLE, ADD_HEADER);
        }
        startActivityForResult(intent, request);
		
	}
	
}