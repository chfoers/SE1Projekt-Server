package gruppe10.musik;

import java.io.Serializable;

/**
 * Diese Klasse bildet ein Musikstück ab.
 * Ein Musikstück wird durch den Sont(-titel) und den Artist bestimmt.
 * 
 * @author M.Tork  
 */
public class Music implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String song;
	private String artist;
	
	public Music(String song, String artist) {
		this.song = song;
		this.artist = artist;
	}

	public String getSong() {
		return song;
	}

	public String getArtist() {
		return artist;
	}

	@Override
	public String toString() {
		return "Music [Song = " + this.song + ", Artist = " +this.artist+ "]";
	}

}
