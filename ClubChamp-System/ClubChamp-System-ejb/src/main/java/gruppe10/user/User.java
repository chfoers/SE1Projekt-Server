package gruppe10.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import gruppe10.entities.Music;

/**
 * Diese Klasse bildet einen User ab.
 * 
 * @author M.Tork
 * 
 * 
 */



@Entity
@Table(name="User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id;
	
	private String userName;
	private String password;
	
	/**
	 * ArrayList speichert alle Songs die User gewünscht hat seperat ab
	 * 
	 * @author Christian Förster
	 */
	@ElementCollection
    @CollectionTable(name="musikGeliked")
    @Column(name="musik")
    private List<Music> musikGeliked = new ArrayList<Music>();
	private String mail;
	private boolean Dj = false;
	
	
	
	
	//@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="clubbewertung")
	//private Map<Integer, ClubBewertung> clubbwertung;
	
	public User() {}

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
