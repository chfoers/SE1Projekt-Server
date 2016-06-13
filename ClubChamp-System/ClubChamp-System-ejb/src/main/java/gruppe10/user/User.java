package gruppe10.user;

import java.io.Serializable;
import java.util.ArrayList;

import gruppe10.musik.Music;

/**
 * Diese Klasse bildet einen User ab.
 * 
 * @author M.Tork
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private ArrayList<Music> musikGeliked;
	private String mail;
	private boolean Dj = false;

	public User(String mail, String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.musikGeliked = new ArrayList<Music>();
	}

	public User(String mail, String userName, String password, boolean isDj) {
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.Dj = isDj;
		this.musikGeliked = new ArrayList<Music>();
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getMail() {
		return mail;
	}

	public boolean isDj() {
		return this.Dj;
	}

	@Override
	public String toString() {
		return "User [UserName = " + userName + ", Mail = " + mail + "]";
	}

	public void addMusik(Music music) {
		musikGeliked.add(music);
	}

	public void deleteMusic(Music music) {
		musikGeliked.remove(music);
	}

	public void clearMusikGeliked() {
		musikGeliked.clear();
	}

	public Music findeMusikGeliked(Music music) {
		for (Music tmp : musikGeliked) {
			if (tmp.getSong().equals(music.getSong()) && tmp.getArtist().equals(music.getArtist())) {
				return tmp;
			}
		}
		return null;
	}
}