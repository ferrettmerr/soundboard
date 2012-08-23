package com.lawrencebrewer.soundboard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lawrencebrewer.soundboard.datamodel.Sound;
import com.lawrencebrewer.soundboard.telephony.Call;

/**
 * Activity that contains a list of sounds to play during a call. Has the capability to end the call.
 * This activity responds to messages posted to the handler given to the API.
 * 
 * @author Larry Brewer
 *
 */
public class SoundboardActivity extends Activity{
	Call call;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundboard);
				
		createCall();		
		call.startCall();
		
		ArrayList<Sound> sounds = Sound.getSounds();
		createList(sounds);
	}

	private void createList(ArrayList<Sound> sounds) {
		final ListView listView = (ListView)findViewById(R.id.soundboard_list);
		listView.setAdapter(new SoundboardListAdapter(this, R.layout.soundboard_list_item, sounds));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Sound sound = (Sound) listView.getItemAtPosition(position);
				call.sendSound(sound.url);
				
			}
		});
	}

	private void createCall() {
		TelephonyManager telephonyManager =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = telephonyManager.getLine1Number();
		
		String toNumber = getIntent().getStringExtra("toNumber");
		
		Handler handler = new APIHandler(this);
		
		call = new Call(toNumber,phoneNumber, handler, this);
	}

	
	/**
	 * List adapter that displays sounds with a picture, title, and url to the sound.
	 * @author Larry Brewer
	 *
	 */
	public class SoundboardListAdapter extends ArrayAdapter<Sound>{
		
		public SoundboardListAdapter(Context context, int textViewResourceId,
				List<Sound> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = convertView;
			if (view == null){
				LayoutInflater inflater = (LayoutInflater) SoundboardActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.soundboard_list_item, parent, false);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.title = (TextView)view.findViewById(R.id.soundboard_list_item_title);
				viewHolder.url = (TextView)view.findViewById(R.id.soundboard_list_item_url);

				view.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) view.getTag();

			Sound sound = this.getItem(position);

			holder.title.setText(sound.title);
			holder.url.setText(sound.url);
			
			return view;
		}
		
	}
	
	/**
	 * Handles the responses from the API.
	 * @author Larry
	 *
	 */
	private static class APIHandler extends Handler{
		SoundboardActivity activity;
		
		public APIHandler(SoundboardActivity activity) {
			this.activity = activity;
		}
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	public void endCall(View view){
		call.endCall();
		finish();
	}
	
	private class ViewHolder{
		public TextView title;
		public TextView url;
	}	
}