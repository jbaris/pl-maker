package ar.org.plmaker.activities;

import java.io.File;
import java.io.FileWriter;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.application.PlMakerApplication;
import ar.org.plmaker.holder.ContextHolder;
import ar.org.plmaker.utils.Utils;

import com.google.inject.Inject;

public class ExportPlaylistActivity extends BaseActivity {

	private static final int SAVE_FILE = 0;

	private static final String SAVE_DIR_KEY = "saveDir";

	@InjectView(R.id.export_savefile_buttton)
	private Button saveFileButton;

	@InjectView(R.id.export_sendmail_buttton)
	private Button sendMailButton;

	@Inject
	private PlMakerService plMakerService;

	private char[] getPlayListFile() {
		final ContextHolder context = ContextHolder.getInstance();
		return plMakerService.exportPlayList(context.getCurrentPlaylist(),
				context.getCurrentExportType());
	}

	private String getPlaylistFilename(String dirPath) {
		final ContextHolder context = ContextHolder.getInstance();
		return dirPath + "/" + context.getCurrentPlaylist() + "."
				+ context.getCurrentExportType();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SAVE_FILE && resultCode == Activity.RESULT_OK) {
			final String dirPath = data
					.getStringExtra(SelectDirectoryActivity.RESULT_PATH);
			savePlayListFile(dirPath);
			final SharedPreferences settings = getSharedPreferences(
					PlMakerApplication.PREFS_NAME, 0);
			final Editor editor = settings.edit();
			editor.putString(SAVE_DIR_KEY, dirPath);
			editor.commit();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_playlist);
		saveFileButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveFile();
			}
		});
		sendMailButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMail();
			}
		});
	}

	private void overwritePlayListFile(File file, boolean showOk) {
		try {
			FileWriter osw;
			osw = new FileWriter(file);
			osw.write(getPlayListFile());
			osw.flush();
			osw.close();
			if (showOk) {
				showOk();
			}
		} catch (final Exception e) {
			Toast.makeText(getApplicationContext(),
					R.string.error_writing_file, Toast.LENGTH_LONG).show();
		}
	}

	private void overwritePlayListFile(String fileName, boolean showOk) {
		overwritePlayListFile(new File(fileName), showOk);
	}

	private void saveFile() {
		final Intent intent = new Intent(getBaseContext(),
				SelectDirectoryActivity.class);
		final SharedPreferences settings = getSharedPreferences(
				PlMakerApplication.PREFS_NAME, 0);
		final String dir = settings.getString(SAVE_DIR_KEY, "/");
		intent.putExtra(SelectDirectoryActivity.START_PATH, dir);
		intent.putExtra(SelectDirectoryActivity.TITLE_KEY,
				getString(R.string.save));
		startActivityForResult(intent, SAVE_FILE);
	}

	private String savePlayListFile(String dirPath) {
		final String fileName = getPlaylistFilename(dirPath);
		final File file = new File(fileName);
		if (file.exists()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					getString(R.string.file_already_exists_override,
							Utils.getFile(fileName)))
					.setCancelable(false)
					.setPositiveButton(getString(R.string.yes),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									ExportPlaylistActivity.this
											.overwritePlayListFile(file, true);
								}
							})
					.setNegativeButton(getString(R.string.no),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			builder.show();
		} else {
			overwritePlayListFile(file, true);
		}
		return fileName;
	}

	private void sendMail() {
		try {
			final String fileName = getPlaylistFilename(getExternalCacheDir()
					.getPath());
			overwritePlayListFile(fileName, false);
			final String playListName = ContextHolder.getInstance()
					.getCurrentPlaylist();
			final Intent i = new Intent(Intent.ACTION_SEND);
			i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.playlist)
					+ ": " + playListName);
			i.putExtra(Intent.EXTRA_TEXT, getString(R.string.file_attached));
			final Uri uri = Uri.parse("file://" + fileName);
			i.putExtra(Intent.EXTRA_STREAM, uri);
			i.setType("text/plain");
			startActivity(Intent.createChooser(i,
					getString(R.string.send_email)));
		} catch (final Exception e) {
			Toast.makeText(getApplicationContext(),
					R.string.error_writing_file, Toast.LENGTH_LONG).show();
		}
	}

	private void showOk() {
		Toast.makeText(getApplicationContext(), R.string.playlist_exported,
				Toast.LENGTH_LONG).show();
	}

}
