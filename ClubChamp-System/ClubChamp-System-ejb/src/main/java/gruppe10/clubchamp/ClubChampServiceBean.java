package gruppe10.clubchamp;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.session.SessionRegistry;
import gruppe10.session.UserSession;
import gruppe10.user.User;
import gruppe10.user.UserRegistry;

/**
 * Diese Stateless Session Bean implementiert das ClubChamp-Interface.
 *
 *@author M.Tork
 *
 */
@Stateless
@Remote(ClubChampService.class)
public class ClubChampServiceBean implements ClubChampService{

	private static final Logger logger = Logger.getLogger(ClubChampServiceBean.class);

	private UserRegistry userRegistry;
	
	private SessionRegistry sessionRegistry;

	@Override
	public String toString() {
		return "Hallo, ich bin eine Instanz von ClubChampServiceBean!";
	}

	@Override
	public String login(String username, String password) throws LoginFailedException{ 
		String sessionID = null;
		User client = this.userRegistry.findCustomerByName(username);
		if (client!=null && client.getPassword().equals(password)) {
			UserSession newSession = new UserSession(client);
			sessionID = newSession.getSessionID();
			logger.info(newSession + " Login erfolgreich.");
		}
		else {
			logger.info("Login fehlgeschlagen, da Client unbekannt oder Passwort falsch. username="+username);
			throw new LoginFailedException("Login fehlgeschlagen");
		}
		return sessionID;
	}

	@Override
	public void logout(String sessionID) throws NoSessionException {
		UserSession session = getSession(sessionID);
		this.sessionRegistry.removeSession(session);
		logger.info(session + " Logout erfolgreich.");		
	}
	
	private UserSession getSession(String sessionID) throws NoSessionException {
		UserSession session = this.sessionRegistry.findSession(sessionID);
		if (session==null)
			throw new NoSessionException("Session-ID unbekannt.");
		else
			return session;
	}
	
	@PostConstruct
	public void init(){
		Context context;
		try {
			context = new InitialContext();
			String lookupString = "java:global/ClubChamp-System-ear/ClubChamp-System-ejb-0.0.1/UserRegistry!gruppe10.user.UserRegistry";
			userRegistry = (UserRegistry) context.lookup(lookupString);
			
			context = new InitialContext();
			lookupString = "java:global/ClubChamp-System-ear/ClubChamp-System-ejb-0.0.1/SessionRegistry!gruppe10.session.SessionRegistry";
			sessionRegistry = (SessionRegistry) context.lookup(lookupString);
		} 
		catch (NamingException e) {
			e.printStackTrace();
		}
	}

}