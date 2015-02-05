package com.example.speedtrain;

import android.app.Activity;
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
        
		int[] records = DBHelper.getInstance(this).getRecords();
		
		for (int i =0; i < records.length; i++){
			if (i >= fields.length){break;}
			textRecord = (TextView) findViewById(fields[i]);
			textRecord.setText(Integer.toString(records[i]));
		}
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
