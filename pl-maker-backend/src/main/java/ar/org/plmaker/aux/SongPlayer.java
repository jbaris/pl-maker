package ar.org.plmaker.aux;

public interface SongPlayer {

	void play(String file);

	void stop();

	Float volumeDown();

	Float volumeUp();

}
