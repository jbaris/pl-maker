package ar.org.plmaker.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ar.org.plmaker.aux.PlayListExporter;
import ar.org.plmaker.aux.SongPlayer;
import ar.org.plmaker.bo.PlMakerBO;
import ar.org.plmaker.command.Command;
import ar.org.plmaker.dao.PlMakerDAO;
import ar.org.plmaker.entities.Song;

public class PlMakerBOImpl implements PlMakerBO, ApplicationContextAware {

	private static Logger logger = Logger.getLogger(PlMakerBOImpl.class);
	private PlMakerDAO plMakerDAO;
	private SongPlayer songPlayer;
	private PlayListExporter exporter;
	private final List<Command> history = new ArrayList<Command>();
	private ApplicationContext applicationContext;

	@Override
	public boolean createPlayList(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Playlist name can not be null");
		}
		final boolean createPlayList = getPlMakerDAO().createPlayList(name);
		if (createPlayList) {
			logger.info("Playlist created: " + name);
		} else {
			logger.info("Playlist alredy exists: " + name);
		}
		return createPlayList;
	}

	@Override
	public void execute(String command, Collection<String[]> params) {
		final Command cmd = (Command) getApplicationContext().getBean(command);
		cmd.setParams(params);
		cmd.execute();
		history.add(cmd);
	}

	@Override
	public char[] exportPlayList(String playListName, String exportType) {
		final List<String> songs = getPlMakerDAO().getSongs(playListName);
		return getExporter().exportPlayList(playListName, exportType, songs);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public PlayListExporter getExporter() {
		return exporter;
	}

	@Override
	public List<String> getExportTypes() {
		return getExporter().getExportTypes();
	}

	@Override
	public Song getNextUncategorized(String playListName) {
		if (playListName == null) {
			throw new IllegalArgumentException("Playlist name can not be null");
		}
		// esto es muy poco performante, mejorar con perisitencia de playlists
		final Collection<Song> allSongs = getPlMakerDAO().getAllSongs();
		final List<String> playList = getPlMakerDAO().getSongs(playListName);
		final List<String> ignoredList = getPlMakerDAO().getSkipSongs(
				playListName);
		for (final Song song : allSongs) {
			if (!(playList.contains(song.getFile()) || ignoredList
					.contains(song.getFile()))) {
				return song;
			}
		}
		return null;
	}

	@Override
	public List<String> getPlayLists() {
		return getPlMakerDAO().getPlayLists();
	}

	public PlMakerDAO getPlMakerDAO() {
		return plMakerDAO;
	}

	public SongPlayer getSongPlayer() {
		return songPlayer;
	}

	@Override
	public List<String> getSongs(String playList) {
		return getPlMakerDAO().getSongs(playList);
	}

	@Override
	public void play(Song song) {
		if (song == null) {
			throw new IllegalArgumentException("Song can not be null");
		}
		getSongPlayer().play(song.getFile());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setExporter(PlayListExporter exporter) {
		this.exporter = exporter;
	}

	public void setPlMakerDAO(PlMakerDAO carBo) {
		plMakerDAO = carBo;
	}

	public void setSongPlayer(SongPlayer songPlayer) {
		this.songPlayer = songPlayer;
	}

	@Override
	public void stop() {
		getSongPlayer().stop();
	}

	@Override
	public boolean undoLastCommand() {
		if (history.isEmpty()) {
			return false;
		} else {
			final Command cmd = history.get(history.size() - 1);
			cmd.undo();
			history.remove(history.size() - 1);
			return true;
		}
	}

	@Override
	public Float volumeDown() {
		return getSongPlayer().volumeDown();
	}

	@Override
	public Float volumeUp() {
		return getSongPlayer().volumeUp();
	}

}
