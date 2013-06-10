package mobisocial.musubi.provider;

import android.database.sqlite.SQLiteOpenHelper;

public interface DBProvider {
	public SQLiteOpenHelper getDatabaseSource();
}
