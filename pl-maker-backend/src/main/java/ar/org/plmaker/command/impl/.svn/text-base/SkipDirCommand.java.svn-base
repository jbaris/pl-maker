package ar.org.plmaker.command.impl;

import java.util.List;

import org.apache.log4j.Logger;

public class SkipDirCommand extends AbstractDirCommand {

	private static Logger logger = Logger.getLogger(SkipDirCommand.class);

	@Override
	public void execute() {
		final String playList = getParams().get("playList");
		if (playList == null) {
			throw new IllegalArgumentException("PlayList can not be null");
		}
		final String dir = getParams().get("dir");
		if (dir == null) {
			throw new IllegalArgumentException("Dir can not be null");
		}
		final List<String> songs = getFsHelper().getSongs(dir, false);
		for (final String song : songs) {
			getPlMakerDAO().addSkipSong(playList, song);
		}
		logger.info(String.format("Dir (%s) skipped from playlist (%s) ", dir,
				playList));
	}

	@Override
	public void undo() {
		final String dir = getParams().get("dir");
		final String playList = getParams().get("playList");
		final List<String> songs = getFsHelper().getSongs(dir, false);
		for (final String song : songs) {
			getPlMakerDAO().removeSkipSong(playList, song);
		}
		logger.info(String.format("Dir (%s) skipped from playlist (%s) ", dir,
				playList));
	}

}
