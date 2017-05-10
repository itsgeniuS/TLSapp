package com.example.accidentdetection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class Accelarometerservice extends Service implements
		SensorEventListener {
	DBAdapter db;
	GPSTracker gps;
	double latitude, longtitude;
	private static final int FORCE_THRESHOLD = 350;
	private static final int TIME_THRESHOLD = 100;
	private static final int SHAKE_TIMEOUT = 500;
	private static final int SHAKE_DURATION = 1000;
	private static final int SHAKE_COUNT = 3;

	// private SensorManager mSensorMgr;
	private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
	private long mLastTime;
	// private OnShakeListener mShakeListener;
	private Context mContext;
	private int mShakeCount = 0;
	private long mLastShake;
	private long mLastForce;

	// ----------------------------------------bruno's tutorial stuff
	SensorManager mSensorEventManager;

	Sensor mSensor;

	// BroadcastReceiver for handling ACTION_SCREEN_OFF.
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Check action just to be on the safe side.
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.v("shake mediator screen off", "trying re-registration");
				// Unregisters the listener and registers it again.
				mSensorEventManager
						.unregisterListener(Accelarometerservice.this);
				mSensorEventManager.registerListener(Accelarometerservice.this,
						mSensor, SensorManager.SENSOR_DELAY_NORMAL);
			}
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();

		db = new DBAdapter(Accelarometerservice.this);
		Log.v("shake service startup", "registering for shake");
		gps = new GPSTracker(Accelarometerservice.this);

		mContext = getApplicationContext();
		// Obtain a reference to system-wide sensor event manager.
		mSensorEventManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);

		// Get the default sensor for accel
		mSensor = mSensorEventManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		// Register for events.
		mSensorEventManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		// TODO I'll only register at screen off. I don't have a use for shake
		// while not in sleep (yet)

		// Register our receiver for the ACTION_SCREEN_OFF action. This will
		// make our receiver
		// code be called whenever the phone enters standby mode.
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);

	}

	@Override
	public void onDestroy() {
		// Unregister our receiver.
		unregisterReceiver(mReceiver);

		// Unregister from SensorManager.
		mSensorEventManager.unregisterListener(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't need a IBinder interface.
		return null;
	}

	// -------------end of the tutorial besides the accuracy and sensor change
	// stubs

	public void onShake() {
		// Poke a user activity to cause wake?
		Log.v("onShake", "doing wakeup");
		// send in a broadcast for exit request to the main mediator

		showNotification();
		getlocation();
		sendalertmessage();

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// not used right now
	}

	// Used to decide if it is a shake
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;

		Log.v("sensor", "sensor change is verifying");
		long now = System.currentTimeMillis();

		if ((now - mLastForce) > SHAKE_TIMEOUT) {
			mShakeCount = 0;
		}

		if ((now - mLastTime) > TIME_THRESHOLD) {
			long diff = now - mLastTime;
			float speed = Math.abs(event.values[SensorManager.DATA_X]
					+ event.values[SensorManager.DATA_Y]
					+ event.values[SensorManager.DATA_Z] - mLastX - mLastY
					- mLastZ)
					/ diff * 10000;
			if (speed > FORCE_THRESHOLD) {
				if ((++mShakeCount >= SHAKE_COUNT)
						&& (now - mLastShake > SHAKE_DURATION)) {
					mLastShake = now;
					mShakeCount = 0;

					// call the reaction you want to have happen
					onShake();
				}
				mLastForce = now;
			}
			mLastTime = now;
			mLastX = event.values[SensorManager.DATA_X];
			mLastY = event.values[SensorManager.DATA_Y];
			mLastZ = event.values[SensorManager.DATA_Z];
		}

	}

	public void showNotification() {

		// define sound URI, the sound to be played when there's a notification
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// intent triggered, you can add other intent for other actions
		// Intent intent = new Intent(MainActivity.this,
		// NotificationReceiver.class);
		// PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this,
		// 0, intent, 0);

		// this is it, we'll build the notification!
		// in the addAction method, if you don't want any icon, just set the
		// first param to 0
		Notification mNotification = new Notification.Builder(this)

				.setContentTitle("Accident Detected")
				.setContentText(
						"You Are Strugled In Accident Emergency Alert Send With In 10 Secs")
				.setSmallIcon(R.drawable.picture1)

				.setSound(soundUri)

				.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// If you want to hide the notification after it was selected, do the
		// code below
		// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, mNotification);
	}

	public void cancelNotification(int notificationId) {

		if (Context.NOTIFICATION_SERVICE != null) {
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager nMgr = (NotificationManager) getApplicationContext()
					.getSystemService(ns);
			nMgr.cancel(notificationId);
		}
	}

	public void sendalertmessage() {
		try {
			db.open();
			Cursor c = db.getAllContacts();
			if (c.moveToFirst()) {
				do {
					// DisplayContact(c);
					sendSMSMessage(c.getString(1));

				} while (c.moveToNext());
			}
			db.close();
		} catch (Exception exp) {
			System.out.println("Message" + exp.getMessage());
		}
	}

	protected void sendSMSMessage(String phone) {
		Log.i("Send SMS", "");
		String phoneNo = phone;
		String message = "Accident Detected  Visit this Link For Getting Location: http://maps.google.com/?q="
				+ latitude + "," + longtitude;

		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, message, null, null);
			Toast.makeText(getApplicationContext(), "SMS sent.",
					Toast.LENGTH_LONG).show();
		}

		catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again.", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void getlocation() {
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longtitude = gps.getLongitude();

		} else {

			gps.showSettingsAlert();
		}
	}

}