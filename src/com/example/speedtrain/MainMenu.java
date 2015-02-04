package com.example.speedtrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainMenu extends Activity implements OnClickListener{

	private Button textQuit;
	private Button textPlay;
	private Button textRecords;
	
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textPlay = (Button) findViewById(R.id.train);
        textPlay.setOnClickListener((OnClickListener) this);
        
        textRecords = (Button) findViewById(R.id.records);
        textRecords.setOnClickListener((OnClickListener) this);
        
        textQuit = (Button) findViewById(R.id.quit);
        textQuit.setOnClickListener((OnClickListener) this);
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()){
    		case R.id.train:
    			textPlay.setBackgroundResource(R.drawable.menu_clicked); 
    			Intent intentGame = new Intent(this, Game.class);
    			startActivity(intentGame);
    			break;
    		case R.id.records:
    			textRecords.setBackgroundResource(R.drawable.menu_clicked);
    			Intent intentRecords = new Intent(this, Records.class);
    			startActivity(intentRecords);
    			break;
    		case R.id.quit:
    			textQuit.setBackgroundResource(R.drawable.menu_clicked);
    			finish();
    			break;
    	}
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	textPlay.setBackgroundResource(R.drawable.menu);
    	textRecords.setBackgroundResource(R.drawable.menu);
    }
}
