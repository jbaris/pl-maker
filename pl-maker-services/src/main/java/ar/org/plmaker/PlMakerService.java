package ar.org.plmaker;

import java.util.Collection;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import ar.org.plmaker.entities.Song;

/**
 * A play list web service
 * 
 * @author Juan Ignacio Barisich
 */
@WebService(name = "PlMakerService")
public interface PlMakerService {

	String SKIP_SONG = "skipSong.command";
	String SKIP_DIR = "skipDir.command";
	String ADD_SONG = "addSong.command";
	String ADD_DIR = "addDir.command";

	boolean createPlayList(@WebParam(name = "playListName") String playListName);

	void execute(@WebParam(name = "command") String command,
			@WebParam(name = "params") Collection<String[]> params);

	char[] exportPlayList(@WebParam(name = "playListName") String playListName,
			@WebParam(name = "exportType") String exportType);

	List<String> getExportTypes();

	Song getNextUncategorized(
			@WebParam(name = "playListName") String playListName);

	String getPathSeparator();

	List<String> getPlayLists();

	List<String> getSongs(@WebParam(name = "playListName") String playListName);

	void play(@WebParam(name = "song") Song song);

	void stop();

	boolean undoLastCommand();

	Float volumeDown();

	Float volumeUp();

}