package gruppe10.musik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

import gruppe10.user.User;

/**
* Klasse MusicRegistry als Singleton Klasse. 
* Alle gewünschten Musik-Objekte sollen hier gespeichert werden.
* 
* @author M.Tork
*/
@Startup
@Singleton
public class MusicRegistry {
	
	private static final Logger logger = Logger.getLogger(MusicRegistry.class);
	
	private ArrayList<Music> musikListe;
	
	@PostConstruct
	public void init() {
			this.musikListe = new ArrayList<Music>();
			// hier noch Beispiel-Daten 
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
		for(Music temp: musikListe){
			if(temp.getArtist().equals(artist) && temp.getSong().equals(song)){
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
