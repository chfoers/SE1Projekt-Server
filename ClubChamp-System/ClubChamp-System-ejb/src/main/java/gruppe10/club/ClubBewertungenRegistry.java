package gruppe10.club;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

import gruppe10.user.User;

/**
* Klasse ClubBewertungenRegistry als Singleton Klasse.
* Alle vorhandenen ClubBewertungen sollen hier gespeichert werden.
* 
* @author M.Tork
*/
@Startup
@Singleton
public class ClubBewertungenRegistry {
	
	private static final Logger logger = Logger.getLogger(ClubBewertungenRegistry.class);
	
	private HashMap<User, ClubBewertung> clubBewertungenRegistry;
	
	@PostConstruct
	public void init() {
			this.clubBewertungenRegistry = new HashMap<User, ClubBewertung>();
	}
	
	@Lock(LockType.WRITE)
	public void addClubBewertung(User user, ClubBewertung clubBewertung) {
		this.clubBewertungenRegistry.put(user, clubBewertung);
	}
	
	@Lock(LockType.READ)
	public ClubBewertung findBewertungByUser(User user) {
		return this.clubBewertungenRegistry.get(user);
	}

}
