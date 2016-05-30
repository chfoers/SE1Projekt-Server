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
	
	private String sessionID = null;
	private User user = null;	
	
	public UserSession(User user) {
		lastID++;
		this.sessionID = "" + lastID;
		this.user = user;
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