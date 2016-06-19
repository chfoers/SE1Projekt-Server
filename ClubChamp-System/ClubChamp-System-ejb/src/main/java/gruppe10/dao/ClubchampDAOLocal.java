package gruppe10.dao;

import java.util.List;

import javax.ejb.Local;

import gruppe10.entities.ClubBewertung;
import gruppe10.entities.Music;

/**
* @author Christian Förster
* Interface, dass die Methoden zum Datenbankzugriff bereitstellt.
*/

@Local
public interface ClubchampDAOLocal {
	
	public Music addMusic(String song, String artist);

	public void deleteMusic(int id);

	public void clearMusic();

	public Music findMusic(String song, String artist);

	public List<Music> musikListeAusgeben();
	
	public ClubBewertung addClubBewertung(int rating);
	
	public ClubBewertung findClubBewertung(int rating);

}
