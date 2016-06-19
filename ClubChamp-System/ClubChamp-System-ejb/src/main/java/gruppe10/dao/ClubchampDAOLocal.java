package gruppe10.dao;

import java.util.List;

import javax.ejb.Local;

import gruppe10.entities.ClubBewertung;
import gruppe10.entities.Music;
import gruppe10.user.User;

/**
* @author Christian Förster
* Interface, dass die Methoden zum Datenbankzugriff bereitstellt.
*/

@Local
public interface ClubchampDAOLocal {
	//MusikDAO
	public Music addMusic(String song, String artist);

	public void deleteMusic(int id);

	public void clearMusic();

	public Music findMusic(String song, String artist);

	public List<Music> musikListeAusgeben();
	
	
	//ClubBewertenDAO
	
	public ClubBewertung addClubBewertung(int rating);
	
	//public ClubBewertung findBewertungByUser(User user);
	
	public ClubBewertung findClubBewertung(int rating);

	

}
