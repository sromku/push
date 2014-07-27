package com.sromku.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class Push {

	private static Push mInstance = null;

	private static final String EXTRA_REGISTRATION_ID = "registration_id";
	private static final String ACTION_GCM_REGISTRATION = "com.google.android.c2dm.intent.REGISTRATION";
	private static final String ACTION_GCM_RECIEVE = "com.google.android.c2dm.intent.RECEIVE";

	public static final int PUSH_REGISTERED = 2001;
	public static final int PUSH_UNREGISTERED = 2002;
	public static final int PUSH_MESSAGE_RECIEVED = 2003;
	public static final int PUSH_REGISTERED_FAIL = 2004;
	public static final int PUSH_UNREGISTERED_FAIL = 2005;

	private GoogleCloudMessaging mGoogleCloudMessaging;
	private static Handler mHandler;

	private Push() {
	}

	public static Push getInstance() {
		if (mInstance == null) {
			mInstance = new Push();
		}
		return mInstance;
	}

	/**
	 * Set context to work from
	 * 
	 * @param context
	 * @return {@link Push}
	 */
	public Push setContext(Context context) {
		mGoogleCloudMessaging = GoogleCloudMessaging.getInstance(context);
		return this;
	}

	/**
	 * Set callback handler
	 * 
	 * @param handler
	 * @return {@link Push}
	 */
	public Push setHandler(Handler handler) {
		mHandler = handler;
		return this;
	}

	/**
	 * Register to Google cloud push messaging.
	 * 
	 * @param projectIds
	 *            The project id
	 */
	public void register(String... projectIds) {
		new Register().execute(projectIds);
	}

	/**
	 * Unregister from Google cloud push messaging.
	 */
	public void unregister() {
		new Unregister().execute();
	}

	public static final class PushReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				// if registration action was received
				if (ACTION_GCM_REGISTRATION.equals(intent.getAction())) {
					// get registration id
					String registrationId = intent.getStringExtra(EXTRA_REGISTRATION_ID);

					// if registration was performed
					if (registrationId != null) {
						if (mHandler != null) {
							Message message = mHandler.obtainMessage(PUSH_REGISTERED, registrationId);
							mHandler.sendMessage(message);
						}
					} else {
						if (mHandler != null) {
							Message message = mHandler.obtainMessage(PUSH_UNREGISTERED);
							mHandler.sendMessage(message);
						}
					}
				} else if (ACTION_GCM_RECIEVE.equals(intent.getAction())) {
					// get message
					Bundle extras = intent.getExtras();
					if (extras != null) {
						String data = extras.getString("data");
						if (mHandler != null) {
							Message message = mHandler.obtainMessage(PUSH_MESSAGE_RECIEVED, data);
							mHandler.sendMessage(message);
						}
					} else {
						// TODO
					}
				}
			}
		}
	}

	private class Register extends AsyncTask<String[], Void, Void> {

		@Override
		protected Void doInBackground(String[]... params) {
			try {
				mGoogleCloudMessaging.register(params[0]);
			} catch (Exception e) {
				Message message = mHandler.obtainMessage(PUSH_REGISTERED_FAIL);
				mHandler.sendMessage(message);
			}
			return null;
		}

	}

	private class Unregister extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				mGoogleCloudMessaging.unregister();
			} catch (Exception e) {
				Message message = mHandler.obtainMessage(PUSH_UNREGISTERED_FAIL);
				mHandler.sendMessage(message);
			}
			return null;
		}

	}

}
