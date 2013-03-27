package ar.org.plmaker.exporter.impl;

import java.util.List;

import ar.org.plmaker.exporter.PlaylistExporterStrategy;

public class M3UPlaylistExporterStrategy implements PlaylistExporterStrategy {

	@Override
	public char[] export(String playListName, List<String> songs) {
		final StringBuffer sb = new StringBuffer(songs.size() * 100);
		for (final String string : songs) {
			sb.append(string);
			sb.append("\n");
		}
		return sb.toString().toCharArray();
	}

}
