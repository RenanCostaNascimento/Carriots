package br.edu.ifes.sr.sd.carriots;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Representa a Activity do aplicativo.
 * 
 * @author Renan
 * 
 */
public class MainActivity extends ActionBarActivity {

	EditText emailText;

	/**
	 * Chamado quando a aplica��o inicia.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		emailText = (EditText) findViewById(R.id.editText1);
	}

	/**
	 * M�todo chamado quando o bot�o de iniciar servi�o � apertado.
	 * 
	 * @param button
	 *            o bot�o que foi apertado.
	 */
	public void startService(View button) {
		if (!emailText.getEditableText().toString().equals("")) {
			Intent intent = new Intent(this, CarriotsService.class);
			intent.putExtra("email", emailText.getText().toString());
			Toast.makeText(this, "Iniciando servi�o...", Toast.LENGTH_SHORT)
					.show();
			this.startService(intent);
		} else {
			Toast.makeText(this, "Digite um email.", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * M�todo chamado quando o bot�o de parar servi�o � apertado.
	 * 
	 * @param button
	 *            o bot�o que foi apertado.
	 */
	public void stopService(View button) {
		Intent intent = new Intent(this, CarriotsService.class);
		this.stopService(intent);
		Toast.makeText(this, "Parando servi�o...", Toast.LENGTH_SHORT).show();
	}

}
