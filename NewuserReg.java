package com.example.accidentdetection;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewuserReg extends Activity {
	EditText edName,edMobilenum;
	String name,mobilenum;
	 DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser_reg);
		edName=(EditText)findViewById(R.id.editText1);
		edMobilenum=(EditText)findViewById(R.id.editText2);
		db = new DBAdapter(NewuserReg.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newuser_reg, menu);
		return true;
	}
	
	public void Registeron_db(View view)
	{
		name=edName.getText().toString();
		mobilenum=edMobilenum.getText().toString();
		
		    db.open();
			
			db.insertContact(name,mobilenum);
			
			db.close();
			
			Toast.makeText(NewuserReg.this,"New User Registered", Toast.LENGTH_LONG).show();
	      
		
	}

}
