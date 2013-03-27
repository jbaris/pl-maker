package ar.org.plmaker.dao;

import java.util.Collection;
import java.util.List;

import ar.org.plmaker.aux.ProgressMonitor;
import ar.org.plmaker.entities.Song;

public interface PlMakerDAO {

	void addPlaylistSong(String playList, String song);

	void addSkipSong(String playList, String song);

	boolean createPlayList(String name);

	Collection<Song> getAllSongs();

	List<String> getPlayLists();

	List<String> getSkipSongs(String playListName);

	List<String> getSongs(String playList);

	void removePlaylistSong(String playList, String song);

	void removeSkipSong(String playList, String song);

	void setProgressMonitor(ProgressMonitor progressMonitor);

}
