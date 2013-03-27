package ar.org.plmaker.command.impl;

import java.util.List;

import org.apache.log4j.Logger;

public class AddDirCommand extends AbstractDirCommand {

	private static Logger logger = Logger.getLogger(AddDirCommand.class);

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
			getPlMakerDAO().addPlaylistSong(playList, song);
		}
		logger.info(String.format("Dir (%s) added to playlist (%s) ", dir,
				playList));
	}

	@Override
	public void undo() {
		final String dir = getParams().get("dir");
		final String playList = getParams().get("playList");
		final List<String> songs = getFsHelper().getSongs(dir, false);
		for (final String song : songs) {
			getPlMakerDAO().removePlaylistSong(playList, song);
		}
		logger.info(String.format("Dir (%s) removed from playlist (%s) ", dir,
				playList));
	}

}
