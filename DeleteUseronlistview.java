package com.example.accidentdetection;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteUseronlistview extends Activity {
	DBAdapter db;
	String[] users = new String[20];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_useronlistview);
		db = new DBAdapter(DeleteUseronlistview.this);
		viewuseronlistview();
		final ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.activity_list_item, android.R.id.text1, users);
		  listView.setAdapter(adapter); 
		  
		  listView.setOnItemClickListener(new OnItemClickListener() {
			  
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition     = position;
               
               // ListView Clicked item value
               String  itemValue    = (String) listView.getItemAtPosition(position);
                  
                // Show Alert 
                Toast.makeText(getApplicationContext(),
                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                  .show();
             
              }

         }); 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_useronlistview, menu);
		return true;
	}

	public void ShowAllusers(View view) {

	}

	public void viewuseronlistview() {
		try {
			db.open();
			int i = 0;
			Cursor c = db.getAllContacts();
			if (c.moveToFirst()) {
				do {
					// DisplayContact(c);
					// letstext.append("\n"+i+")"+c.getString(0)+":"+c.getString(1));
					users[i] = c.getString(0) + ":" + c.getString(1);
					i += 1;

				} while (c.moveToNext());
			}
			db.close();

			
		}

		catch (Exception exp) {
			System.out.println("Message" + exp.getMessage());
		}

	}

}
