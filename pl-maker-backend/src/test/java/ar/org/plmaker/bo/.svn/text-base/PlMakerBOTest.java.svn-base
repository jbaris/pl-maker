package ar.org.plmaker.bo;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.org.plmaker.PlMakerService;
import ar.org.plmaker.TransactionalSpringContextTest;
import ar.org.plmaker.entities.Song;

@TransactionConfiguration(defaultRollback = true)
public class PlMakerBOTest extends TransactionalSpringContextTest {

	public PlMakerBOTest() {
		System.setProperty("musicDir", "/home/juani/music/aerosmith/");
	}

	private Collection<String[]> createParams(String... string) {
		final Collection<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < string.length; i += 2) {
			final String[] param = new String[] { string[i], string[i + 1] };
			result.add(param);

		}
		return result;
	}

	private PlMakerBO getPlMakerBO() {
		return (PlMakerBO) getApplicationContext().getBean("plmaker.bo");
	}

	@Test
	public void testCreatePlayList() {
		assertFalse(getPlMakerBO().getPlayLists().contains("sample"));
		assertTrue(getPlMakerBO().createPlayList("sample"));
		assertEquals(1, getPlMakerBO().getPlayLists().size());
		assertEquals("sample", getPlMakerBO().getPlayLists().get(0));
		assertFalse(getPlMakerBO().createPlayList("sample"));
		try {
			getPlMakerBO().createPlayList(null);
			fail("Must throw an exception");
		} catch (final IllegalArgumentException e) {
			// Test ok
		}
	}

	@Test
	public void testExecuteAddCommand() {
		getPlMakerBO().createPlayList("sample");
		assertTrue(getPlMakerBO().getSongs("sample").isEmpty());
		// Add song
		getPlMakerBO().execute(
				PlMakerService.ADD_SONG,
				createParams("song", "/home/juani/music/aerosmith/DreamOn.mp3",
						"playList", "sample"));
		assertEquals(1, getPlMakerBO().getSongs("sample").size());
		String song = getPlMakerBO().getSongs("sample").get(0);
		assertEquals("/home/juani/music/aerosmith/DreamOn.mp3", song);
		// Undo add song
		getPlMakerBO().undoLastCommand();
		assertTrue(getPlMakerBO().getSongs("sample").isEmpty());
		// Add dir
		getPlMakerBO().execute(
				PlMakerService.ADD_DIR,
				createParams("dir", "/home/juani/music/aerosmith/", "playList",
						"sample"));
		assertEquals(2, getPlMakerBO().getSongs("sample").size());
		song = getPlMakerBO().getSongs("sample").get(0);
		assertEquals("/home/juani/music/aerosmith/DreamOn.mp3", song);
		song = getPlMakerBO().getSongs("sample").get(1);
		assertEquals("/home/juani/music/aerosmith/Mamakin.mp3", song);
		// Undo add dir
		getPlMakerBO().undoLastCommand();
		assertTrue(getPlMakerBO().getSongs("sample").isEmpty());
	}

	@Test
	public void testExecuteSkipCommand() {
		getPlMakerBO().createPlayList("sample");
		assertTrue(getPlMakerBO().getSongs("sample").isEmpty());
		final Song next = getPlMakerBO().getNextUncategorized("sample");
		assertTrue(next != null);
		// Skip song
		getPlMakerBO().execute(PlMakerService.SKIP_SONG,
				createParams("song", next.getFile(), "playList", "sample"));
		Song next2 = getPlMakerBO().getNextUncategorized("sample");
		assertFalse(next.equals(next2));
		// Undo skip song
		getPlMakerBO().undoLastCommand();
		next2 = getPlMakerBO().getNextUncategorized("sample");
		assertTrue(next.equals(next2));
		// Skip dir
		getPlMakerBO().execute(
				PlMakerService.SKIP_DIR,
				createParams("dir", "/home/juani/music/aerosmith/", "playList",
						"sample"));
		assertTrue(getPlMakerBO().getNextUncategorized("sample") == null);
		// Undo skip dir
		getPlMakerBO().undoLastCommand();
		next2 = getPlMakerBO().getNextUncategorized("sample");
		assertTrue(next.equals(next2));
	}

	public void testNextUncategorized() {
		getPlMakerBO().createPlayList("sample");
		assertTrue(getPlMakerBO().getSongs("sample").isEmpty());
		assertTrue(getPlMakerBO().getNextUncategorized("sample") != null);
		// Skip song
		getPlMakerBO().execute(
				PlMakerService.SKIP_SONG,
				createParams("song", "/home/juani/music/aerosmith/Mamakin.mp3",
						"playList", "sample"));
		assertTrue(getPlMakerBO().getNextUncategorized("sample") != null);
		// Add song
		getPlMakerBO().execute(
				PlMakerService.ADD_SONG,
				createParams("song", "/home/juani/music/aerosmith/DreamOn.mp3",
						"playList", "sample"));
		assertTrue(getPlMakerBO().getNextUncategorized("sample") == null);
		// Undo add and skip
		getPlMakerBO().undoLastCommand();
		final Song next = getPlMakerBO().getNextUncategorized("sample");
		assertTrue(next != null);
		getPlMakerBO().undoLastCommand();
		assertTrue(next.equals(getPlMakerBO().getNextUncategorized("sample")));
	}

}
