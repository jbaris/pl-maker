package ar.org.plmaker.holder;

import java.util.regex.Pattern;

public class ContextHolder {

	private static ContextHolder instance = new ContextHolder();

	public static ContextHolder getInstance() {
		return instance;
	}

	private String serverAddress = "";
	private String currentPlaylist = null;
	private String currentExportType = null;
	private int serverPort;
	private String pathSeparator;
	private String quotedPathSeparator;

	private ContextHolder() {

	}

	public String getCurrentExportType() {
		return currentExportType;
	}

	public String getCurrentPlaylist() {
		return currentPlaylist;
	}

	public String getPathSeparator() {
		return pathSeparator;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setCurrentExportType(String currentExportType) {
		this.currentExportType = currentExportType;
	}

	public void setCurrentPlaylist(String currentPlaylist) {
		this.currentPlaylist = currentPlaylist;
	}

	public void setPathSeparator(String pathSeparator2) {
		this.pathSeparator = pathSeparator2;
		this.quotedPathSeparator = Pattern.quote(pathSeparator2);
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getQuotedPathSeparator() {
		return quotedPathSeparator;
	}

}
