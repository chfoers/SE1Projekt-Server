package gruppe10.session;

import gruppe10.user.User;

public class UserSession {
	
	private static int lastID = 0;
	
	private final String sessionID;
	private final User user;
	
	public UserSession(User user) {
		lastID++;
		this.sessionID = "" + lastID;
		this.user = user;
		SessionRegistry.getInstance().addSession(this);
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