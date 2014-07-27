package com.sromku.push.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sromku.push.Push;
import com.sromku.push.PushCallback;

public class MainActivity extends Activity {

	private final static String PROJECT_ID = "819600420437";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.register);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PushCallback pushCallback = new PushCallback() {

					@Override
					protected void onRegistration(String registrationId) {
						Toast.makeText(getApplicationContext(), "Registered to push messaging: " + registrationId, Toast.LENGTH_SHORT).show();
					}

					@Override
					protected void onMessage(Object message) {
						Toast.makeText(getApplicationContext(), "Message received: " + message, Toast.LENGTH_SHORT).show();
					}
				};

				// set push
				Push push = Push.getInstance()
						.setContext(MainActivity.this)
						.setHandler(new Handler(pushCallback));

				// register
				push.register(PROJECT_ID);
			}
		});
	}

}
