package com.example.channellist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mAstrosButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		initializeView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		/*IntentFilter iff = new IntentFilter();
		iff.addAction("mobisocial.intent.action.DATA_RECEIVED");
		this.registerReceiver(this.messageReceiver, iff);*/
		super.onResume();
	}
	
	private void initializeView() {
		mAstrosButton = (Button) findViewById(R.id.my_astros);
		mAstrosButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), DisplayAstros.class));
			}
		});
	}

	public void MyChannels (View v){
		Intent intent = new Intent(v.getContext(), DisplayChannels.class);
		startActivityForResult(intent,0);
		
	}
	
}
