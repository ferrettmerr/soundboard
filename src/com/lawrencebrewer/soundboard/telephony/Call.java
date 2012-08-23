package com.lawrencebrewer.soundboard.telephony;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lawrencebrewer.soundboard.activities.R;
/**
 * Class to handle the interactions with the server. 
 * This class is not meant to be a real world implementation but a quick example of web calls
 * There should be an underlying HTTPRequest API for ease of use especially if multiple android applications will use TelApi.
 * 
 * @author Larry Brewer
 *
 */
public class Call {
	String fromNumber;
	String toNumber;
	SoftReference<Handler> softHandler;
	String authSID;
	String authToken;
	String callSID;
	String baseURL;
	
	public Call(String toNumber, String fromNumber, Handler handler, Context context) {
		this.fromNumber = fromNumber;
		this.toNumber = toNumber;
		this.softHandler = new SoftReference<Handler>(handler);
		this.authSID = context.getString(R.string.authSID);
		this.authToken = context.getString(R.string.tokenSID);
		this.baseURL = context.getString(R.string.baseURL);
	}

	public void startCall(){
		new Thread(new Runnable() {

			public void run() {
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(baseURL + "Accounts/" + authSID +"/Calls.json");

					UsernamePasswordCredentials creds = new UsernamePasswordCredentials(authSID, authToken);
					httppost.addHeader(new BasicScheme().authenticate(creds, httppost));

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
					nameValuePairs.add(new BasicNameValuePair("From", fromNumber));
					nameValuePairs.add(new BasicNameValuePair("To", toNumber));		
					nameValuePairs.add(new BasicNameValuePair("Url", "http://lawrencebrewer.com/response.php"));
					nameValuePairs.add(new BasicNameValuePair("Method", "GET"));		
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					HttpResponse response = httpclient.execute(httppost);
					String responseString = EntityUtils.toString(response.getEntity());
					
					Log.d("Server Call", responseString);
					
					JSONObject responseObj = null;
					responseObj = new JSONObject(responseString);
					Call.this.callSID = responseObj.getString("sid");
					
					Message message = new Message();
					message.obj = "Call id: " + Call.this.callSID;
					
					Handler handler = Call.this.softHandler.get();
					if (handler != null){
						handler.sendMessage(message);						
					}
				
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AuthenticationException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	public void endCall(){
		new Thread(new Runnable() {

			public void run() {
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(baseURL + "Accounts/" + authSID +"/Calls/" + callSID);

					UsernamePasswordCredentials creds = new UsernamePasswordCredentials(authSID, authToken);
					httppost.addHeader(new BasicScheme().authenticate(creds, httppost));

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("Status", "Completed"));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					httpclient.execute(httppost);
				
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AuthenticationException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void sendSound(final String url){
		new Thread(new Runnable() {

			public void run() {
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(baseURL+"Accounts/" + authSID +"/Calls/" + callSID + "/Play");

					UsernamePasswordCredentials creds = new UsernamePasswordCredentials(authSID, authToken);
					httppost.addHeader(new BasicScheme().authenticate(creds, httppost));

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("Sounds", url));
					nameValuePairs.add(new BasicNameValuePair("Loop", "false"));
					nameValuePairs.add(new BasicNameValuePair("Mix", "true"));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					httpclient.execute(httppost);
				
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AuthenticationException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
