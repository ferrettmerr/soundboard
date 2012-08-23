package com.lawrencebrewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.lawrencebrewer.soundboard.R;

public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TelephonyManager telephonyManager =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = telephonyManager.getLine1Number();
		System.out.println(phoneNumber);
	}


	public void call(View view){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String host = "https://api.telapi.com";
		String authSID = "ACa88d8386a17d4f949f12325dd6c51d95";
		String authToken = "2ce4961cf0024054b09b7ea17395564d";
		

		try {
//			httpclient.getCredentialsProvider().setCredentials(new AuthScope("api.telapi.com", 443), new UsernamePasswordCredentials(authSID, authToken));
			HttpPost httppost = new HttpPost("https://api.telapi.com/2011-07-01/Accounts/" + authSID +"/Calls");


			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(authSID, authToken);
			httppost.addHeader(new BasicScheme().authenticate(creds, httppost));
            
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("From", "(908) 675-5126"));
			nameValuePairs.add(new BasicNameValuePair("To", "(513) 278-7365"));		
			nameValuePairs.add(new BasicNameValuePair("Url", "http://www.google.com"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			Log.d("Server Call", EntityUtils.toString(response.getEntity()));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	} 
}
