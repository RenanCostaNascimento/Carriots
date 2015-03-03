package br.edu.ifes.sr.sd.carriots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			ConnectToCarriots carriots = new ConnectToCarriots();
			carriots.execute(wifiInfo);
		}
	}

	private class ConnectToCarriots extends AsyncTask<WifiInfo, Void, Void> {

		@Override
		protected Void doInBackground(WifiInfo... arg0) {
			sendData(arg0[0]);
			return null;
		}

		protected void sendData(WifiInfo data) {
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
						+ device + "\",\"at\":\"now\"," + "\"data\":{\"ssid\":"
						+ data.getSSID() + ",\"ip\":\""
						+ Formatter.formatIpAddress(data.getIpAddress())
						+ "\"}}";
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
				Log.d("Return_Message", returnMsg);
			}
		}

	}

}
