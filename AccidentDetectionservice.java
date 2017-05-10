package com.example.accidentdetection;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AccidentDetectionservice extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accident_detectionservice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accident_detectionservice, menu);
		return true;
	}

}
