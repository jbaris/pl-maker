package ar.org.plmaker.activities;

import java.util.Collections;
import java.util.List;

import roboguice.adapter.IterableAdapter;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.application.PlMakerApplication;
import ar.org.plmaker.holder.ContextHolder;

import com.google.inject.Inject;

public class SelectPlaylistActivity extends BaseActivity {

	private static final String PLAYLIST_INDEX_KEY = "playlistIndex";
	protected static final String NEXT_ACTIVITY_CLASS_KEY = "nextActivityClass";
	static final String TITLE = "title";

	@InjectView(R.id.edit_playlist_button)
	private Button button;

	@InjectView(R.id.edit_playlist_spinner)
	private Spinner playlist_spinner;

	@Inject
	protected PlMakerService plMakerService;

	protected boolean checkSelection(View view, SharedPreferences settings) {
		final long selectedItemId = playlist_spinner.getSelectedItemId();
		final boolean ok = selectedItemId != AdapterView.INVALID_ROW_ID;
		if (ok) {
			final Editor editor = settings.edit();
			editor.putInt(PLAYLIST_INDEX_KEY, (int) selectedItemId);
			editor.commit();
		} else {
			Toast.makeText(view.getContext(), R.string.select_playlist,
					Toast.LENGTH_LONG).show();
		}
		return ok;
	}

	protected void fillContext() {
		ContextHolder.getInstance().setCurrentPlaylist(
				(String) playlist_spinner.getSelectedItem());
	}

	private void fillSpinner(Spinner spinner, SharedPreferences settings) {
		final List<String> playLists = getPlayLists(spinner.getRootView());
		final IterableAdapter<String> adapter = new IterableAdapter<String>(
				spinner.getContext(), android.R.layout.simple_spinner_item,
				playLists);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		final int position = settings.getInt(PLAYLIST_INDEX_KEY, 0);
		if (position < playLists.size()) {
			playlist_spinner.setSelection(position);
		}
	}

	private List<String> getPlayLists(View view) {
		try {
			return plMakerService.getPlayLists();
		} catch (final Exception e) {
			Toast.makeText(view.getContext(), R.string.error_accessing_server,
					Toast.LENGTH_LONG).show();
			final Intent myIntent = new Intent(view.getContext(),
					MenuActivity.class);
			startActivityForResult(myIntent, 0);
			return Collections.<String> emptyList();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent();
		setTitle(getIntent().getExtras().getString(TITLE));
		final SharedPreferences settings = getSharedPreferences(
				PlMakerApplication.PREFS_NAME, 0);
		fillSpinner(playlist_spinner, settings);
		@SuppressWarnings("unchecked")
		final Class<? extends Activity> nextActivityClass = (Class<? extends Activity>) getIntent()
				.getExtras().getSerializable(NEXT_ACTIVITY_CLASS_KEY);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (checkSelection(button.getRootView(), settings)) {
					fillContext();
					final Intent myIntent = new Intent(view.getContext(),
							nextActivityClass);
					startActivityForResult(myIntent, 0);
				}
			}

		});
	}

	protected void setContent() {
		setContentView(R.layout.select_playlist);
	}

}
