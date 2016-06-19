package gruppe10.musik;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import gruppe10.entities.Music;

import org.jboss.logging.Logger;

/**
 * Klasse MusicRegistry als Singleton Klasse. Alle gewünschten Musik-Objekte
 * sollen hier gespeichert werden.
 * 
 * @deprecated : Persistierung in MySQL-Datenbank über DAO.
 * 
 * @author M.Tork
 * 
 * Methoden dieser Registry in ClubChampDao. Diese Klasse wurde aus Informationszwecken nicht gelöscht
 * @author Christian Förster
 */



@Startup
@Singleton
@Deprecated
public class MusicRegistry {

	private static final Logger logger = Logger.getLogger(MusicRegistry.class);

	private ArrayList<Music> musikListe;

	@PostConstruct
	public void init() {
		this.musikListe = new ArrayList<Music>();
		Music Bsp1 = new Music("Hypnotize", "Notorius BIG");
		this.addMusic(Bsp1);
		Music Bsp2 = new Music("Alle meine Entchen", "Eskuche");
		this.addMusic(Bsp2);
	}

	@Lock(LockType.WRITE)
	public void addMusic(Music newMusic) {
		this.musikListe.add(newMusic);
		logger.info("Musikstück angelegt: " + newMusic);
	}

	@Lock(LockType.WRITE)
	public void deleteMusic(Music newMusic) {
		this.musikListe.remove(newMusic);
		logger.info("Musikstück: " + newMusic + "wurde entfernt.");
	}

	@Lock(LockType.WRITE)
	public void clearMusic() {
		this.musikListe.clear();
		logger.info("Musikliste wurde geleert.");
	}

	@Lock(LockType.READ)
	public Music findMusic(String song, String artist) {
		for (Music temp : musikListe) {
			if (temp.getArtist().equals(artist) && temp.getSong().equals(song)) {
				return temp;
			}
		}
		return null;
	}

	@Lock(LockType.READ)
	public ArrayList<Music> musikListeAusgeben() {
		return musikListe;
	}

}
