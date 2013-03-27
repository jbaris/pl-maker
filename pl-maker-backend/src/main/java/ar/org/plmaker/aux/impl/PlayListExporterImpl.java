package ar.org.plmaker.aux.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.org.plmaker.aux.PlayListExporter;
import ar.org.plmaker.exporter.PlaylistExporterStrategy;

public class PlayListExporterImpl implements PlayListExporter {

	private Map<String, PlaylistExporterStrategy> exporters;

	@Override
	public char[] exportPlayList(String playListName, String exportType,
			List<String> songs) {
		final PlaylistExporterStrategy exporter = getExporters()
				.get(exportType);
		return exporter.export(playListName, songs);
	}

	public Map<String, PlaylistExporterStrategy> getExporters() {
		return exporters;
	}

	@Override
	public List<String> getExportTypes() {
		return new ArrayList<String>(getExporters().keySet());
	}

	public void setExporters(Map<String, PlaylistExporterStrategy> exporters) {
		this.exporters = exporters;
	}

}
