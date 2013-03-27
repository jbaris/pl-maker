package ar.org.plmaker.entities;

import java.io.Serializable;

/**
 * A song
 * 
 * @author Juan Ignacio Barisich
 */
public class Song implements Serializable {
	private static final long serialVersionUID = -9024595447689196525L;

	private Long id;
	private String name;

	private String file;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Song other = (Song) obj;
		return other.id.equals(id);
	}

	public String getFile() {
		return file;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return getFile().substring(getFile().lastIndexOf(".") + 1);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
