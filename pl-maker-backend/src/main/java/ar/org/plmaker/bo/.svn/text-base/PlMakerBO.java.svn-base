package ar.org.plmaker.bo;

import java.util.Collection;
import java.util.List;

import ar.org.plmaker.entities.Song;

public interface PlMakerBO {

	boolean createPlayList(String name);

	void execute(String command, Collection<String[]> params);

	char[] exportPlayList(String playListName, String exportType);

	List<String> getExportTypes();

	Song getNextUncategorized(String playListName);

	List<String> getPlayLists();

	List<String> getSongs(String playList);

	void play(Song song);

	void stop();

	boolean undoLastCommand();

	Float volumeDown();

	Float volumeUp();

}
