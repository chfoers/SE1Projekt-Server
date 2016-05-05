package gruppe10.user;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

/**
* Klasse UserRegistry als Singleton Klasse. Alle vorhandenen User-Objekte sollen hier registriert werden, damit die UserRegistry
* als zentrales User-Verzeichnis fungieren kann.
* 
* @author M.Tork
*/
@Startup
@Singleton
public class UserRegistry { 
	
	private static final Logger logger = Logger.getLogger(UserRegistry.class);
		
	private HashMap<String,User> users;
	
	@PostConstruct
	public void init() {
			this.users = new HashMap<String, User>();
			//erzeuge Beispieldaten:
			User michael = new User("michael", "123");
			this.addUser(michael);
			logger.info("Kunde angelegt: " + michael);	
	}
	
	@Lock(LockType.READ)
	public User findCustomerByName(String userName) {
		return this.users.get(userName);
	}
	
	@Lock(LockType.WRITE)
	public void addUser(User newUser) {
		this.users.put(newUser.getUserName(), newUser);
	}

}
