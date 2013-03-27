package ar.org.plmaker.activities;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ar.org.plmaker.PlMakerService;

import com.google.inject.Inject;

public class NewPlaylistActivity extends BaseActivity {

	@InjectView(R.id.new_playlist_button)
	private Button button;

	@InjectView(R.id.new_playlist_text)
	private EditText text;

	@Inject
	private PlMakerService plMakerService;

	private void createPlaylist(String playlistName, View view) {
		try {
			if (plMakerService.createPlayList(playlistName)) {
				Toast.makeText(view.getContext(), R.string.playlist_created,
						Toast.LENGTH_LONG).show();
				final Intent myIntent = new Intent(view.getContext(),
						MenuActivity.class);
				startActivityForResult(myIntent, 0);
			} else {
				Toast.makeText(view.getContext(),
						R.string.playlist_alredy_exists, Toast.LENGTH_LONG)
						.show();
			}
		} catch (final Exception e) {
			Toast.makeText(view.getContext(), R.string.error_accessing_server,
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_playlist);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (text.getText().length() == 0) {
					Toast.makeText(view.getContext(),
							R.string.enter_playlist_name, Toast.LENGTH_LONG)
							.show();
				} else {
					createPlaylist(text.getText().toString(), view);
				}
			}

		});

	}

}
