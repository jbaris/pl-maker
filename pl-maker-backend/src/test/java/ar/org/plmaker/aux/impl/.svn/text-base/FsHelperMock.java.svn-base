package ar.org.plmaker.aux.impl;

import java.util.ArrayList;
import java.util.List;

import ar.org.plmaker.aux.FsHelper;
import ar.org.plmaker.aux.ProgressMonitor;

public class FsHelperMock implements FsHelper {

	@Override
	public List<String> getSongs(String dir, boolean b) {
		return getSongs(dir, b, null);
	}

	@Override
	public List<String> getSongs(String dir, boolean recursive,
			ProgressMonitor progressMonitor) {
		final List<String> result = new ArrayList<String>();
		if ("/home/juani/music/aerosmith/".equals(dir)) {
			result.add("/home/juani/music/aerosmith/DreamOn.mp3");
			result.add("/home/juani/music/aerosmith/Mamakin.mp3");
		}
		return result;
	}

}
