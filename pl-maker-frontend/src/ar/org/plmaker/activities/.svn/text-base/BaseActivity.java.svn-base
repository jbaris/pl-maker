package ar.org.plmaker.activities;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.widget.Toast;
import ar.org.plmaker.holder.ContextHolder;

public abstract class BaseActivity extends RoboActivity {

	@Override
	protected void onResume() {
		super.onResume();
		if (ContextHolder.getInstance().getServerAddress() == null) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.enter_server_info), Toast.LENGTH_SHORT)
					.show();
			final Intent myIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(myIntent);
		}
	}

}
