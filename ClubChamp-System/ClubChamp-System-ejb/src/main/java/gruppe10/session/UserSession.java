package gruppe10.session;

import gruppe10.user.User;

/**
 * Diese Klasse bildet eine UserSession ab.
 * 
 * @author M.Tork
 */
public class UserSession {

	private static int lastId = 0;

	private String sessionId = null;
	private User user = null;

	public UserSession(User user) {
		lastId++;
		this.sessionId = "" + lastId;
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "UserSession[" + this.sessionId + "," + this.getUser().getMail() + "]";
	}

}