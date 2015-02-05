package com.example.speedtrain;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

public class DBHelper extends SQLiteOpenHelper {
	private static DBHelper instance;
	final String TABLE_RECORDS = "records";
	final String ROW_SCRORE = "score";

	private DBHelper(Context context) {
		super(context, "speedTrainDB", null, 1);
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+ TABLE_RECORDS +" (id INTEGER PRIMARY KEY AUTOINCREMENT,date LONG,"+ ROW_SCRORE +" INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void setNewRecord(int record) {
		ContentValues cv = new ContentValues();
		cv.put("date", SystemClock.uptimeMillis());
		cv.put(ROW_SCRORE, record);
		this.getWritableDatabase().insert(TABLE_RECORDS, null, cv);
		this.close();
	}

	public ArrayList<Integer> getRecords() {
		Cursor c = this.getWritableDatabase().query("records",
				new String[] { ROW_SCRORE }, null, null, null, null, ROW_SCRORE + " DESC");
		ArrayList<Integer> records = new ArrayList<Integer>();
		if (c.moveToFirst()) {
			int i = 0;
			do {
				int scoreColIndex = c.getColumnIndex(ROW_SCRORE);
				records.add(i, c.getInt(scoreColIndex)) ;
				i++;
			} while (c.moveToNext());
		}
		this.close();
		return records;
	}

}
