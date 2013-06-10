package mobisocial.musubi.model.helpers;

import mobisocial.crypto.IBEncryptionScheme;
import mobisocial.crypto.IBHashedIdentity;
import mobisocial.crypto.IBSignatureScheme;
import mobisocial.musubi.encoding.NeedsKey;
import mobisocial.musubi.model.MEncryptionUserKey;
import mobisocial.musubi.model.MIdentity;
import mobisocial.musubi.model.MSignatureUserKey;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class UserKeyManager {
	final SQLiteOpenHelper databaseSource_;
	final IBSignatureScheme signatureScheme_;
	final IBEncryptionScheme encryptionScheme_;
	
	SQLiteStatement sqlInsertSignatureUserKey_;
	SQLiteStatement sqlInsertEncryptionUserKey_;

	
	public UserKeyManager(IBEncryptionScheme encryptionScheme, IBSignatureScheme signatureScheme, SQLiteOpenHelper databaseSource) {
		databaseSource_ = databaseSource;
		encryptionScheme_ = encryptionScheme;
		signatureScheme_ = signatureScheme;
	}
	
	SQLiteDatabase initializeDatabase() {
		return databaseSource_.getWritableDatabase();
	}

	public void insertSignatureUserKey(MSignatureUserKey key) {
		SQLiteDatabase db = initializeDatabase();
		if(sqlInsertSignatureUserKey_ == null) {
			synchronized (this) {
				if(sqlInsertSignatureUserKey_ == null) {
					sqlInsertSignatureUserKey_ = db.compileStatement(
						"INSERT INTO " + MSignatureUserKey.TABLE + 
						" (" +
						MSignatureUserKey.COL_IDENTITY_ID + "," +
						MSignatureUserKey.COL_WHEN + "," +
						MSignatureUserKey.COL_USER_KEY +
						") " +
						"VALUES (?,?,?)"
					);
				}
			}
		}
		synchronized (sqlInsertSignatureUserKey_) {
			sqlInsertSignatureUserKey_.bindLong(1, key.identityId_);
			sqlInsertSignatureUserKey_.bindLong(2, key.when_);
			sqlInsertSignatureUserKey_.bindBlob(3, key.userKey_);
			key.id_ = sqlInsertSignatureUserKey_.executeInsert();
		}
	}
	
	public void insertEncryptionUserKey(MEncryptionUserKey key) {
		SQLiteDatabase db = initializeDatabase();
		if(sqlInsertEncryptionUserKey_ == null) {
			synchronized (this) {
				if(sqlInsertEncryptionUserKey_ == null) {
					sqlInsertEncryptionUserKey_ = db.compileStatement(
						"INSERT INTO " + MEncryptionUserKey.TABLE + 
						" (" +
						MEncryptionUserKey.COL_IDENTITY_ID + "," +
						MEncryptionUserKey.COL_WHEN + "," +
						MEncryptionUserKey.COL_USER_KEY +
						") " +
						"VALUES (?,?,?)"
					);
				}
			}
		}
		synchronized (sqlInsertEncryptionUserKey_) {
			sqlInsertEncryptionUserKey_.bindLong(1, key.identityId_);
			sqlInsertEncryptionUserKey_.bindLong(2, key.when_);
			sqlInsertEncryptionUserKey_.bindBlob(3, key.userKey_);
			key.id_ = sqlInsertEncryptionUserKey_.executeInsert();
		}
	}
	//TODO: compiled statement
	public IBSignatureScheme.UserKey getSignatureKey(MIdentity from, IBHashedIdentity me) throws NeedsKey.Signature {
		SQLiteDatabase db = initializeDatabase();
		Cursor c = db.query(
			MSignatureUserKey.TABLE, 
			new String[] { MSignatureUserKey.COL_USER_KEY}, 
			MSignatureUserKey.COL_IDENTITY_ID + "=? AND " + MSignatureUserKey.COL_WHEN + "=?",
			new String[] { String.valueOf(from.id_), String.valueOf(me.temporalFrame_)}, 
			null, null, null
		); 
		try {
			while(c.moveToNext()) {
				return new IBSignatureScheme.UserKey(c.getBlob(0));
			}
			throw new NeedsKey.Signature(me);
		} finally {
			c.close();
		}
	}
	//TODO: compiled statement
	public IBEncryptionScheme.UserKey getEncryptionKey(MIdentity to, IBHashedIdentity me) throws NeedsKey.Encryption {
		SQLiteDatabase db = initializeDatabase();
		Cursor c = db.query(
			MEncryptionUserKey.TABLE, 
			new String[] { MEncryptionUserKey.COL_USER_KEY}, 
			MEncryptionUserKey.COL_IDENTITY_ID + "=? AND " + MEncryptionUserKey.COL_WHEN + "=?",
			new String[] { String.valueOf(to.id_), String.valueOf(me.temporalFrame_)}, 
			null, null, null
		); 
		try {
			while(c.moveToNext()) {
				return new IBEncryptionScheme.UserKey(c.getBlob(0));
			}
			throw new NeedsKey.Encryption(me);
		} finally {
			c.close();
		}
	}
}
