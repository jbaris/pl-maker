package ar.org.plmaker.command.impl;

import org.apache.log4j.Logger;

public class SkipSongCommand extends AbstractCommand {

	private static Logger logger = Logger.getLogger(SkipSongCommand.class);

	@Override
	public void execute() {
		final String playList = getParams().get("playList");
		if (playList == null) {
			throw new IllegalArgumentException("PlayList can not be null");
		}
		final String song = getParams().get("song");
		if (song == null) {
			throw new IllegalArgumentException("Song can not be null");
		}
		getPlMakerDAO().addSkipSong(playList, song);
		logger.info(String.format("Song (%s) skipped to playlist (%s) ", song,
				playList));
	}

	@Override
	public void undo() {
		final String song = getParams().get("song");
		final String playList = getParams().get("playList");
		getPlMakerDAO().removeSkipSong(playList, song);
		logger.info(String.format("Song (%s) skipped from playlist (%s) ",
				song, playList));
	}

}
