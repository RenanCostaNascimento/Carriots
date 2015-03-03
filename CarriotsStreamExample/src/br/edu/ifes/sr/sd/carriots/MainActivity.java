package br.edu.ifes.sr.sd.carriots;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void sendStream(View v) {
		new MyAsyncTask().execute();
	}

	private class MyAsyncTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			return sendData(getSSID());
		}

		protected String getSSID() {
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			return wifiInfo.getSSID();
		}

		protected String sendData(String data) {
			String device = "RazrI@renancn.renancn";
			String apikey = "22da2a360650481bd17039e0b18af5a4a77e620b4e006be3cb395b59c32be82a";
			String request = "https://api.carriots.com/streams";
			String decodedString = "";
			String returnMsg = "";
			String writeOut = "";
			URL url;
			HttpURLConnection connection = null;
			try {
				url = new URL(request);
				connection = (HttpURLConnection) url.openConnection();
				// establish the parameters for the http post request
				connection.setDoOutput(true);
				connection.addRequestProperty("carriots.apikey", apikey);
				connection.addRequestProperty("Content-Type",
						"application/json");
				connection.setRequestMethod("POST");
				// construct the json string to be sent
				writeOut = "{" + "\"protocol\":\"v2\"," + "\"device\":\""
						+ device + "\",\"at\":\"now\","
						+ "\"data\":{\"ssid\":" + data + "}}";
				// create an output stream writer and write the json string to
				// it
				System.out.println(writeOut);
				final OutputStreamWriter osw = new OutputStreamWriter(
						connection.getOutputStream());
				osw.write(writeOut);
				osw.close();
				// create a buffered reader to interpret the incoming message
				// from the carriots system
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				while ((decodedString = in.readLine()) != null) {
					returnMsg += decodedString;
				}
				in.close();
				connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				returnMsg = "" + e;
			}
			return returnMsg;
		}

		protected void onPostExecute(String result) {
			// show the message returned from Carriots to the user
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
					.show();
		}
	}

}
