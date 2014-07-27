push
====

Simple Android GCM push library.<br> 
Test with live web page written in Go: http://test.sromku.com/

## Usage

1. Create callback:
	``` java
	PushCallback pushCallback = new PushCallback() {

		@Override
		protected void onRegistration(String registrationId) {
			// Do something, like send this val to your server
		}

		@Override
		protected void onMessage(Object message) {
			// Here you get the message from your server
		}
	};
	```

2. Setup the push
	``` java
	Push push = Push.getInstance()
		.setContext(context)
		.setHandler(new Handler(pushCallback));
	```

3. Start listening
	``` java
	push.register(PROJECT_ID);
	```

## Setup

1. Add reference from your project to `library-push` library project. 
2. In your app manifest add this:

	* Add permissions
		``` xml
		<uses-permission android:name="android.permission.INTERNET" />
	    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />

		```

	* In `<application>` tag add:
		``` xml
		<meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />

        <receiver
            android:name="com.sromku.push.Push$PushReceiver"
            android:exported="true"
            android:permission="com.google.android.c2dm.permission.SEND" >
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <action android:name="com.google.android.c2dm.intent.REGISTRATION" />

                <category android:name="YOUR_APP_PACKAGE" />
            </intent-filter>
        </receiver>
		```

		Change the `YOUR_APP_PACKAGE` to your app package name.

## Test

http://test.sromku.com/ - I uploaded test site with all source code here. Just set all needed params and send push to yourself. 

## Thanks
Go push - https://github.com/alexjlockwood/gcm

## License

    Copyright 2013-present Roman Kushnarenko

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
