package com.example.channellist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import mobisocial.socialkit.Obj;
import mobisocial.socialkit.musubi.DbFeed;
import mobisocial.socialkit.musubi.DbIdentity;
import mobisocial.socialkit.musubi.DbObj;
import mobisocial.socialkit.musubi.Musubi;
import mobisocial.socialkit.obj.MemObj;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void MyChannels (View v){
		Intent intent = new Intent(v.getContext(), DisplayChannels.class);
		startActivityForResult(intent,0);
		
	}
	
}
