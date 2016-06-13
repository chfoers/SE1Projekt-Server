package gruppe10.common;

import java.util.ArrayList;

import gruppe10.musik.Music;

/**
 * Dieses Business Interface definiert die Schnittstelle zwischen Client und
 * Server.
 * 
 * @deprecated Die Verwendung eines Webservices macht dieses Interface
 *             (eigentlich) überflüssig.
 * 
 * @author M.Tork
 */
@Deprecated
public interface ClubChampService {

	/**
	 * Methode zum Einloggen mit E-Mail und Password.
	 * 
	 * @param mail
	 * @param password
	 * @return sessionID
	 * @throws LoginFailedException
	 * 
	 */
	public String login(String mail, String password) throws LoginFailedException;

	/**
	 * Methode zum Registrieren eines Users.
	 * 
	 * @param mail
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public boolean signUp(String mail, String username, String password) throws SignUpFailedException;

	/**
	 * Methode zum Ausloggen.
	 * 
	 * @param sessionID
	 * @throw NoSessionException
	 */
	public void logout(String sessionId) throws NoSessionException;

	/**
	 * Methode zum Wünschen von Musikstücken.
	 * 
	 * @param sessionId
	 * @param song
	 * @param artist
	 * @return String
	 * 
	 */
	public String musikWuenschen(String sessionId, String song, String artist);

	/**
	 * Methode zum Bewerten des Clubs.
	 * 
	 * @param sessionId
	 * @param rating
	 * 
	 */
	public void clubBewerten(String sessionId, int rating);

	/**
	 * Methode zur Ausgabe der Musikliste.
	 * 
	 * @return ArrayList (Musikliste)
	 * 
	 */
	public ArrayList<Music> musikWuenscheAusgeben();

	/**
	 * Methode zum Liken eines Musikstückes.
	 * 
	 * @param sessionID
	 * @param song
	 * @param artist
	 * @return String
	 * 
	 */
	public String musikLiken(String sessionId, String song, String artist);

	/**
	 * Methode zum Feedback geben.
	 * 
	 * @param sessionId
	 * @param feedback
	 *            [0=Musikwunsch passt nicht in den heutigen Rahmen; 1 =
	 *            Musikwunsch wird bald gespielt]
	 * @param song
	 * @param artist
	 * @return boolean
	 * 
	 */
	public boolean feedbackGeben(String sessionId, int feedback, String song, String artist);

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
	public boolean musikWurdeGespielt(String sessionId, String song, String artist);

	/**
	 * Musikwunschliste säubern. (Z.B. am Ende einer Veranstaltung)
	 * 
	 * @param sessionId
	 * 
	 */
	public void clearMusicWunschliste(String sessionId);

}
