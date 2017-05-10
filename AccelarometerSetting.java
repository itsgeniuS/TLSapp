package com.example.accidentdetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class AccelarometerSetting extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelarometer_setting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accelarometer_setting, menu);
		return true;
	}
	
	public void apply_prcs(View view)
	{
		Toast.makeText(AccelarometerSetting.this,"Sensor Setting Applied",Toast.LENGTH_LONG).show();
		Intent myint=new Intent(AccelarometerSetting.this,Dashboard.class);
		startActivity(myint);
	}

}
