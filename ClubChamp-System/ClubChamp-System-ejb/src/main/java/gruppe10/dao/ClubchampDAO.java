package gruppe10.dao;

import java.util.List;


/**
* @author Christian Förster
*Implementiert Methoden der ClubChampDAOLocal.
*/

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import gruppe10.clubchamp.ClubChampWebService;
import gruppe10.entities.ClubBewertung;
import gruppe10.entities.Music;
import gruppe10.user.User;

@Stateless
public class ClubchampDAO implements ClubchampDAOLocal {
	
	private static final Logger logger = Logger.getLogger(ClubchampDAO.class);

	@PersistenceContext
	EntityManager em;

	
	
	/////////////////////////////////////////////////// MUSIC /////////////////////////////////
	/**
	* Diese Methode erstellt ein Music Object
	* @author Christian Förster
	*/
	@Override
	public Music addMusic(String song, String artist) {
		Music music = new Music();
		music.setSong(song);
		music.setArtist(artist);
		em.persist(music);
		return music;
	}
	
	/**
	* Diese Methode löscht ein Music Object
	* @author Christian Förster
	*/

	@Override
	public void deleteMusic(int id) {
		Music music = em.find(Music.class, id);
		if (music != null) {
			em.remove(music);
		}
	}
	
	/**
	* Diese Methode löscht alle Music Objecte
	* @author Christian Förster
	*/

	@Override
	public void clearMusic() {
		int songs = em.createQuery("DELETE from Music m").executeUpdate();
	}
	
	/**
	* Diese Methode findet ein bestimmtes Music Object
	* @author Christian Förster
	* @param
	*/
	
	@Override 
	public Music findMusic(String song, String artist) {
		List songs = em.createQuery("SELECT m FROM Music m WHERE m.song LIKE :custSong AND m.artist Like :custArtist")
				.setParameter("custSong", song)
				.setParameter("custArtist", artist)
				.getResultList();
		logger.info("55");
		if (songs.size() == 1) {
			return (Music) songs.get(0);
		} else {
			return null;
		}
	}
	
	/**
	* Diese Methode gibt eine MusikListe aus
	* @author Christian Förster
	*/

	@Override
	public List<Music> musikListeAusgeben() {
		List<Music>songs = em.createQuery("Select m from Music m").getResultList();
		if(songs.size() >=1){
			return songs;
			}
		else 
		{
			return null;
		}
			
	}
/////////////////////////////////////////////////// ClubBewertung ///////////////////////////////////////
	
	/**
	* Diese Methode erstellt ein ClubBewertung Object
	* @author Christian Förster
	*/
	
	@Override
	public ClubBewertung addClubBewertung(int rating){
		ClubBewertung clubBewertung = new ClubBewertung();
		clubBewertung.setRating(rating);
		em.persist(clubBewertung);
		return clubBewertung;
	}

	/**
	* Diese Methode findet ein ClubBewertung Object
	* @author Christian Förster
	*/
	
	@Override
	public ClubBewertung findClubBewertung(int id){
		List bewertung = em.createQuery("SELECT c FROM ClubBewertung c WHERE c.id LIKE :custId")
						.setParameter("custId", id)
						.getResultList();
		if (bewertung.size() == 1) {
			return (ClubBewertung) bewertung.get(0);
		} else {
			return null;
		}
	}

}






