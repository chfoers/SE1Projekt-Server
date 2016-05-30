package gruppe10.musik;

import java.util.ArrayList;
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
	}
	
	@Lock(LockType.WRITE)
	public void addMusic(Music newMusic) {
		this.musikListe.add(newMusic);
		logger.info("Musikstück angelegt: " + newMusic);	
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
		logger.info("MusikListe von MusicRegistry wird übergeben");	
		return musikListe;
	}

}
