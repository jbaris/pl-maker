package ar.org.plmaker.activities;

import java.util.Collections;
import java.util.List;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.holder.ContextHolder;
import ar.org.plmaker.utils.Utils;

import com.google.inject.Inject;

public class ViewPlaylistActivity extends BaseActivity {

	@Inject
	private PlMakerService plMakerService;

	@InjectView(R.id.view_playlist_list)
	private ListView list;

	private void fillList() {
		final List<String> songs = getSongs(list.getRootView());
		final String[] objects = new String[songs.size()];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = Utils.getShortSong(songs.get(i));

		}
		list.setAdapter(new ArrayAdapter<String>(list.getContext(),
				R.layout.view_playlist_row, objects));
	}

	private List<String> getSongs(View view) {
		try {
			return plMakerService.getSongs(ContextHolder.getInstance()
					.getCurrentPlaylist());
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
		setContentView(R.layout.view_playlist);
		setTitle(getString(R.string.view_playlist) + " | "
				+ ContextHolder.getInstance().getCurrentPlaylist());
		fillList();
	}

}
