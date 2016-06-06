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

	// userName sollte unique sein
	private String userName;
	private String password;
	private ArrayList<Music> gelikteMusik;
	// mail sollte unique sein
	private String mail;
	private boolean Dj = false;

	public User(String mail, String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.gelikteMusik = new ArrayList<Music>();
	}

	public User(String mail, String userName, String password, boolean isDj) {
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.Dj = isDj;
		this.gelikteMusik = new ArrayList<Music>();
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
		gelikteMusik.add(music);
	}

	public void deleteMusic(Music music) {
		gelikteMusik.remove(music);
	}

	public void clearGelikteMusik() {
		gelikteMusik.clear();
	}

	public Music findeGelikteMusic(Music music) {
		for (Music tmp : gelikteMusik) {
			if (tmp.getSong().equals(music.getSong()) && tmp.getArtist().equals(music.getArtist())) {
				return tmp;
			}
		}
		return null;
	}
}