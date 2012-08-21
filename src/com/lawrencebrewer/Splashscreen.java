package com.lawrencebrewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lawrencebrewer.soundboard.R;

/**
 * Presents the splash screen for 3 seconds and loads the MainActivity.
 * 
 * @author Larry Brewer
 */
public class Splashscreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        
        
        new Thread(new Runnable() {
			
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				startActivity(new Intent(Splashscreen.this, MainActivity.class));
				finish();
			}
		}).start();
    }
}