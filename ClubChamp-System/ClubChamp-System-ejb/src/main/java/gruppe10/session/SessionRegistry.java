package gruppe10.session;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Klasse SessionRegistry als Singleton Klasse. Alle vorhandenen UserSession-Objekte sollen hier registriert werden, damit die SessionRegistry
 * als zentrales UserSession-Verzeichnis fungieren kann.
 * 
 * @author M.Tork
 */
@Startup
@Singleton
public class SessionRegistry {
		
	private HashMap<String,UserSession> sessions;
	
	@PostConstruct
	public void init() {
		sessions = new HashMap<String, UserSession>();
	}
	
	@Lock(LockType.READ)
	public UserSession findSession(String sessionID) {
		return this.sessions.get(sessionID);
	}
	
	@Lock(LockType.WRITE)
	public void addSession(UserSession newSession) {
		this.sessions.put(newSession.getSessionID(), newSession);
	}
	
	@Lock(LockType.WRITE)
	public void removeSession(UserSession oldSession) {
		this.sessions.remove(oldSession.getSessionID());
	}

}