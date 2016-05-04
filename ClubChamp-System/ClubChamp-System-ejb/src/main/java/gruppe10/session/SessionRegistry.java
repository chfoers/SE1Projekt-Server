package gruppe10.session;

import java.util.HashMap;

/**
 * Klasse SessionRegistry als Singleton Klasse. Alle vorhandenen UserSession-Objekte sollen hier registriert werden, damit die SessionRegistry
 * als zentrales UserSession-Verzeichnis fungieren kann.
 * 
 * @author M.Tork
 */

public class SessionRegistry {
	
private static SessionRegistry singleInstance = new SessionRegistry();
	
	private HashMap<String,UserSession> sessions;
	
	private SessionRegistry() {
		sessions = new HashMap<String, UserSession>();
	}
	
	public static SessionRegistry getInstance() {
		return singleInstance;
	}
	
	public UserSession findSession(String sessionID) {
		return this.sessions.get(sessionID);
	}
	
	public void addSession(UserSession newSession) {
		this.sessions.put(newSession.getSessionID(), newSession);
	}
	
	public void removeSession(UserSession oldSession) {
		this.sessions.remove(oldSession.getSessionID());
	}

}