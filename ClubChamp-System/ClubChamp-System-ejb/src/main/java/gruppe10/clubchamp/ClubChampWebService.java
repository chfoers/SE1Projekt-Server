package gruppe10.clubchamp;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.logging.Logger;

import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.common.SignUpFailedException;
import gruppe10.dao.ClubchampDAOLocal;
import gruppe10.entities.Music;
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
	private ClubchampDAOLocal dao;
	@EJB
	private OutputRequesterBean outputRequester;

	public String toString() {
		return "Hallo, ich bin eine Instanz von ClubChampWebService!";
	}

	private User getUserWithSessionId(String sessionId) {
		UserSession usersession = sessionRegistry.findSession(sessionId);
		User user = usersession.getUser();
		return user;
	}

	/**
	 * Methode zum Einloggen mit E-Mail und Password.
	 * 
	 * @param mail
	 * @param password
	 * @return sessionID
	 * @throws LoginFailedException
	 * 
	 */
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

	/**
	 * Methode zum Ausloggen.
	 * 
	 * @param sessionID
	 * @throw NoSessionException
	 */
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

	/**
	 * Methode zum Registrieren eines Users.
	 * 
	 * @param mail
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public boolean signUp(String mail, String username, String password) throws SignUpFailedException {
		boolean success = false;
		if (userRegistry.findCustomerByMail(mail) != null) {
			logger.info("Registrierung fehlgeschlagen. Der User ist schon vorhanden.");
			throw new SignUpFailedException("Registrierung fehlgeschlagen. Der User ist schon vorhanden.");
		} else if (userRegistry.findCustomerByMail(mail) == null) {
			User newUser = new User(mail, username, password);
			userRegistry.addUser(newUser);
			logger.info("Kunde registriert: " + newUser);
			String message = "Kunde" + newUser.getUserName() + "hat sich erfolgreich registriert, Herzliche Willkommen";
			logger.info(message);
			outputRequester.printLetter(message);

			success = true;
		}
		return success;
	}

	/**
	 * Methode zum Wünschen von Musikstücken.
	 * 
	 * @param sessionId
	 * @param song
	 * @param artist
	 * @return String
	 */
	public String musikWuenschen(String sessionId, String song, String artist) {
		Music music = dao.findMusic(song, artist);
		String success = null;
		if (music != null) {
			success = "Musikwunsch schon vorhanden. Song wird stattdessen geliked: ";
			success = success + this.musikLiken(sessionId, song, artist);
			logger.info(success);
			return success;
		} else {
			Music newMusic = dao.addMusic(song, artist);
			logger.info("Musikstück in Allgemeiner-Wunsch-Liste abgespeichert: " + newMusic);
			User user = getUserWithSessionId(sessionId);
			user.addMusik(newMusic);
			logger.info("Musikstück in Wunsch-Liste vom User abgespeichert.");
			success = newMusic + " erfolgreich gewünscht";
			logger.info(newMusic + " erfolgreich gewünscht");
			return success;
		}
	}

	/**
	 * Methode zum Liken eines Musikstückes.
	 * 
	 * @param sessionID
	 * @param song
	 * @param artist
	 * @return String
	 * 
	 */
	public String musikLiken(String sessionId, String song, String artist) {
		String success = null;
		User user = getUserWithSessionId(sessionId);
		Music music = dao.findMusic(song, artist);
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

	/**
	 * Methode zum Bewerten des Clubs.
	 * 
	 * @param sessionId
	 * @param rating
	 * 
	 * @author Michael Tork
	 */
	/*
	 * public void clubBewerten(String sessionId, int rating) { ClubBewertung
	 * clubBewertung = new ClubBewertung(rating); User user =
	 * getUserWithSessionId(sessionId);
	 * clubBewertungenRegistry.addClubBewertung(user, clubBewertung);
	 * logger.info("Eintrag in ClubBewertungenRegistry angelegt: [" +
	 * user.getUserName() + "," + clubBewertung.getRating() + "]."); }
	 */

	/**
	 * Methode zum Bewerten des Clubs.
	 * 
	 * @param rating
	 * @param sessionId
	 * 
	 * @author Christian Förster
	 */
	public void clubBewerten(int rating, String sessionId) {
		// ClubBewertung newClubBewertung = dao.addClubBewertung(rating);
		dao.addClubBewertung(rating);
		// User user = getUserWithSessionId(sessionId);
		logger.info("Eine ClubBewertung wird in die Tabelle hinzugefügt");
	}

	/**
	 * Methode zur Ausgabe der Musikliste.
	 * 
	 * @return String[] (Musikliste)
	 * 
	 */
	public String[] musikWuenscheAusgeben() {
		logger.info("MusikListe von ClubChamoServiceBean wird übergeben");
		List<Music> musikListe = dao.musikListeAusgeben();
		Collections.sort(musikListe);
		String[] musicArray = new String[musikListe.size()];
		for (int i = 0; i < musikListe.size(); i++) {
			musicArray[i] = musikListe.get(i).toString();
		}
		return musicArray;
	}

	/**
	 * Methode zum Feedback geben.
	 * 
	 * @param sessionId
	 * @param feedback [0=Musikwunsch passt nicht in den heutigen Rahmen;1 = Musikwunsch wird bald gespielt]
	 * @param song
	 * @param artist
	 * @return boolean
	 * 
	 */
	public boolean feedbackGeben(String sessionId, int feedback, String song, String artist) {
		boolean success = false;
		User user = getUserWithSessionId(sessionId);
		if (user.isDj()) {
			Music music = dao.findMusic(song, artist);
			music.setFeedback(feedback);
			success = true;
			logger.info("Feedback gegeben.");
		} else {
			logger.info("Feedback gegeben fehlgeschlagen. Nur als DJ möglich.");
		}
		return success;
	}

	/**
	 * Methode, mit der der DJ ein Musikstück als gespielt deklariert. Folge:
	 * Musikstück wird aus MusikListe entfernt.
	 * 
	 * @param sessionId
	 * @param song
	 * @param artist
	 * @return boolean
	 * 
	 */
	public boolean musikWurdeGespielt(String sessionId, String song, String artist) {
		User user = getUserWithSessionId(sessionId);
		if (user.isDj()) {
			Music music = dao.findMusic(song, artist);
			dao.deleteMusic(music.getId());
			logger.info(music + " erfolgreich aus der Wunschliste entfernt.");
			Collection<User> users = userRegistry.returnAllUser();
			for (User tmp : users) {
				tmp.deleteMusic(music.getArtist(), music.getSong());
			}
			logger.info(music + " erfolgreich aus der User-Wunsch-Liste entfernt.");
			return true;
		}
		return false;
	}

	/**
	 * Musikwunschliste säubern. (Z.B. am Ende einer Veranstaltung)
	 * 
	 * @param sessionId
	 * 
	 */
	public boolean clearMusicWunschliste(String sessionId) {
		User user = getUserWithSessionId(sessionId);
		if (user.isDj()) {
			dao.clearMusic();
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