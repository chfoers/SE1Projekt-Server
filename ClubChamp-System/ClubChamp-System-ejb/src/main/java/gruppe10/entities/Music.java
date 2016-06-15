package gruppe10.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Diese Klasse bildet ein Musikstück ab.
 * 
 * @author M.Tork
 */
@Entity
@Table(name = "Music")
public class Music implements Serializable, Comparable<Music> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String song;
	private String artist;
	private int likes;
	private String feedback;

	public Music() {
	}

	public Music(String song, String artist) {
		this.song = song;
		this.artist = artist;
		this.likes = 0;
		this.feedback = null;
	}

	public String getSong() {
		return this.song;
	}

	public String getArtist() {
		return this.artist;
	}

	public void likeSong() {
		this.likes = this.likes + 1;
	}

	public int getLikes() {
		return this.likes;
	}

	public void setFeedback(int i) {
		if (i == 0) {
			this.feedback = "Musikwunsch passt nicht in den heutigen Rahmen.";
		} else if (i == 1) {
			this.feedback = "Musikwunsch wird bald gespielt.";
		} else {
			this.feedback = "Feedback ungültig";
		}
	}

	public String getFeedback() {
		return this.feedback;
	}

	@Override
	public String toString() {
		return "Music [Song = " + this.song + ", Artist = " + this.artist + ", Likes = " + this.likes + ", Feedback = "
				+ this.feedback + "]";
	}

	@Override
	public int compareTo(Music other) {
		if (other.getLikes() == this.getLikes()) {
			return 0;
		} else if (other.getLikes() < this.getLikes()) {
			return -1;
		} else {
			return 1;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
