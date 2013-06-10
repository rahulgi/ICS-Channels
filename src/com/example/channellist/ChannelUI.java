package com.example.channellist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.SocketException;

import mobisocial.socialkit.Obj;
import mobisocial.socialkit.musubi.Musubi;
import mobisocial.socialkit.obj.MemObj;
import mobisocial.musubi.util.*;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


public class ChannelUI extends Activity {

	private static final String ACTION_EDIT_FEED = "musubi.intent.action.EDIT_FEED";
	private static final int REQUEST_EDIT_FEED = 2;

	private static final String ADD_TITLE = "member_header";
	private static final String ADD_HEADER = "Channel Members";

	private static final String TAG = "Channel_UI"; 

	public static final String CATEGORY_IN_PLACE = "mobisocial.intent.category.IN_PLACE";
	static final String PICSAY_PACKAGE_PREFIX = "com.shinycore.picsay";
	private static final int REQUEST_CAPTURE_MEDIA = 9412;
	private Uri mFeedUri;
	private Uri mImageUri;
	private String mType;

	static final String ACTION_MEDIA_CAPTURE = "mobisocial.intent.action.MEDIA_CAPTURE";

	public static final String PICTURE_SUBFOLDER = "Pictures/Musubi";
	public static final String HTML_SUBFOLDER = "Musubi/HTML";
	public static final String FILES_SUBFOLDER = "Musubi/Files";
	public static final String APPS_SUBFOLDER = "Musubi/Apps";

	public static final int MAX_IMAGE_WIDTH = 1280;
	public static final int MAX_IMAGE_HEIGHT = 720;
	public static final int MAX_IMAGE_SIZE = 40*1024;


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

	@SuppressWarnings("deprecation")
	public void AddPhotos(View v){
		AlertDialog alert = new AlertDialog.Builder(ChannelUI.this).create();
		alert.setTitle("Photo Options"); 
		alert.setButton("From Gallery", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		alert.setButton2("Take Photo", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mFeedUri = FeedUri;

				final File path = new File(Environment.getExternalStorageDirectory(),
						(ChannelUI.this).getPackageName());
				if (!path.exists()) {
					path.mkdir();
				}

				File new_file = new File(path, "image.tmp");
				mImageUri = Uri.fromFile(new_file);

				mType = "image/jpeg";
				Intent intent = new Intent(ACTION_MEDIA_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
				intent.putExtra(Musubi.EXTRA_FEED_URI, FeedUri);

				try {
					startActivityForResult(intent, REQUEST_CAPTURE_MEDIA);
				} catch (ActivityNotFoundException e) {
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, REQUEST_CAPTURE_MEDIA);
				}	
			}
		});

		alert.show(); 
	}
	class CameraCaptureTask extends AsyncTask<Void, Void, Boolean> {

		Throwable mError;	   
		Obj mObj;	    
		String mComment;

		public CameraCaptureTask(String comment) {
			mComment = comment;
			mObj = null;
		}

		protected Boolean doInBackground(Void... params) {

			Uri storedUri =  null;

			//CONTENT CORRAL PASS=======================================================================================
			File contentDir;
			if (mType != null && (mType.startsWith("image/") || mType.startsWith("video/"))) {
				contentDir = new File(Environment.getExternalStorageDirectory(), PICTURE_SUBFOLDER);
			} else {
				contentDir = new File(Environment.getExternalStorageDirectory(), FILES_SUBFOLDER);
			}

			if(!contentDir.exists() && !contentDir.mkdirs()) {
				Log.e(TAG, "failed to create musubi corral directory");
				storedUri = null;
			}
			int timestamp = (int) (System.currentTimeMillis() / 1000L);
			String ext = null;

			if (mType.equals("image/jpeg")) {
				ext = "jpg";
			}

			if (mType.equals("image/png")) {
				ext = "png";
			}

			String fname = timestamp + "-" + mImageUri.getLastPathSegment() + "." + ext;
			File copy = new File(contentDir, fname);
			FileOutputStream out = null;
			InputStream in = null;
			try {
				contentDir.mkdirs();
				in = (ChannelUI.this).getContentResolver().openInputStream(mImageUri);
				BufferedInputStream bin = new BufferedInputStream(in);
				byte[] buff = new byte[1024];
				out = new FileOutputStream(copy);
				int r;
				while ((r = bin.read(buff)) > 0) {
					out.write(buff, 0, r);
				}
				bin.close();
				storedUri = Uri.fromFile(copy);
			} catch (IOException e) {
				Log.w(TAG, "Error copying file", e);
				if (copy.exists()) {
					copy.delete();
				}
				storedUri = null;
			} finally {
				try {
					if (in != null)
						in.close();
					if (out != null)
						out.close();
				} catch (IOException e) {
					Log.e(TAG, "failed to close handle on store corral content", e);
				}
			}


			///PICTURE OBJ PASS=====================================================================
			try {
				mObj = getmObj(storedUri, null); 	                		                
				//=======================================================================
			} catch (IOException e) {
				Log.e(TAG, "Corral photo action had an issue", e);
				try {
					mObj = getmObj(mImageUri,null); //PictureObj.from(getActivity(), mImageUri, true, mComment);
				} catch(Throwable t) {
					Log.e(TAG, "fallback photo action had an issue", t);
				}
			}

			if (mObj == null) {
				return false;
			}

			//EasyTracker.getTracker().trackEvent(MusubiAnalytics.CATEGORY_APP, MusubiAnalytics.ACTION_SEND_OBJ, type, -1);
			//MusubiContentProvider.insertInBackground(obj, mFeedUri, null);

			//public static void insertInBackground(Obj obj, Uri feedUri, String assumedAppId) {

			//==========================================================================
			
			
			//==========================================================================

	        
			return true;
		}

		@Override

		protected void onPostExecute(Boolean result) {

			if (result) {
				//Helpers.emailUnclaimedMembers((ChannelUI.this), mObj, mFeedUri);	            
			} else {
				Toast.makeText((ChannelUI.this), "Failed to capture media.",
						Toast.LENGTH_SHORT).show();	            
			}	      
		}
	}	 

	public MemObj getmObj(Uri storedUri, String mComment) throws IOException{
		boolean referenceOrig = true; 

		ContentResolver cr = (ChannelUI.this).getContentResolver();

		UriImage image = new UriImage((ChannelUI.this), storedUri);
		byte[] data = image.getResizedImageData(MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT, MAX_IMAGE_SIZE);   

		JSONObject base = new JSONObject();
		// Maintain a reference to original file
		try {
			String type = cr.getType(storedUri);
			if (type == null) {
				type = "image/jpeg";
			}


			if (mComment != null && mComment.length() > 0) {
				base.put("text", mComment);
			}

			base.put("mimeType", type);
			if (referenceOrig) {
				base.put("localUri", storedUri.toString());
				String localIp = null;

				//=====================================                 
				try {
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
								.hasMoreElements();) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress()) {
								// 	not ready for IPv6, apparently.
								if (!inetAddress.getHostAddress().contains(":")) {
									localIp = inetAddress.getHostAddress().toString();
								}
							}
						}
					}
				} catch (SocketException ex) {

				}             
				                     
			}

		} catch (JSONException e) {
			Log.e(TAG, "impossible json error possible!");             
		}            	                 	         

		return new MemObj("picture", base, data);	
	}
}
