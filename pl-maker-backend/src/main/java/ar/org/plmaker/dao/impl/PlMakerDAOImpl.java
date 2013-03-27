package ar.org.plmaker.dao.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.org.plmaker.aux.FsHelper;
import ar.org.plmaker.aux.ProgressMonitor;
import ar.org.plmaker.dao.PlMakerDAO;
import ar.org.plmaker.entities.PlayList;
import ar.org.plmaker.entities.Song;

public class PlMakerDAOImpl extends HibernateDaoSupport implements PlMakerDAO,
		InitializingBean {

	private static Logger LOGGER = Logger.getLogger(PlMakerDAOImpl.class);
	private FsHelper fsHelper;
	private String musicDir;
	private CacheManager cacheManager;
	private ProgressMonitor progressMonitor;

	public PlMakerDAOImpl() {
		musicDir = System.getProperty("musicDir");
		progressMonitor = new ProgressMonitor() {

			@Override
			public int getTotal() {
				return 0;
			}

			@Override
			public void setCurrent(String status, int current) {
			}

			@Override
			public void start(String status) {
			}

		};
	}

	@Override
	public void addPlaylistSong(String playListName, String songFile) {
		final PlayList playList = getPlayList(playListName);
		playList.getPlay().add(songFile);
		getHibernateTemplate().saveOrUpdate(playList);
		LOGGER.debug("Song added to playlist. The lastest playlist's songs are:\n"
				+ playList.tail());
	}

	@Override
	public void addSkipSong(String playListName, String songFile) {
		final PlayList playList = getPlayList(playListName);
		playList.getSkip().add(songFile);
		getHibernateTemplate().saveOrUpdate(playList);
	}

	private Song buildSong(String song) {
		final Song result = new Song();
		result.setFile(song);
		result.setName(song.substring(song.lastIndexOf(File.separator) + 1));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean createPlayList(String name) {
		final List<PlayList> playList = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(PlayList.class).add(
						Restrictions.eq("name", name)));
		final boolean alredyExists = !playList.isEmpty();
		if (!alredyExists) {
			final PlayList newPlayList = new PlayList();
			newPlayList.setName(name);
			getHibernateTemplate().saveOrUpdate(newPlayList);
		}
		return !alredyExists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Song> getAllSongs() {
		return getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Song.class));
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public FsHelper getFsHelper() {
		return fsHelper;
	}

	private String getMusicDir() {
		return musicDir;
	}

	@SuppressWarnings("unchecked")
	private PlayList getPlayList(String playListName) {
		final List<PlayList> playList = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(PlayList.class).add(
						Restrictions.eq("name", playListName)));
		if (playList.isEmpty()) {
			throw new IllegalArgumentException("Playlist not found");
		}
		return playList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPlayLists() {
		final List<PlayList> findByCriteria = getHibernateTemplate()
				.findByCriteria(DetachedCriteria.forClass(PlayList.class));
		final List<String> result = new ArrayList<String>(findByCriteria.size());
		for (final PlayList playList : findByCriteria) {
			result.add(playList.getName());
		}
		return result;
	}

	public ProgressMonitor getProgressMonitor() {
		return progressMonitor;
	}

	@Override
	public List<String> getSkipSongs(String playListName) {
		return new ArrayList<String>(getPlayList(playListName).getSkip());
	}

	@Override
	public List<String> getSongs(String playListName) {
		return new ArrayList<String>(getPlayList(playListName).getPlay());
	}

	@Override
	public void initDao() throws Exception {
		getHibernateTemplate().setCacheQueries(true);
		getHibernateTemplate().setQueryCacheRegion("plmaker");
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.createQuery("delete from Song").executeUpdate();
				return null;
			}

		});
		if (!getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Song.class)).isEmpty()) {
			throw new IllegalStateException();
		}
		logger.info("Song list initializing started from: " + getMusicDir());
		logger.info("Looking for songs...");
		getProgressMonitor().setCurrent("Looking for songs...", 10);
		final List<String> songs = getFsHelper().getSongs(getMusicDir(), true,
				getProgressMonitor());
		getProgressMonitor().setCurrent("Adding files to repository...", 70);
		logger.info("Adding files to repository...");
		final int total = songs.size();
		int current = 0;
		for (final String songFile : songs) {
			getProgressMonitor().setCurrent("Adding song " + songFile,
					70 + current * 25 / total);
			getHibernateTemplate().saveOrUpdate(buildSong(songFile));
			current++;
		}
		logger.info("Song list initializing end");
	}

	@Override
	public void removePlaylistSong(String playListName, String songFile) {
		final PlayList playList = getPlayList(playListName);
		playList.getPlay().remove(songFile);
		getHibernateTemplate().saveOrUpdate(playList);
	}

	@Override
	public void removeSkipSong(String playListName, String songFile) {
		final PlayList playList = getPlayList(playListName);
		playList.getSkip().remove(songFile);
		getHibernateTemplate().saveOrUpdate(playList);
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setFsHelper(FsHelper fsHelper) {
		this.fsHelper = fsHelper;
	}

	@Override
	public void setProgressMonitor(ProgressMonitor progressMonitor) {
		this.progressMonitor = progressMonitor;
	}
}
