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
	 * Methode zum Ausloggen.
	 * @throws NoSessionException
	 * @param sessionID
	 * @throw NoSessionException
	 */
	public void logout(String sessionID) throws NoSessionException;
	
}
