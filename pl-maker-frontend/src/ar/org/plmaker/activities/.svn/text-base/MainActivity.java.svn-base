package ar.org.plmaker.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.application.PlMakerApplication;
import ar.org.plmaker.holder.ContextHolder;

import com.google.inject.Inject;

public class MainActivity extends RoboActivity {

	private static final String DEFAULT_SERVER_PORT = "80";

	@InjectView(R.id.main_button)
	private Button button;

	@InjectView(R.id.main_address_text)
	private EditText address_text;

	@InjectView(R.id.main_port_text)
	private EditText port_text;

	@Inject
	private PlMakerService plMakerService;

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final SharedPreferences settings = getSharedPreferences(
				PlMakerApplication.PREFS_NAME, 0);
		final String serverHost = settings.getString("serverHost", "");
		address_text.setText(serverHost);
		final String serverPort = settings.getString("serverPort",
				DEFAULT_SERVER_PORT);
		port_text.setText(serverPort);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (address_text.getText().length() == 0) {
					Toast.makeText(view.getContext(),
							R.string.enter_server_address, Toast.LENGTH_LONG)
							.show();
				} else if (port_text.getText().length() == 0) {
					Toast.makeText(view.getContext(),
							R.string.enter_server_port, Toast.LENGTH_LONG)
							.show();
				} else {
					final String serverPortStr = port_text.getText().toString();
					if (!isInteger(serverPortStr)) {
						Toast.makeText(view.getContext(),
								R.string.server_port_must_be_numeric,
								Toast.LENGTH_LONG).show();
					} else {
						final String serverHost = address_text.getText()
								.toString();
						final int serverPort = Integer.valueOf(serverPortStr);
						final SharedPreferences.Editor editor = settings.edit();
						editor.putString("serverHost", serverHost);
						editor.putString("serverPort", serverPortStr);
						editor.commit();
						ContextHolder.getInstance()
								.setServerAddress(serverHost);
						ContextHolder.getInstance().setServerPort(serverPort);
						final String pathSeparator = plMakerService
								.getPathSeparator();
						ContextHolder.getInstance().setPathSeparator(
								pathSeparator);
						final Intent myIntent = new Intent(view.getContext(),
								MenuActivity.class);
						startActivityForResult(myIntent, 0);
					}
				}
			}

		});
	}
}
