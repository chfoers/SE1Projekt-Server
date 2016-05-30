package gruppe10.common;

import java.util.ArrayList;

import gruppe10.musik.Music;
import gruppe10.user.User;

/**
 * Dieses Business Interface definiert die Schnittstelle zwischen Client und Server.
 * 
 * @author M.Tork  
 */
public interface ClubChampService{
	
	/**
	 * Methode zum Einloggen mit Username und Password.
	 * @throws LoginFailedException
	 * @param username
	 * @param password
	 * @return sessionID
	 */
	public String login(String username, String password) throws LoginFailedException;
	
	/**
	 * Methode zum Registrieren eines Users.
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public boolean signUp(String username, String password)throws SignUpFailedException;
	
	/**
	 * Methode zum Ausloggen.
	 * @throws NoSessionException
	 * @param sessionID
	 * @throw NoSessionException
	 */
	public void logout(String sessionID) throws NoSessionException;
	
	/**
	 * Methode zum Wünschen von Musikstücken.
	 * @param song
	 * @param artist
	 * @return boolean
	 * 
	 */
	public String musikWuenschen(String sessionId, String song, String artist);
	
	/**
	 * Methode zum Bewerten des Clubs.
	 * @param sessionId
	 * @param rating
	 * 
	 */
	public void clubBewerten(String sessionId, int rating);
	
	/**
	 * Methode zur Ausgabe der Musikliste.
	 * @return ArrayList (Musikliste)
	 * 
	 */
	public ArrayList<Music> musikWuenscheAusgeben();	
	
	/**
	 * Methode zum Liken eines Musikstückes.
	 * @param sessionID
	 * @param song
	 * @param artist
	 * 
	 */
	public String musikLiken(String sessionID, String song, String artist);
	
}
