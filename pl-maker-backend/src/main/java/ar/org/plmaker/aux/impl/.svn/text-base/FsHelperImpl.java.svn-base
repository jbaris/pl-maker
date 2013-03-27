package ar.org.plmaker.aux.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import ar.org.plmaker.aux.FsHelper;
import ar.org.plmaker.aux.ProgressMonitor;

/**
 * @author juan.barisich@gmail.com
 * @author javapractices.com
 * @author Alex Wong
 */
public class FsHelperImpl implements FsHelper {

	private static Logger logger = Logger.getLogger(FsHelperImpl.class);
	private static int TOTAL_PROGRESS = 60;
	private static final String[] SONG_EXTS = new String[] { "mp3", "wav",
			"wma" };
	private static final Transformer FILE_TRANSFORMER = new Transformer() {
		@Override
		public Object transform(Object input) {
			return ((File) input).getPath();
		}
	};
	private static final Predicate DIR_PREDICATE = new Predicate() {

		@Override
		public boolean evaluate(Object object) {
			return ((File) object).isDirectory();
		}
	};

	@SuppressWarnings("unchecked")
	private Collection<File> getDirs(File aStartingDir) {
		final Collection<File> asList = Arrays.asList(aStartingDir.listFiles());
		return CollectionUtils.select(asList, DIR_PREDICATE);
	}

	@SuppressWarnings("unchecked")
	private Collection<String> getSongs(final Collection<File> currentSongs) {
		return CollectionUtils.collect(currentSongs, FILE_TRANSFORMER);
	}

	@SuppressWarnings("unchecked")
	private Collection<String> getSongs(File currentDir) {
		final Collection<File> listFiles = FileUtils.listFiles(currentDir,
				SONG_EXTS, true);
		return getSongs(listFiles);
	}

	@Override
	public List<String> getSongs(String dir, boolean recursive) {
		return getSongs(dir, recursive, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSongs(String dir, boolean recursive,
			ProgressMonitor progressMonitor) {
		logger.info("Native file encoding: "
				+ System.getProperty("file.encoding"));
		try {
			final List<String> result = new ArrayList<String>();
			final File aStartingDir = new File(dir);
			final List<File> currentSongs = (List<File>) FileUtils.listFiles(
					aStartingDir, SONG_EXTS, false);
			result.addAll(getSongs(currentSongs));
			if (recursive) {
				final Collection<File> currentDirs = getDirs(aStartingDir);
				int current = 0;
				final int total = currentDirs.size();
				for (final File currentDir : currentDirs) {
					result.addAll(getSongs(currentDir));
					updateMonitor(progressMonitor, currentDir, current, total);
					current++;
				}
			}
			return result;
		} catch (final Exception e) {
			throw new IllegalStateException("Directory read error", e);
		}
	}

	private void updateMonitor(ProgressMonitor progressMonitor, File file,
			int current, int total) {
		final int value = 10 + current * TOTAL_PROGRESS / total;
		progressMonitor.setCurrent("Adding songs from " + file.getName()
				+ " ...", value);
	}

}
