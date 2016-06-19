package gruppe10.club;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import gruppe10.user.User;
import gruppe10.entities.ClubBewertung;

/**
 * Klasse ClubBewertungenRegistry als Singleton Klasse. Alle vorhandenen
 * ClubBewertungen sollen hier gespeichert werden.
 * 
 * @author M.Tork
 * 
 * @deprecated Der Webservice nutzt diese Registry nicht mehr. Clubbewertungen
 *             werden vom Webservice in eine Datenbank persistiert.
 */
@Deprecated
@Startup
@Singleton
public class ClubBewertungenRegistry {

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
