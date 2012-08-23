package com.lawrencebrewer.soundboard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lawrencebrewer.soundboard.telephony.Call;

public class MainActivity extends Activity{
	
	Call call;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	}

	public void call(View view){
		String toNumber = ((EditText)findViewById(R.id.main_phone_number)).getText().toString();

		if (toNumber != null && toNumber.length() > 0 ){
			Intent i = new Intent(this, SoundboardActivity.class);
			i.putExtra("toNumber", toNumber);
			
			startActivity(i);			
		}
		else {
			Toast.makeText(this, "Please enter a valid Phone Number", Toast.LENGTH_SHORT).show();
		}

	} 
}
