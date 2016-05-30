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
	
	//userName muss unique sein
	private String userName;
	private String password;
	private ArrayList<Music> gelikteMusik;
	//mail muss unique sein
	private String mail;
	private String favouriteGenre;
	
	public User(String mail, String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.gelikteMusik = new ArrayList<Music>();
	}
	
	public User(String mail, String userName, String password, String favouriteGenre) {
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.favouriteGenre = favouriteGenre;
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
	
	public String getFavouriteGenre() {
		return favouriteGenre;
	}

	@Override
	public String toString() {
		return "User [UserName = " + userName+", Mail = " +mail+ "]";
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