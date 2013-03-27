package ar.org.plmaker.command.impl;

import org.apache.log4j.Logger;

public class AddSongCommand extends AbstractCommand {

	private static Logger logger = Logger.getLogger(AddSongCommand.class);

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
		getPlMakerDAO().addPlaylistSong(playList, song);
		logger.info(String.format("Song (%s) added to playlist (%s) ", song,
				playList));
	}

	@Override
	public void undo() {
		final String song = getParams().get("song");
		final String playList = getParams().get("playList");
		getPlMakerDAO().removePlaylistSong(playList, song);
		logger.info(String.format("Song (%s) removed from playlist (%s) ",
				song, playList));
	}

}
