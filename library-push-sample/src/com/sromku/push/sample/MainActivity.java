package com.sromku.push.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sromku.push.Push;

public class MainActivity extends Activity implements Callback {

	private final static String PROJECT_ID = "819600420437";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.register);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Push push = Push.getInstance()
						.setContext(MainActivity.this)
						.setHandler(new Handler(MainActivity.this));
				push.register(PROJECT_ID);
			}
		});
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == Push.PUSH_REGISTERED) {
			String registrationId = (String) msg.obj;
			Toast.makeText(getApplicationContext(), "Registered to push messaging: " + registrationId, Toast.LENGTH_SHORT).show();			
		} else if (msg.what == Push.PUSH_MESSAGE_RECIEVED) {
			Toast.makeText(getApplicationContext(), "Message received: " + msg.obj, Toast.LENGTH_SHORT).show();
		}
		return false;
	}

}
