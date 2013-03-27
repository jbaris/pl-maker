package ar.org.plmaker.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.entities.Song;
import ar.org.plmaker.holder.ContextHolder;
import ar.org.plmaker.utils.Utils;

import com.google.inject.Inject;

public class EditPlaylistActivity extends BaseActivity {

	private static DecimalFormat df = new DecimalFormat("#.##");

	@Inject
	private PlMakerService plMakerService;

	@InjectView(R.id.edit_playlist_song_text)
	private EditText songText;

	@InjectView(R.id.edit_playlist_dir_text)
	private EditText dirText;

	@InjectView(R.id.edit_playlist_stop_play_button)
	private Button stopPlayButton;

	@InjectView(R.id.edit_playlist_volume_up_button)
	private Button volumeUpButton;

	@InjectView(R.id.edit_playlist_volume_down_button)
	private Button volumeDownButton;

	@InjectView(R.id.edit_playlist_add_song_button)
	private Button addSongButton;

	@InjectView(R.id.edit_playlist_add_dir_button)
	private Button addDirButton;

	@InjectView(R.id.edit_playlist_skip_song_button)
	private Button skipSongButton;

	@InjectView(R.id.edit_playlist_skip_dir_button)
	private Button skipDirButton;

	@InjectView(R.id.edit_playlist_undo_button)
	private Button undoButton;

	private String currentSongFile;

	private void addCurrentDir() {
		excetuteCommand("dir", getCurrentSongDir(), PlMakerService.ADD_DIR,
				R.string.dir_added);
	}

	private void addCurrentSong() {
		excetuteCommand("song", getCurrentSongFile(), PlMakerService.ADD_SONG,
				R.string.song_added);
	}

	private void excetuteCommand(String param, String value, String command,
			int msgStringId) {
		try {
			if (value == null) {
				Toast.makeText(getApplicationContext(),
						R.string.no_song_playing, Toast.LENGTH_SHORT).show();
			} else {
				final Collection<String[]> params = new ArrayList<String[]>();
				params.add(new String[] { param, value });
				params.add(new String[] { "playList",
						ContextHolder.getInstance().getCurrentPlaylist() });
				plMakerService.execute(command, params);
				Toast.makeText(getApplicationContext(), msgStringId,
						Toast.LENGTH_SHORT).show();
				showAndPlayNextSong();
			}
		} catch (final Exception e) {
			handleServerError();
		}
	}

	private String getCurrentSongDir() {
		return Utils.getDir(currentSongFile);
	}

	private String getCurrentSongFile() {
		return currentSongFile;
	}

	private String getPercent(Float volume) {
		return df.format(100 * volume) + " %";
	}

	private void handleServerError() {
		Toast.makeText(getApplicationContext(),
				R.string.error_accessing_server, Toast.LENGTH_LONG).show();
		final Intent myIntent = new Intent(getApplicationContext(),
				MenuActivity.class);
		startActivityForResult(myIntent, 0);
	}

	private void hideAndStop() {
		stopPlayButton.setText(getString(R.string.resume));
		final Drawable dw = stopPlayButton.getContext().getResources()
				.getDrawable(R.drawable.play);
		stopPlayButton.setCompoundDrawablesWithIntrinsicBounds(dw, null, null,
				null);
		try {
			songText.setText(getString(R.string.stopped));
			dirText.setText(getString(R.string.stopped));
			plMakerService.stop();
			currentSongFile = null;
		} catch (final Exception e) {
			handleServerError();
		}
	}

	@Override
	public void onBackPressed() {
		plMakerService.stop();
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getString(R.string.edit_playlist) + " | "
				+ ContextHolder.getInstance().getCurrentPlaylist());
		setContentView(R.layout.edit_playlist);
		showAndPlayNextSong();
		stopPlayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				switchStopResume();
			}

		});
		volumeUpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				volumeUp();
			}

		});
		volumeDownButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				volumeDown();
			}

		});
		addSongButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				addCurrentSong();
			}

		});
		addDirButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				addCurrentDir();
			}

		});
		skipSongButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				skipCurrentSong();
			}

		});
		skipDirButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				skipCurrentDir();
			}

		});
		undoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				undoLastAction();
			}
		});
	}

	private void showAndPlayNextSong() {
		stopPlayButton.setText(getString(R.string.stop));
		final Drawable dw = stopPlayButton.getContext().getResources()
				.getDrawable(R.drawable.stop);
		stopPlayButton.setCompoundDrawablesWithIntrinsicBounds(dw, null, null,
				null);
		try {
			final Song nextUncategorized = plMakerService
					.getNextUncategorized(ContextHolder.getInstance()
							.getCurrentPlaylist());
			if (nextUncategorized == null) {
				hideAndStop();
				Toast.makeText(getApplicationContext(),
						R.string.no_more_songs_to_process, Toast.LENGTH_LONG)
						.show();
			} else {
				songText.setText(nextUncategorized.getName());
				dirText.setText(Utils.getShortDir(nextUncategorized.getFile()));
				plMakerService.play(nextUncategorized);
				currentSongFile = nextUncategorized.getFile();
			}
		} catch (final Exception e) {
			handleServerError();
		}
	}

	private void skipCurrentDir() {
		excetuteCommand("dir", getCurrentSongDir(), PlMakerService.SKIP_DIR,
				R.string.dir_skipped);
	}

	private void skipCurrentSong() {
		excetuteCommand("song", getCurrentSongFile(), PlMakerService.SKIP_SONG,
				R.string.song_skipped);
	}

	private void switchStopResume() {
		if (stopPlayButton.getText().equals(getString(R.string.stop))) {
			// Stop
			hideAndStop();
		} else {
			// Resume
			showAndPlayNextSong();
		}
	}

	private void undoLastAction() {
		if (plMakerService.undoLastCommand()) {
			Toast.makeText(getApplicationContext(),
					R.string.last_action_was_reverted, Toast.LENGTH_SHORT)
					.show();
			showAndPlayNextSong();
		} else {
			Toast.makeText(getApplicationContext(), R.string.no_action_to_undo,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void volumeDown() {
		try {
			final Float volume = plMakerService.volumeDown();
			if (volume != null) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.volume) + " " + getPercent(volume),
						Toast.LENGTH_SHORT).show();
			}
		} catch (final Exception e) {
			handleServerError();
		}
	}

	private void volumeUp() {
		try {
			final Float volume = plMakerService.volumeUp();
			if (volume != null) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.volume) + " " + getPercent(volume),
						Toast.LENGTH_SHORT).show();
			}
		} catch (final Exception e) {
			handleServerError();
		}
	}

}
