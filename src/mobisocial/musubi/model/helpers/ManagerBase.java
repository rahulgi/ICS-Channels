package mobisocial.musubi.model.helpers;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public abstract class ManagerBase {
    final SQLiteOpenHelper mDatabase;
    final SQLiteDatabase mDirectDatabase;

    public ManagerBase(SQLiteOpenHelper databaseSource) {
        mDatabase = databaseSource;
        mDirectDatabase = null;
    }

    public ManagerBase(SQLiteDatabase db) {
    	assert(db != null);
    	mDatabase = null;
        mDirectDatabase = db;
    }

    SQLiteDatabase initializeDatabase() {
    	if(mDirectDatabase != null)
    		return mDirectDatabase;
        return mDatabase.getWritableDatabase();
    }

    void bindField(SQLiteStatement statement, int field, Object val) {
        if (val == null) {
            statement.bindNull(field);
        } else if (val instanceof String) {
            statement.bindString(field, (String)val);
        } else if (val instanceof Integer) {
            statement.bindLong(field, new Long((Integer)val));
        } else if (val instanceof Long) {
            statement.bindLong(field, (Long)val);
        } else if (val instanceof byte[]) {
            statement.bindBlob(field, (byte[])val);
        } else if (val instanceof Double) {
            statement.bindDouble(field, (Double)val);
        } else if (val instanceof Float) {
            statement.bindDouble(field, new Double((Float)val));
        } else {
            throw new IllegalArgumentException("Could not bind " + val);
        }
    }

    /**
     * Closes any compiled statements and other database resources opened by this manager.
     */
    public abstract void close();

    @Override
	protected void finalize() throws Throwable {
		close();
	}
}
