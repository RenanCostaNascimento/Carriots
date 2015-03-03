package br.edu.ifes.sr.sd.carriots;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class CarriotsService extends Service {

	ConnectivityChangeReceiver receiver;
	IntentFilter intentFilter;
	
	@Override
	public void onCreate() {
		intentFilter = new IntentFilter();
		intentFilter.addAction(CONNECTIVITY_SERVICE);
		receiver = new ConnectivityChangeReceiver();
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
				
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
