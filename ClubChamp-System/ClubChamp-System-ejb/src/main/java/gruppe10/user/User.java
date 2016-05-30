package gruppe10.user;

import java.io.Serializable;
import java.util.ArrayList;

import gruppe10.musik.Music;

/**
 * Diese Klasse bildet einen User ab.
 * 
 * @author M.Tork  
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private ArrayList<Music> gelikteMusik;
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.gelikteMusik = new ArrayList<Music>();
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User [userName = " + userName+"]";
	}
	
	public void addMusik(Music music){
		gelikteMusik.add(music);
	}
	
	public Music findeGelikteMusic(Music music){
		for(Music tmp: gelikteMusik){
			if(tmp.getSong().equals(music.getSong()) && tmp.getArtist().equals(music.getArtist())){
				return tmp;				
			}
		}
		return null;
	}
}