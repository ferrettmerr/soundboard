package com.lawrencebrewer.soundboard.datamodel;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sound {
	public String title;
	public String url;

	public static ArrayList<Sound> getSounds(){
		String files = "[{\"title\":\"doh!\",\"url\":\"http://lawrencebrewer.com/HomersDoh.wav!\"},{\"title\":\"Whoopdy Freaking do!\",\"url\":\"http://fozzy42.com/SoundClips/TV/wfreakndo1.wav\"}]";

		try {
			JSONArray musicArray = new JSONArray(files);

			ArrayList<Sound> sounds = new ArrayList<Sound>();
			for (int i = 0; i < musicArray.length(); i++){
				Sound sound = new Sound();

				JSONObject jsonObj = musicArray.getJSONObject(i);

				sound.title = jsonObj.getString("title");
				sound.url = jsonObj.getString("url");

				sounds.add(sound);
			}
			
			return sounds;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
