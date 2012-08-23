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

public class SoundboardActivity extends Activity{
	Call call;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(SoundboardActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
		}
	};
	
	
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
		
		call = new Call(toNumber,phoneNumber, handler, this);
	}

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
	
	
	public void endCall(View view){
		call.endCall();
	}
	
	private class ViewHolder{
		public TextView title;
		public TextView url;
	}	
}
