package com.example.channellist;

import org.json.JSONArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; 


class ChannelHelper extends SQLiteOpenHelper{
	/* ADDED BY RAHUL WITH RESPECT TO ASTROS */
	private static final String TAG = "DatabaseAdapter";
	
	public static final String KEY_ROWID = "_id";
	
	public static final String TABLE_CHANNELS = "Channels"; 
	   	public static final String CHANNELS_KEY_CHANNEL = "channel";
       	public static final String CHANNELS_KEY_FEED_URI = "feeduri";
	
	private static final String TABLE_ASTROS = "astros";
		private static final String ASTROS_KEY_NAME = "name";
		private static final String ASTROS_KEY_ASTRO_ID = "astro_id"; // is this actually a static id?
		private static final String ASTROS_KEY_CONTROL_FEED = "control";
		private static final String ASTROS_KEY_CHANNELS = "channels";
		private static final String ASTROS_KEY_ACTIVE = "active"; // channel URI
		// Maybe add Musubi identifier such that Astro can be added to channel using SDK rather than through interface.. is this possible?
	
	private static final String DATABASE_CREATE_ASTROS_TABLE = 
	"create table " + TABLE_ASTROS + " (" + KEY_ROWID + " integer primary key autoincrement, "
    + ASTROS_KEY_NAME + " text not null, " + ASTROS_KEY_ASTRO_ID + " text not null, " + 
    ASTROS_KEY_CONTROL_FEED + " text not null, " + ASTROS_KEY_CHANNELS + " text not null, " + ASTROS_KEY_ACTIVE + " text not null)";
	
	private static final String DATABASE_CREATE_CHANNELS_TABLE = 
			"create table " + TABLE_CHANNELS + " (" 
					+ KEY_ROWID + " integer primary key autoincrement, " 
					+ CHANNELS_KEY_CHANNEL + " text not null, "
					+ CHANNELS_KEY_FEED_URI + " text not null)";
	
	private static final String[] ASTRO_COLUMNS = new String[]{ 
		KEY_ROWID, ASTROS_KEY_NAME, ASTROS_KEY_ASTRO_ID, ASTROS_KEY_CONTROL_FEED, ASTROS_KEY_CHANNELS, ASTROS_KEY_ACTIVE };

	private static final String[] CHANNEL_COLUMNS = new String[]{
		KEY_ROWID, CHANNELS_KEY_FEED_URI, CHANNELS_KEY_CHANNEL
	};
	
	
//	private static final String DATABASE_PATH = "/data/data/com.example.channellist/databases/";
	private static final String DATABASE_NAME = "channels2.db";
	private static final int SCHEMA_VERSION = 6; 
	
	public ChannelHelper(Context context){
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		//	this.myContext = context;  
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onOpen(db);
		db.execSQL(DATABASE_CREATE_CHANNELS_TABLE);
		/* ADDED BY RAHUL WITH RESPECT TO ASTROS */
		db.execSQL(DATABASE_CREATE_ASTROS_TABLE);
		/* END ADDED BY */
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/* ADDED BY RAHUL WITH RESPECT TO ASTROS */
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASTROS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);
        onCreate(db);
		/* END ADDED BY */
	}
	
	public void insert(String channel_name, String feedURI){
		try{	
			ContentValues cv = new ContentValues(); 
			cv.put("channel", channel_name);
			cv.put("feeduri", feedURI);
			getWritableDatabase().insert("Channels", null, cv);
		}catch (Exception e) {
			Log.e("ERROR", "ERROR AT: " + e.toString()); 
		}
	}
	
	public Cursor getAll(){
		Cursor c = getReadableDatabase().query(TABLE_CHANNELS, CHANNEL_COLUMNS, "", null, null, null, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}
	
	
	public String getName(Cursor c){
		return(c.getString(1)); 
	}
	
	public Cursor getChannel(long id) {
		Cursor c = getReadableDatabase().query(TABLE_CHANNELS, CHANNEL_COLUMNS, KEY_ROWID + " = " + id, null, null, null, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}
	

	/*private void copyDBFromResource(){
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String dbFilePath = DATABASE_PATH + DATABASE_NAME; 
		
		try{
			inputStream = myContext.getAssets().open(DATABASE_NAME);
			outputStream = new FileOutputStream(dbFilePath); 
			
			byte[] buffer = new byte [1024];
			int length; 
			while((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length); 
			}
			
			outputStream.flush(); 
			outputStream.close();
			inputStream.close();
			
		} catch (IOException e){
			throw new Error("Problem copying db from resource file");
		}
		
	/* ADDED BY RAHUL WITH RESPECT TO ASTROS */
	public long addAstro(String name, String id, String controlFeed)  {
		ContentValues astroValues = new ContentValues();
		astroValues.put(ASTROS_KEY_NAME, name);
		astroValues.put(ASTROS_KEY_ASTRO_ID, id);
		astroValues.put(ASTROS_KEY_CONTROL_FEED, controlFeed);
		astroValues.put(ASTROS_KEY_CHANNELS, (new JSONArray().toString()));
		astroValues.put(ASTROS_KEY_ACTIVE, "");
		return getWritableDatabase().insert(TABLE_ASTROS, null, astroValues);
	}
	
	public Cursor fetchAllAstros() {
		Cursor c = getReadableDatabase().query(TABLE_ASTROS, ASTRO_COLUMNS, "", null, null, null, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}
	
	public String getAstroName(Cursor c) {
		return c.getString(1);
	}
	/* END ADDED BY */
}

