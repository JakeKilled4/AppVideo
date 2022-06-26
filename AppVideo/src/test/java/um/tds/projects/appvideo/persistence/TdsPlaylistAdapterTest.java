package um.tds.projects.appvideo.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import um.tds.projects.appvideo.backend.Playlist;
import um.tds.projects.appvideo.backend.Video;

public class TdsPlaylistAdapterTest {
	
	private static TdsPlaylistAdapter playlistAdpt;
	private static Playlist           playlist;
	
	@BeforeClass
	public static void setup() {
		playlistAdpt = TdsPlaylistAdapter.getUniqueInstance();
	}
	
	@Before
	public void before() {
		playlist = new Playlist("abcd");
		playlistAdpt.registerPlaylist(playlist);
	}
	
	@After
	public void after() {
		playlistAdpt.removePlaylist(playlist);
	}

	@Test
	public void canStoreAndDeletePlaylist() {
		Playlist p2 = playlistAdpt.loadPlaylist(playlist.getCode());
		assertEquals(playlist.getCode(), p2.getCode());
	}
	
	@Test
	public void canStoreName() {
		Playlist p2 = playlistAdpt.loadPlaylist(playlist.getCode());
		assertEquals(playlist.getName(), p2.getName());
	}
	
	@Test
	public void canModifyName() {
		playlist.setName("aaaa");
		playlistAdpt.modifyPlaylist(playlist);
		Playlist p2 = playlistAdpt.loadPlaylist(playlist.getCode());
		assertEquals("aaaa", p2.getName());
	}
	
	@Test
	public void canStoreVideo() {
		Playlist p1 = new Playlist("play");
		Video    v1 = new Video("a", "b", 1);
		p1.addVideo(v1);
		
		playlistAdpt.registerPlaylist(p1);
		Playlist p2 = playlistAdpt.loadPlaylist(p1.getCode());
		assertEquals(1, p2.getNumVideos());
		Video    v2 = p2.getVideos().get(0);
		assertEquals(v1.getCode(), v2.getCode());
		
		playlistAdpt.removePlaylist(p1);
	}
	
	@Test
	public void canStoreVideoTitle() {
		Playlist p1 = new Playlist("play");
		Video    v1 = new Video("a", "b", 1);
		p1.addVideo(v1);
		
		playlistAdpt.registerPlaylist(p1);
		Playlist p2 = playlistAdpt.loadPlaylist(p1.getCode());
		assertEquals(1, p2.getNumVideos());
		Video    v2 = p2.getVideos().get(0);
		assertEquals(v1.getTitle(), v2.getTitle());
		
		playlistAdpt.removePlaylist(p1);
	}
	
	@Test
	public void canStoreMultipleLabels() {
		Playlist p1 = new Playlist("play");
		Video    v1 = new Video("u1", "t1", 1);
		Video    v2 = new Video("u2", "t2", 2);
		Video    v3 = new Video("u3", "t3", 3);
		p1.addVideo(v1);
		p1.addVideo(v2);
		p1.addVideo(v3);
		
		playlistAdpt.registerPlaylist(p1);
		Playlist p2 = playlistAdpt.loadPlaylist(p1.getCode());
		
		assertEquals(3,    p2.getNumVideos());
		assertEquals("u1", p2.getVideos().get(0).getUrl());
		assertEquals("u2", p2.getVideos().get(1).getUrl());
		assertEquals("u3", p2.getVideos().get(2).getUrl());
		
		playlistAdpt.removePlaylist(p1);
	}
	
	@Test
	public void canAddMultipleLabels() {
		playlist.addVideo(new Video("u1", "t1", 1));
		playlist.addVideo(new Video("u2", "t2", 2));
		playlistAdpt.modifyPlaylist(playlist);
		
		Playlist p2 = playlistAdpt.loadPlaylist(playlist.getCode());
		
		assertEquals(2,    p2.getNumVideos());
		assertEquals("u1", p2.getVideos().get(0).getUrl());
		assertEquals("u2", p2.getVideos().get(1).getUrl());
	}
}