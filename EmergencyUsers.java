package com.example.accidentdetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class EmergencyUsers extends Activity {
	 DBAdapter db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_users);
		db = new DBAdapter(EmergencyUsers.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emergency_users, menu);
		return true;
	}
	
	public void newusers(View view)
	{
		Intent ymmint=new Intent(EmergencyUsers.this,NewuserReg.class);
		startActivity(ymmint);
	}
	
	public void viewusers(View view)
	{
		Intent ymmint=new Intent(EmergencyUsers.this,ViewRegisteredUser.class);
		startActivity(ymmint);
		
	}
	
	public void deletetable(View view)
	{
		
		    db.open();
			
			db.deleteTable();
			
			db.close();
			
			Toast.makeText(EmergencyUsers.this,"Emergency User Values Cleared",Toast.LENGTH_LONG).show();
			Toast.makeText(EmergencyUsers.this,"No Emergency users",Toast.LENGTH_LONG).show();
			
	
	}
	
	public void deletparticular(View view)
	{
		
		Intent myint=new Intent(EmergencyUsers.this,DeleteUseronlistview.class);
		startActivity(myint);
	}
	

}
