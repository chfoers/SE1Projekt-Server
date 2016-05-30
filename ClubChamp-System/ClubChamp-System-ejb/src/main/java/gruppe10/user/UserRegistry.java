package gruppe10.user;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
	
	@Resource
	private String user1, password1, user2, password2;
	
	@PostConstruct
	public void init() {
			this.users = new HashMap<String, User>();
			//erzeuge Beispieldaten:
			User michael = new User(user1, password1);
			this.addUser(michael);
			User hamster = new User(user2, password2);
			this.addUser(hamster);
			logger.info("Kunde angelegt: " + hamster);	
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
