package ar.org.plmaker.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.List;

import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.bo.PlMakerBO;
import ar.org.plmaker.entities.Song;

public class PlMakerServiceImpl implements PlMakerService {

	private PlMakerBO plMakerBO;

	@Override
	public boolean createPlayList(String name) {
		return getPlMakerBO().createPlayList(name);
	}

	@Override
	public void execute(String command, Collection<String[]> params) {
		getPlMakerBO().execute(command, params);
	}

	@Override
	public char[] exportPlayList(String playListName, String exportType) {
		return getPlMakerBO().exportPlayList(playListName, exportType);
	}

	@Override
	public List<String> getExportTypes() {
		return getPlMakerBO().getExportTypes();
	}

	@Override
	public Song getNextUncategorized(String playListName) {
		return getPlMakerBO().getNextUncategorized(playListName);
	}

	@Override
	public String getPathSeparator() {
		return File.separator;
	}

	@Override
	public List<String> getPlayLists() {
		return getPlMakerBO().getPlayLists();
	}

	public PlMakerBO getPlMakerBO() {
		return plMakerBO;
	}

	@Override
	public List<String> getSongs(String playList) {
		return getPlMakerBO().getSongs(playList);
	}

	@Override
	public void play(Song song) {
		getPlMakerBO().play(song);
	}

	public void setPlMakerBO(PlMakerBO carBo) {
		plMakerBO = carBo;
	}

	@Override
	public void stop() {
		getPlMakerBO().stop();
	}

	@Override
	public boolean undoLastCommand() {
		return getPlMakerBO().undoLastCommand();
	}

	@Override
	public Float volumeDown() {
		return getPlMakerBO().volumeDown();
	}

	@Override
	public Float volumeUp() {
		return getPlMakerBO().volumeUp();
	}

}
