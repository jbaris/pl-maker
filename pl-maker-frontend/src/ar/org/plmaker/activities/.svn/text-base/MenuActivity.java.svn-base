package ar.org.plmaker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends BaseActivity {

	private void addOpenActivityListener(int buttonId,
			final Class<? extends Activity> activityClass) {
		addOpenActivityListener(buttonId, activityClass, new Bundle());
	}

	private void addOpenActivityListener(int buttonId,
			final Class<? extends Activity> activityClass, final Bundle bundle) {
		final Button button = (Button) findViewById(buttonId);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				final Intent myIntent = new Intent(view.getContext(),
						activityClass);
				myIntent.putExtras(bundle);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	private Bundle getNextActivityClassBundle(
			Class<? extends Activity> nextActivityClass, String title) {
		final Bundle editPlaylistBundle = new Bundle();
		editPlaylistBundle.putString(SelectPlaylistActivity.TITLE, title);
		editPlaylistBundle.putSerializable(
				SelectPlaylistActivity.NEXT_ACTIVITY_CLASS_KEY,
				nextActivityClass);
		return editPlaylistBundle;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		// New
		addOpenActivityListener(R.id.menu_new_button, NewPlaylistActivity.class);
		// Edit
		addOpenActivityListener(
				R.id.menu_edit_button,
				SelectPlaylistActivity.class,
				getNextActivityClassBundle(EditPlaylistActivity.class,
						getString(R.string.edit_playlist)));
		// View
		addOpenActivityListener(
				R.id.menu_view_button,
				SelectPlaylistActivity.class,
				getNextActivityClassBundle(ViewPlaylistActivity.class,
						getString(R.string.view_playlist)));
		// Export
		addOpenActivityListener(
				R.id.menu_export_button,
				SelectPlaylistAndExportTypeActivity.class,
				getNextActivityClassBundle(ExportPlaylistActivity.class,
						getString(R.string.export_playlist)));
	}

}
