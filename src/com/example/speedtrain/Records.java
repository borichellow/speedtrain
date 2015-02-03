package com.example.speedtrain;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Records extends Activity implements OnClickListener{
	
	int[] fields = {R.id.textRecord1, R.id.textRecord2, R.id.textRecord3, R.id.textRecord4, R.id.textRecord5, R.id.textRecord6, R.id.textRecord7};
	
	@Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);

        Button btnBack = (Button) findViewById(R.id.btnBack );
        TextView textRecord = null;
        
        btnBack.setOnClickListener((OnClickListener) this);
        
		//DataBase
		DBHelper dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor c = db.query("records", new String[] {"score"}, null, null, null, null, "score DESC");
		
		if (c.moveToFirst()) {

			int i = 0;
			do {
				int scoreColIndex = c.getColumnIndex("score");
				textRecord = (TextView) findViewById(fields[i]);
				textRecord.setText(Integer.toString(c.getInt(scoreColIndex)));
				i++;
				if (i >= fields.length){break;}
			} while (c.moveToNext());
		}
		c.close();
		dbHelper.close();
    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btnBack :
				finish();
				break;
		}
	}

}
