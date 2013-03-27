package ar.org.plmaker.entities;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
	private Long id;
	private String name;

	private List<String> play;
	private List<String> skip;

	public PlayList() {
		play = new ArrayList<String>();
		skip = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getPlay() {
		return play;
	}

	public List<String> getSkip() {
		return skip;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlay(List<String> songs) {
		play = songs;
	}

	public void setSkip(List<String> skip) {
		this.skip = skip;
	}

	public String tail() {
		final StringBuffer result = new StringBuffer();
		try {
			result.append(play.get(play.size() - 1));
			result.append("\n");
			result.append(play.get(play.size() - 2));
			result.append("\n");
			result.append(play.get(play.size() - 3));
		} catch (final Exception e) {
			// do nothingF
		}
		return result.toString();
	}

}
