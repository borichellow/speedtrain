package com.example.speedtrain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

public class DBHelper extends SQLiteOpenHelper {
	private static DBHelper instance;

    private DBHelper(Context context) {
      super(context, "speedTrainDB", null, 1);
    }
    
    public static DBHelper getInstance(Context context){
    	if (instance == null){
    		instance = new DBHelper(context);
    	}
    	return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("CREATE TABLE records (id INTEGER PRIMARY KEY AUTOINCREMENT,date LONG,score INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    
    }
    
    public void setNewRecord(int record){
    	ContentValues cv = new ContentValues();
    	cv.put("date", SystemClock.uptimeMillis());
		cv.put("score", record);
		this.getWritableDatabase().insert("records", null, cv);
		this.close();
    }
    
    public int[] getRecords(){
    	Cursor c = this.getWritableDatabase().
    			query("records", new String[] {"score"}, null, null, null, null, "score DESC");
		int[] records = {} ;
		if (c.moveToFirst()) {
			int i = 0;
			do {
				int scoreColIndex = c.getColumnIndex("score");
				records[i] = c.getInt(scoreColIndex);
				i++;
			} while (c.moveToNext());
		}
		this.close();
		return records;
    }

}
