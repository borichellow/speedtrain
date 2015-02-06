package com.example.speedtrain;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Records extends Activity implements OnClickListener {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.records);
		
		LinearLayout recordFields = (LinearLayout) findViewById(R.id.recordFields);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		TextView textRecord = null;

		btnBack.setOnClickListener((OnClickListener) this);

		ArrayList<Integer> records = DBHelper.getInstance(this).getRecords();		
		for (int i = 0; i < recordFields.getChildCount(); i++) {
			textRecord = ((TextView)recordFields.getChildAt(i));
			textRecord.setText(Integer.toString(records.get(i)));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnBack:
			finish();
			break;
		}
	}

}
