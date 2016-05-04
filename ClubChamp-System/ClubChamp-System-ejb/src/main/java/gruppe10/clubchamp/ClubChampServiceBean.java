package gruppe10.clubchamp;

import javax.ejb.Remote;
import javax.ejb.Stateless;

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
	
	@Override
	public String toString() {
		return "Hallo, ich bin eine Instanz von ClubChampServiceBean!";
	}

	@Override
	public String login(String username, String password) throws LoginFailedException{ 
		String sessionID = null;
		User client = UserRegistry.getInstance().findCustomerByName(username);
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
		SessionRegistry.getInstance().removeSession(session);
		logger.info(session + " Logout erfolgreich.");		
	}
	
	private UserSession getSession(String sessionID) throws NoSessionException {
		UserSession session = SessionRegistry.getInstance().findSession(sessionID);
		if (session==null)
			throw new NoSessionException("Session-ID unbekannt.");
		else
			return session;
	}

}