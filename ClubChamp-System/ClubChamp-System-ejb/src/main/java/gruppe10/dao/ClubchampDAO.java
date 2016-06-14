package gruppe10.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gruppe10.musik.Music;

@Stateless
public class ClubchampDAO implements ClubchampDAOLocal {

	@PersistenceContext
	EntityManager em;

	@Override
	public Music addMusic(String song, String artist) {
		Music music = new Music();
		music.setSong(song);
		music.setArtist(artist);
		em.persist(music);
		return music;
	}

	@Override
	public void deleteMusic(int id) {
		Music music = em.find(Music.class, id);
		if (music != null) {
			em.remove(music);
		}
	}

	@Override
	public void clearMusic() {
		int songs = em.createQuery("DELETE from Music m").executeUpdate();
	}
	
	//FEHLERRRRRRRRRRRRRR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public Music findMusic(String song, String artist) {
		List songs = em.createQuery("SELECT m FROM Music m WHERE m.song LIKE :custSong AND m.artist Like :custArtist")
				.setParameter("custSong", song).setParameter("custArtist", artist).getResultList();
		if (songs.size() == 1) {
			return (Music) songs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List musikListeAusgeben() {
		List songs = em.createQuery("Select m from Music m").getResultList();
		return songs;
	}

}
