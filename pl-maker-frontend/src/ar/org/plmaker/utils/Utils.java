package ar.org.plmaker.utils;

import ar.org.plmaker.holder.ContextHolder;

public class Utils {

	public static String getDir(String file) {
		if (file == null) {
			return null;
		} else {
			return file.substring(0, file.lastIndexOf(getPathSeparator()));
		}
	}

	public static Object getFile(String fileName) {
		return fileName.substring(fileName.lastIndexOf(getPathSeparator()) + 1);
	}

	private static String getPathSeparator() {
		return ContextHolder.getInstance().getPathSeparator();
	}

	public static CharSequence getShortDir(String file) {
		final String[] split = file.split(getQuotedPathSeparator());
		if (split.length > 3) {
			return split[split.length - 3] + getPathSeparator()
					+ split[split.length - 2];
		} else {
			return split[split.length - 2];
		}
	}

	private static String getQuotedPathSeparator() {
		return ContextHolder.getInstance().getQuotedPathSeparator();
	}

	public static String getShortSong(String song) {
		final String songName = song.substring(song
				.lastIndexOf(getPathSeparator()));
		final String dir = getShortDir(song).toString();
		return dir + songName;
	}

}
