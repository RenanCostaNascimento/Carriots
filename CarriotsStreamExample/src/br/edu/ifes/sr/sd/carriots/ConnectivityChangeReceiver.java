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

/**
 * Classe que representa o BroadcastReceiver. Fica atrelado ao serviço,
 * escutando eventos de mudanças de rede.
 * 
 * @author Renan
 * 
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {

	/**
	 * Método chamado quando algum evento que esteja sendo monitorado acontece.
	 */
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

	/**
	 * Essa classe é a forma que o Android tem de criar uma tarefa assíncrona.
	 * 
	 * @author Renan
	 * 
	 */
	private class ConnectToCarriots extends AsyncTask<WifiInfo, Void, Void> {

		@Override
		protected Void doInBackground(WifiInfo... arg0) {
			sendData(arg0[0]);
			return null;
		}

		@SuppressWarnings("deprecation")
		protected void sendData(WifiInfo data) {
			String dispositivo = "RazrI@renancn.renancn";
			String apikey = "22da2a360650481bd17039e0b18af5a4a77e620b4e006be3cb395b59c32be82a";
			String requisicao = "https://api.carriots.com/streams";
			String decodedString = "";
			String resposta = "";
			String json = "";
			URL url;
			HttpURLConnection connection = null;
			try {
				url = new URL(requisicao);
				connection = (HttpURLConnection) url.openConnection();
				// adiciona parâmetros à requisição
				connection.setDoOutput(true);
				connection.addRequestProperty("carriots.apikey", apikey);
				connection.addRequestProperty("Content-Type",
						"application/json");
				connection.setRequestMethod("POST");
				// cria o json
				json = "{" + "\"protocol\":\"v2\"," + "\"device\":\""
						+ dispositivo + "\",\"at\":\"now\"," + "\"data\":{\"ssid\":"
						+ data.getSSID() + ",\"ip\":\""
						+ Formatter.formatIpAddress(data.getIpAddress())
						+ "\",\"email\":\"" + CarriotsService.email + "\"}}";
				
				final OutputStreamWriter osw = new OutputStreamWriter(
						connection.getOutputStream());
				osw.write(json);
				osw.close();
				// interpreta a mensagem recebida do carriots
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				while ((decodedString = in.readLine()) != null) {
					resposta += decodedString;
				}
				in.close();
				connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				resposta = "" + e;
				Log.d("Return_Message", resposta);
			}
		}

	}

}
