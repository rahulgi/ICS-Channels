package com.example.channellist;

import mobisocial.socialkit.musubi.DbObj;
import mobisocial.socialkit.musubi.Musubi;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {

	private static final String TAG = "MessageReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent == null)
			Log.d(TAG, "no intent");
		
		Log.d(TAG, "message received: " + intent);
		
		Uri objUri = intent.getParcelableExtra("objUri");
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

		Log.d(TAG, obj.getType());
		
		JSONObject json = obj.getJson();
		if (json == null) {
			Log.d(TAG, "no json attached to obj");
			return;
		}
		Log.d(TAG, "json: " + json);
		
		/*if (obj.getSender().isOwned()) {
			return; // remove this if you want to process messages you send
		}*/
		
		Log.d(TAG, "MESSAGE RECEIVED!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
