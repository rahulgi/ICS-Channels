package com.example.channellist;

import org.json.JSONArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log; 


class ChannelHelper extends SQLiteOpenHelper{
	/* ADDED BY RAHUL WITH RESPECT TO ASTROS */
	private static final String TAG = "DatabaseAdapter";
	
	public static final String KEY_ROWID = "_id";
	
	public static final String TABLE_CHANNELS = "Channels"; 
	   	public static final String CHANNELS_KEY_CHANNEL = "channel";
       	public static final String CHANNELS_KEY_FEED_URI = "feeduri";
	
	private static final String TABLE_ASTROS = "astros";
		private static final String ASTROS_KEY_CONTROL_FEED = "control";
		private static final String ASTROS_KEY_CHANNELS = "channels";
		private static final String ASTROS_KEY_ACTIVE = "active"; // channel URI
		// Maybe add Musubi identifier such that Astro can be added to channel using SDK rather than through interface.. is this possible?
	
	private static final String DATABASE_CREATE_ASTROS_TABLE = 
	"create table " + TABLE_ASTROS + " (" + KEY_ROWID + " integer primary key autoincrement, "
    + ASTROS_KEY_CONTROL_FEED + " text not null, " + ASTROS_KEY_CHANNELS + " text not null, " + ASTROS_KEY_ACTIVE + " text not null)";
	
	private static final String DATABASE_CREATE_CHANNELS_TABLE = 
			"create table " + TABLE_CHANNELS + " (" 
					+ KEY_ROWID + " integer primary key autoincrement, " 
					+ CHANNELS_KEY_FEED_URI + " text not null, "
					+ CHANNELS_KEY_CHANNEL + " text not null)";
	
	private static final String[] STORY_COLUMNS = new String[]{ KEY_ROWID, ASTROS_KEY_CONTROL_FEED, ASTROS_KEY_CHANNELS, ASTROS_KEY_ACTIVE };
	/* END ADDED BY */
	
	
//	private static final String DATABASE_PATH = "/data/data/com.example.channellist/databases/";
	private static final String DATABASE_NAME = "channels2.db";
	private static final int SCHEMA_VERSION = 1; 
	
	//private static final String TABLE_NAME = "Channels";
//	private static final String COLUMN_ID = "_id";
//	private static final String COLUMN_TITLE = "channel_name"; 
	
	//public SQLiteDatabase dbSqlite; 
	
	//private final Context myContext; 
	
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
		return(getReadableDatabase().rawQuery("SELECT _id, channel FROM Channels", null)); 
	}
	
	
	public String getName(Cursor c){
		return(c.getString(1)); 
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
		
	}*/
	
	/*public void createDatabase(){
	createDB(); 
}*/

/*private void createDB(){
	boolean dbExist = DBExists(); 
	
	if(!dbExist){
		this.getReadableDatabase();
		copyDBFromResource(); 
	}
	
}*/
	
	/*private boolean DBExists(){
	SQLiteDatabase db = null; 
	
	try{
		String databasePath = DATABASE_PATH + DATABASE_NAME; 
		db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
		db.setLocale(Locale.getDefault()); 
		db.setLockingEnabled(true); 
		db.setVersion(1); 
	
	} catch (SQLiteException e) {
		Log.e("SqlHelper", "database not found");
	}
	
	if(db !=null){
		db.close();
	}
	return db != null ? true : false; 
}*/

/*public void openDataBase() throws SQLException {
	
	String dbPath = DATABASE_PATH + DATABASE_NAME; 
	dbSqlite = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE); 
}*/

/*@Override
public synchronized void close() {
	if (dbSqlite != null){
		dbSqlite.close(); 
	}
	super.close(); 
}*/

/*public Cursor getCursor() { 
	SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	queryBuilder.setTables(TABLE_NAME);
	String[] asColumnsToReturn = new String[] {COLUMN_ID, COLUMN_TITLE};
	
	Cursor mCursor = queryBuilder.query(dbSqlite, asColumnsToReturn, null, null, null, null, "channel_name ASC"); 
	
	return mCursor; 
}*/
	
	/* ADDED BY RAHUL WITH RESPECT TO ASTROS */
	public long addAstro(String controlFeed)  {
		ContentValues astroValues = new ContentValues();
		astroValues.put(ASTROS_KEY_CONTROL_FEED, controlFeed);
		astroValues.put(ASTROS_KEY_CHANNELS, (new JSONArray().toString()));
		astroValues.put(ASTROS_KEY_ACTIVE, "");
		return getWritableDatabase().insert(TABLE_ASTROS, null, astroValues);
	}
	/* END ADDED BY */
}

