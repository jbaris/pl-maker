package ar.org.plmaker.activities;

import java.util.Collections;

import roboguice.adapter.IterableAdapter;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import ar.org.plmaker.holder.ContextHolder;

public class SelectPlaylistAndExportTypeActivity extends SelectPlaylistActivity {

	@InjectView(R.id.edit_export_type_spinner)
	private Spinner export_type_spinner;

	@Override
	protected boolean checkSelection(View view, SharedPreferences settings) {
		boolean ok = super.checkSelection(view, settings);
		if (ok) {
			ok = export_type_spinner.getSelectedItemId() != AdapterView.INVALID_ROW_ID;
			if (!ok) {
				Toast.makeText(view.getContext(), R.string.select_export_type,
						Toast.LENGTH_LONG).show();
			}
		}
		return ok;
	}

	@Override
	protected void fillContext() {
		super.fillContext();
		ContextHolder.getInstance().setCurrentExportType(
				(String) export_type_spinner.getSelectedItem());
	}

	private void fillSpinner(Spinner spinner) {
		final IterableAdapter<String> adapter = new IterableAdapter<String>(
				spinner.getContext(), android.R.layout.simple_spinner_item,
				getExportTypes(spinner.getRootView()));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private Iterable<String> getExportTypes(View view) {
		try {
			return plMakerService.getExportTypes();
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
		fillSpinner(export_type_spinner);
	}

	@Override
	protected void setContent() {
		setContentView(R.layout.select_playlist_export_type);
	}

}
