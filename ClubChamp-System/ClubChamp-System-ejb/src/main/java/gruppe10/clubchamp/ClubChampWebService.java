package gruppe10.clubchamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.logging.Logger;

import gruppe10.club.ClubBewertung;
import gruppe10.club.ClubBewertungenRegistry;
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
 * Diese Stateless Session Bean stellt die ClubChamp-Operationen als Webservice
 * bereit.
 *
 * @author M.Tork
 *
 */
@WebService
@Stateless
public class ClubChampWebService {

	private static final Logger logger = Logger.getLogger(ClubChampWebService.class);

	@EJB
	private UserRegistry userRegistry;
	@EJB
	private SessionRegistry sessionRegistry;
	@EJB
	private MusicRegistry musicRegistry;
	@EJB
	private ClubBewertungenRegistry clubBewertungenRegistry;

	public String toString() {
		return "Hallo, ich bin eine Instanz von ClubChampWebService!";
	}

	private User getUserWithSessionId(String sessionId) {
		UserSession usersession = sessionRegistry.findSession(sessionId);
		User user = usersession.getUser();
		return user;
	}

	public String login(String mail, String password) throws LoginFailedException {
		String sessionId = null;
		User client = this.userRegistry.findCustomerByMail(mail);
		if (client != null && client.getPassword().equals(password)) {
			UserSession newSession = new UserSession(client);
			this.sessionRegistry.addSession(newSession);
			sessionId = newSession.getSessionId();
			logger.info(newSession + " Login erfolgreich.");
		} else {
			logger.info("Login fehlgeschlagen, da Client unbekannt oder Passwort falsch. mail=" + mail);
			throw new LoginFailedException("Login fehlgeschlagen");
		}
		return sessionId;
	}

	public boolean logout(String sessionId) throws NoSessionException {
		UserSession session = getSession(sessionId);
		if (session != null) {
			this.sessionRegistry.removeSession(session);
			logger.info(session + " Logout erfolgreich.");
			return true;
		} else {
			throw new NoSessionException("Logout fehlgeschlagen");
		}
	}

	private UserSession getSession(String sessionId) throws NoSessionException {
		UserSession session = this.sessionRegistry.findSession(sessionId);
		if (session == null) {
			logger.info("Session-Id unbekannt.");
			throw new NoSessionException("Session-Id unbekannt.");
		} else
			return session;
	}

	public boolean signUp(String mail, String username, String password) throws SignUpFailedException {
		boolean success = false;
		if (userRegistry.findCustomerByMail(mail) != null) {
			logger.info("Registrierung fehlgeschlagen. Der User ist schon vorhanden.");
			throw new SignUpFailedException("Registrierung fehlgeschlagen. Der User ist schon vorhanden.");
		} else if (userRegistry.findCustomerByMail(mail) == null) {
			User newUser = new User(mail, username, password);
			userRegistry.addUser(newUser);
			logger.info("Kunde registriert: " + newUser);
			success = true;
		}
		return success;
	}

	public String musikWuenschen(String sessionId, String song, String artist) {
		Music music = musicRegistry.findMusic(song, artist);
		String success = null;
		if (music != null) {
			success = "Musikwunsch schon vorhanden. Song wird stattdessen geliked: ";
			success = success + this.musikLiken(sessionId, song, artist);
			return success;
		} else {
			Music newMusic = new Music(song, artist);
			musicRegistry.addMusic(newMusic);
			logger.info("Musikstück in Allgemeiner-Wunsch-Liste abgespeichert: " + newMusic);
			User user = getUserWithSessionId(sessionId);
			user.addMusik(newMusic);
			success = newMusic + " erfolgreich gewünscht";
			return success;
		}
	}

	public String musikLiken(String sessionId, String song, String artist) {
		String success = null;
		User user = getUserWithSessionId(sessionId);
		Music music = musicRegistry.findMusic(song, artist);
		// Musik kann nur einmal vom selben Benutzer geliked werden
		if (user.findeMusikGeliked(music) == null) {
			music.likeSong();
			logger.info("Like: " + music);
			user.addMusik(music);
			success = music + " erfolgreich geliked.";
			return success;
		} else {
			logger.info("Song kann nicht zweimal vom selben Benutzer geliked werden.");
			success = "Song kann nicht zweimal vom selben Benutzer geliked werden.";
			return success;
		}
	}

	public void clubBewerten(String sessionId, int rating) {
		ClubBewertung clubBewertung = new ClubBewertung(rating);
		User user = getUserWithSessionId(sessionId);
		clubBewertungenRegistry.addClubBewertung(user, clubBewertung);
		logger.info("Eintrag in  ClubBewertungenRegistry angelegt: [" + user.getUserName() + ","
				+ clubBewertung.getRating() + "].");
	}

	public String[] musikWuenscheAusgeben() {
		logger.info("MusikListe von ClubChamoServiceBean wird übergeben");
		ArrayList<Music> musikListe = musicRegistry.musikListeAusgeben();
		Collections.sort(musikListe);
		String[] musicArray = new String[musikListe.size()];
		for (int i = 0; i < musikListe.size(); i++) {
			musicArray[i] = musikListe.get(i).toString();
		}
		return musicArray;
	}

	public boolean feedbackGeben(String sessionId, int feedback, String song, String artist) {
		boolean success = false;
		User user = getUserWithSessionId(sessionId);
		if (user.isDj()) {
			Music music = musicRegistry.findMusic(song, artist);
			music.setFeedback(feedback);
			success = true;
			logger.info("Feedback gegeben.");
		} else {
			logger.info("Feedback gegeben fehlgeschlagen.");
		}
		return success;
	}

	public boolean musikWurdeGespielt(String sessionId, String song, String artist) {
		User user = getUserWithSessionId(sessionId);
		if (user.isDj()) {
			Music music = musicRegistry.findMusic(song, artist);
			musicRegistry.deleteMusic(music);
			logger.info(music + " erfolgreich aus der MusicRegistry entfernt.");
			user.deleteMusic(music);
			logger.info(music + " erfolgreich aus der User entfernt.");
			return true;
		}
		return false;
	}

	public boolean clearMusicWunschliste(String sessionId) {
		UserSession usersession = sessionRegistry.findSession(sessionId);
		User user = usersession.getUser();
		if (user.isDj()) {
			musicRegistry.clearMusic();
			logger.info("MusikRegistry wurde geleert.");
			Collection<User> users = userRegistry.returnAllUser();
			for (User tmp : users) {
				tmp.clearMusikGeliked();
			}
			logger.info("Likelisten der User wurden geleert.");
			return true;
		} else {
			return false;
		}
	}

}