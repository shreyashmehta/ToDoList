package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	//public static final String ID = "id";
	//public static int idValue = 0;
	
	
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "MyMemoDB";
	private static final String TABLE_NAME = "mymemo";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TABLE = "create table mymemo(title text not null, description text not null);";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
	}
	
	//-----Opens the Database-----
	
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//-----Close the Database-----
	
	public void close() {
		DBHelper.close();
	}
	
	//-----Insertion of data------
	
	public long insertMemo(String title, String description) {
		ContentValues cv = new ContentValues();
		//cv.put(ID, idValue);
		cv.put(TITLE, title);
		cv.put(DESCRIPTION, description);
		//idValue++;
		return db.insert(TABLE_NAME, null, cv);
	}

	//-----Retrieve the data-----
	
	public Cursor getMemo() throws SQLException {
		db = DBHelper.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] {TITLE, DESCRIPTION}, 
				null, null, null, null, null);
		
		if(cursor != null) 
			cursor.moveToFirst();
		return cursor;
	}
	
	//-----Update a memo-----
}
