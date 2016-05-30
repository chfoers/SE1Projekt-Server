package gruppe10.musik;

import java.io.Serializable;

/**
 * Diese Klasse bildet ein Musikstück ab.
 * 
 * @author M.Tork  
 */
public class Music implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String song;
	private String artist;
	private int likes;
	
	public Music(String song, String artist) {
		this.song = song;
		this.artist = artist;
		this.likes = 0;
	}

	public String getSong() {
		return this.song;
	}

	public String getArtist() {
		return this.artist;
	}
	
	public void likeSong(){
		this.likes = this.likes+1;
	}
	
	public int getLikes(){
		return this.likes;
	}

	@Override
	public String toString() {
		return "Music [Song = " + this.song + ", Artist = " +this.artist+ ", Likes = " +this.likes+ "]";
	}

}
