package gruppe10.common;


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
	public void musikWünschen(String song, String artist);
	
}
