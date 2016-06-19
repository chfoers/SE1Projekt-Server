package gruppe10.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.logging.Logger;

import gruppe10.entities.ClubBewertung;
import gruppe10.entities.Music;

/**
 * Session Bean implementation class DataBuilder
 *         TestDaten
 *         
 * @author Christian Förster
 */
@Singleton
@Startup
public class DataBuilder {
	
	private static final Logger logger = Logger.getLogger(DataBuilder.class);

	@PersistenceContext
	EntityManager em;

	@EJB
	ClubchampDAOLocal dao;

	@Resource
	private Music music1, music2;

	@Resource
	private String song1, artist1, song2, artist2;

	@Resource
	private ClubBewertung clubBewertung1;

	@Resource
	private int rating1;

	@PostConstruct
	private void createTestData() {

		Music music1 = new Music();
		music1.setSong(song1);
		music1.setArtist(artist1);
		em.persist(music1);
		logger.info("Neu angelegt:" + music1);

		Music music2 = new Music();
		music2.setSong(song2);
		music2.setArtist(artist2);
		em.persist(music2);
		logger.info("Neu angelegt:" + music2);

		ClubBewertung clubBewertung1 = new ClubBewertung();
		clubBewertung1.setRating(rating1);
		em.persist(clubBewertung1);
		logger.info("Neu angelegt:" + clubBewertung1);
	}
}
