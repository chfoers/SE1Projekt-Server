package gruppe10.user;

import java.util.HashMap;

import org.jboss.logging.Logger;

/**
* Klasse UserRegistry als Singleton Klasse. Alle vorhandenen User-Objekte sollen hier registriert werden, damit die UserRegistry
* als zentrales User-Verzeichnis fungieren kann.
* 
* @author M.Tork
*/

public class UserRegistry {
	
	private static final Logger logger = Logger.getLogger(UserRegistry.class);
	
	private static UserRegistry singleInstance;
	
	private HashMap<String,User> users;
	
	private UserRegistry() {
		users = new HashMap<String, User>();
	}
	
	public static UserRegistry getInstance() {
        if (singleInstance==null) {
        	singleInstance = new UserRegistry();
			//erzeuge Beispieldaten:
			User michael = new User("michael", "123");
			singleInstance.addUser(michael);
			logger.info("Kunde angelegt: " + michael);			
        }
		return singleInstance;
	}
	
	public User findCustomerByName(String userName) {
		return this.users.get(userName);
	}
	
	public void addUser(User newUser) {
		this.users.put(newUser.getUserName(), newUser);
	}

}
