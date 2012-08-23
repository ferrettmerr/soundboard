package com.lawrencebrewer.soundboard.datamodel;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of a sound that can be played by the application. 
 * This is more of a stub than anything close to an implementation.
 * 
 * @author Larry Brewer
 *
 */
public class Sound {
	public String title;
	public String url;

	public static ArrayList<Sound> getSounds(){
		String files = "[{\"title\":\"doh!\",\"url\":\"http://lawrencebrewer.com/HomersDoh.mp3\",\"img\":\"http://lawrencebrewer.com/doh.jpg\"},{\"title\":\"Kick\",\"url\":\"http://lawrencebrewer.com/kick.mp3\",\"img\":\"http://lawrencebrewer.com/drum.gif\"}]";

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
