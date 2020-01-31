package com.example.sangil.pedometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
        Handler handler =new Handler();

		handler.postDelayed(new Runnable ()
		{public void run(){
			Intent intent =new Intent(Intro.this,Pedometer.class);
			startActivity(intent);
			finish();
		}
			
		},1550);
	}
}
