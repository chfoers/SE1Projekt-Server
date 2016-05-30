package gruppe10.clubchamp;

import java.util.ArrayList;

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
		if (client!=null && client.getPassword().equals(password)){ 
			UserSession newSession = new UserSession(client);
			sessionRegistry.addSession(newSession);
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
			logger.info("Registrierung fehlgeschlagen. Der User ist schon vorhanden.");
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
	public String musikWuenschen(String sessionId, String song, String artist) {
		Music music = musicRegistry.findMusic(song, artist);
		String success = null;
		if(music != null){
			success = "Musikwunsch schon vorhanden. Song wird stattdessen geliked: ";
			success = success + this.musikLiken(sessionId, song, artist);			
			return success;
		} else {
			Music newMusic = new Music(song, artist);
			musicRegistry.addMusic(newMusic);	
			logger.info("Musikstück in Allgemeiner-Wunsch-Liste abgespeichert: " + newMusic);
			UserSession userSession = sessionRegistry.findSession(sessionId);
			User user = userSession.getUser();	
			user.addMusik(newMusic);
			success = newMusic+ " erfolgreich gewünscht";
			return success;
		}
	}

	@Override
	public void clubBewerten(String sessionId, int rating) {
		ClubBewertung clubBewertung = new ClubBewertung(rating);
		UserSession userSession = sessionRegistry.findSession(sessionId);
		User user = userSession.getUser();		
		clubBewertungenRegistry.addClubBewertung(user, clubBewertung);
		logger.info("Eintrag in  ClubBewertungenRegistry angelegt: ["+user.getUserName()+","+clubBewertung.getRating()+"].");
		
	}

	@Override
	public ArrayList<Music> musikWuenscheAusgeben() {
		logger.info("MusikListe von ClubChamoServiceBean wird übergeben");
		return musicRegistry.musikListeAusgeben();
	}

	@Override
	public String musikLiken(String sessionId, String song, String artist) {
		String success = null;
		UserSession usersession = sessionRegistry.findSession(sessionId);
		User user = usersession.getUser();
		Music music = musicRegistry.findMusic(song, artist);		
		if(user.findeGelikteMusic(music)==null){
			music.likeSong();		
			logger.info("Like: " +music);
			user.addMusik(music);
			success = music + " erfolgreich geliked.";
			return success;
		} else {
			logger.info("Song kann nicht zweimal vom selben Benutzer geliked werden.");
			success = "Song kann nicht zweimal vom selben Benutzer geliked werden.";
			return success;
		}
	}
	
}