package com.example.accidentdetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Dashboard extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}
	
	public void Accident_detection(View view)
	{
		Intent myint= new Intent(Dashboard.this,Accidentdetection.class);
		startActivity(myint);
		
		Intent intent = new Intent(this, Accelarometerservice.class);
		startService(intent);
		
		
	}
	
	public void register(View view)
	{
		Intent myint=new Intent(Dashboard.this,EmergencyUsers.class);
		startActivity(myint);
	}
	
	public void Accident_Setting(View view)
	{
		Intent myint=new Intent(Dashboard.this,EmergencyUsers.class);
		startActivity(myint);
		
	}
	
	public void Drowsiness(View view)
	{
		Intent launchIntent = getPackageManager().getLaunchIntentForPackage("mano.facialprocessing");
		if (launchIntent != null) { 
		    startActivity(launchIntent);//null pointer check in case package name was not found
		}
		
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("org.opencv.samples.fd");
		startActivity( LaunchIntent );
	}

}
