package gruppe10.clubchamp;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import gruppe10.club.ClubBewertung;
import gruppe10.club.ClubBewertungenRegistry;
import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.common.SignUpFailedException;
import gruppe10.musik.Music;
import gruppe10.musik.MusicRegistry;
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
	
	@Resource
	private String clubname;
	@EJB
	private UserRegistry userRegistry;
	@EJB
	private SessionRegistry sessionRegistry;
	@EJB
	private MusicRegistry musicRegistry;
	@EJB
	private ClubBewertungenRegistry clubBewertungenRegistry;

	@Override
	public String toString() {
		return "Hallo, ich bin eine Instanz von ClubChampServiceBean!";
	}

	@Override
	public String login(String username, String password) throws LoginFailedException{ 
		String sessionID = null;
		User client = this.userRegistry.findCustomerByName(username);
		if (client!=null && client.getPassword().equals(password)){ // bis hier hin läuft alles mit aquillian(login-test)
			UserSession newSession = new UserSession(client);//ab dieser zeile läuft nix mehr (arquillien-test-login)
			sessionID = newSession.getSessionID();
			logger.info(newSession + " Login erfolgreich.");
		} else {
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
		if (session==null){
			logger.info("Logout fehlgeschlagen, da Session-ID unbekannt.");
			throw new NoSessionException("Session-ID unbekannt.");
		}
		else
			return session;
	}

	@Override
	public boolean signUp(String username, String password)throws SignUpFailedException {
		boolean success = false;
		if(userRegistry.findCustomerByName(username)!=null){
			logger.info("Logout fehlgeschlagen, da Session-ID unbekannt.");
			throw new SignUpFailedException("Registrierung fehlgeschlagen. Der User ist schon vorhanden.");
		}else if(userRegistry.findCustomerByName(username)==null){
			User newUser = new User(username, password);
			userRegistry.addUser(newUser);
			logger.info("Kunde registriert: " + newUser);	
			success = true;
		}
		return success;
	}

	@Override
	public void musikWuenschen(String song, String artist) {
		Music newMusic = new Music(song, artist);
		musicRegistry.addMusic(newMusic);	
		logger.info("Musikstück in Liste abgespeichert: " + newMusic);	
	}

	@Override
	public void clubBewerten(String sessionId, int rating) {
	    //public void clubBewerten(User user, int rating) {
		ClubBewertung clubBewertung = new ClubBewertung(rating);
		UserSession userSession = sessionRegistry.findSession(sessionId);
		User user = userSession.getUser();		
		clubBewertungenRegistry.addClubBewertung(user, clubBewertung);
		logger.info("Eintrag in  ClubBewertungenRegistry angelegt: ["+user.getUserName()+","+clubBewertung.getRating()+"].");
		
	}
	
}