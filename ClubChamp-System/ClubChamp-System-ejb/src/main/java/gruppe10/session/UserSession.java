package gruppe10.session;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import gruppe10.user.User;

/**
 * Diese Klasse bildet eine UserSession ab.
 * 
 * @author M.Tork  
 */
public class UserSession {
	
	private static int lastID = 0;
	
	private final String sessionID;
	private final User user;
	
	private SessionRegistry sessionRegistry;
	
	public UserSession(User user) {
		Context context;
		try {
			context = new InitialContext();
			String lookupString = "java:global/ClubChamp-System-ear/ClubChamp-System-ejb-0.0.1/SessionRegistry!gruppe10.session.SessionRegistry";
			sessionRegistry = (SessionRegistry) context.lookup(lookupString);
		} 
		catch (NamingException e) {
			e.printStackTrace();
		}		
		lastID++;
		this.sessionID = "" + lastID;
		this.user = user;
		sessionRegistry.addSession(this);
	}

	public String getSessionID() {
		return sessionID;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return "UserSession["+this.sessionID+","+this.getUser().getUserName()+"]";
	}

}