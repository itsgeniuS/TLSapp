package com.example.accidentdetection;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ViewRegisteredUser extends Activity {
	DBAdapter db;
	TextView letstext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_registered_user);
		db = new DBAdapter(ViewRegisteredUser.this);
		letstext=(TextView)findViewById(R.id.textView1);
		letstext.setText("");
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_registered_user, menu);
		return true;
	}

	public void redrievedb(View view)
	{
		try{
			db.open();
			int i=1;
			Cursor c = db.getAllContacts();
			if (c.moveToFirst())
			{
				do {
					//DisplayContact(c);
					letstext.append("\n"+i+")"+c.getString(0)+":"+c.getString(1));
					i+=1;
					
				} while (c.moveToNext());
			}
			db.close();
			}
			
			catch(Exception exp)
			{
				System.out.println("Message"+exp.getMessage());
			}
	}
	
	public void DisplayContact(Cursor c)
	{
			letstext.append(c.getString(0));
		
	}

	
}
