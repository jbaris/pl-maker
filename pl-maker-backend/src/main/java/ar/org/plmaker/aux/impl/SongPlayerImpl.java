package ar.org.plmaker.aux.impl;

import java.io.File;
import java.net.URL;

import javax.media.Codec;
import javax.media.Controller;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

import org.apache.log4j.Logger;

import ar.org.plmaker.aux.SongPlayer;

public class SongPlayerImpl implements SongPlayer {

	private static class ForwardRunable implements Runnable {

		private final Player player;

		public ForwardRunable(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(10 * 1000);
				if (player.getState() == Controller.Started) {
					player.setRate(90F); // jump to 90 seconds
				}
			} catch (final Exception e) {
				// do nothing
			}
		}

	}

	private static Logger logger = Logger.getLogger(SongPlayerImpl.class);
	private static final float SLIDER_RANGE = 0.2F;
	private Player player;

	private boolean isPlaying = false;

	public SongPlayerImpl() {
		try {
			// the dark side of the moon
			ClassPathLoader.addURL(new File("lib/jffmpeg-1.1.0.jar").toURI()
					.toURL());
			final String jffmpegAudioDecoder = "net.sourceforge.jffmpeg.AudioDecoder";
			Codec codecAudio;
			codecAudio = (Codec) Class.forName(jffmpegAudioDecoder)
					.newInstance();
			PlugInManager.addPlugIn(jffmpegAudioDecoder,
					codecAudio.getSupportedInputFormats(),
					new Format[] { new AudioFormat("LINEAR") },
					PlugInManager.CODEC);
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private void asyncForward() {
		new Thread(new ForwardRunable(player)).start();
	}

	@Override
	public void play(String file) {
		if (isPlaying) {
			stop();
		}
		final File file2 = new File(file);
		URL url;
		try {
			url = file2.toURI().toURL();
			final MediaLocator locator = new MediaLocator(url);
			player = Manager.createPlayer(locator);
			player.realize();
			player.start();
			asyncForward();
			player.addControllerListener(new ControllerListener() {

				@Override
				public void controllerUpdate(ControllerEvent event) {
					if (event instanceof EndOfMediaEvent) {
						stop();
					}
				}
			});
			logger.info("Start playing song: " + file);
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
		isPlaying = true;
	}

	@Override
	public void stop() {
		if (isPlaying) {
			player.stop();
			player.close();
			player = null;
			logger.info("Stop playing");
			isPlaying = false;
		}
	}

	@Override
	public Float volumeDown() {
		if (isPlaying) {
			final float level = player.getGainControl().getLevel();
			try {
				player.getGainControl().setLevel(level - SLIDER_RANGE);
			} catch (final IllegalArgumentException e) {
				// do nothig
			}
			logger.info("Volume down. Current level: "
					+ player.getGainControl().getLevel());
			return player.getGainControl().getLevel();
		} else {
			return null;
		}
	}

	@Override
	public Float volumeUp() {
		if (isPlaying) {
			final float level = player.getGainControl().getLevel();
			try {
				player.getGainControl().setLevel(level + SLIDER_RANGE);
			} catch (final IllegalArgumentException e) {
				// do nothig
			}
			logger.info("Volume up. Current level: "
					+ player.getGainControl().getLevel());
			return player.getGainControl().getLevel();
		} else {
			return null;
		}
	}

}
