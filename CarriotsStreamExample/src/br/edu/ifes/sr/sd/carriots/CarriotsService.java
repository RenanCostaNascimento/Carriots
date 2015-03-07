package br.edu.ifes.sr.sd.carriots;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Representa o serviço que ficará rodando em plano de fundo.
 * 
 * @author Renan
 * 
 */
public class CarriotsService extends Service {

	private ConnectivityChangeReceiver receiver;
	private IntentFilter intentFilter;
	public static String email;

	@Override
	public void onCreate() {
		// intentFilter serve para filtrar quais broadcasts devem ser ouvidos
		// pelo BroadcastReceiver
		intentFilter = new IntentFilter();
		intentFilter.addAction(CONNECTIVITY_SERVICE);
		receiver = new ConnectivityChangeReceiver();
	}

	/**
	 * Método chamado ao invocar o método startService na Activity.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		email = intent.getStringExtra("email");
		registerReceiver(receiver, intentFilter);

		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
