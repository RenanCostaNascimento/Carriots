package br.edu.ifes.sr.sd.carriots;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startService(View v) {
		Intent intent = new Intent(this, CarriotsService.class);
		Toast.makeText(this, "Iniciando serviço...", Toast.LENGTH_SHORT).show();
		this.startService(intent);
		
	}

	public void stopService(View v) {
		Intent intent = new Intent(this, CarriotsService.class);
		this.stopService(intent);
		Toast.makeText(this, "Parando serviço...", Toast.LENGTH_SHORT).show();
	}

}
