package com.example.accidentdetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ProgressBar;

public class Splash_activity extends Activity {
	protected boolean active=true;
	protected int splashTime=3000;
	private int progressStatus = 0;
    private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_activity);
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		try{
			new Thread(new Runnable() {
	            public void run() {
	                while(progressStatus < 100){
	                    // Update the progress status
	                    progressStatus +=1;

	                    // Try to sleep the thread for 20 milliseconds
	                    try{
	                        Thread.sleep(20);
	                  
	                 

	                    // Update the progress bar
	                    handler.post(new Runnable() {
	                        public void run() {
	                            pb.setProgress(progressStatus);
	                            // Show the progress on TextView
	                            
	                        }
	                    });
	                    
	                    }catch(InterruptedException e){
	                        e.printStackTrace();
	                    }
	                	
	                }
	            }
	        }).start(); // Start the operation
			}catch(Exception Ex){
				Ex.printStackTrace();
			}
			 Thread splashThread=new Thread()
				{
					public void run()
					{
						try
						{
							int waited=0;

							while(active && (waited<splashTime))
							{
								sleep(100);
								if(active)
								{
									waited +=100;
								}
							}
						}

						catch(Exception e)
						{
							e.toString();
						}

						finally	
						{
							Intent int1=new Intent(getApplicationContext(),MainActivity.class);
							startActivity(int1);
							finish();

						}
					}
				};

				splashThread.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_activity, menu);
		return true;
	}

}

