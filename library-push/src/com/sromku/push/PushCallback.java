package com.sromku.push;

import android.os.Handler.Callback;
import android.os.Message;

public abstract class PushCallback implements Callback {

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == Push.PUSH_REGISTERED) {
			String registrationId = (String) msg.obj;
			onRegistration(registrationId);
		} else if (msg.what == Push.PUSH_MESSAGE_RECIEVED) {
			onMessage(msg.obj);
		}
		return false;
	}
	
	protected abstract void onRegistration(String registrationId);
	
	protected abstract void onMessage(Object message);

}
