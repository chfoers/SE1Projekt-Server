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
	private String user1, password1, mail1, favouriteGenre1, user2, password2, mail2, favouriteGenre2;
	
	@PostConstruct
	public void init() {
			this.users = new HashMap<String, User>();
			//erzeuge Beispieldaten:
			User michael = new User(mail1, user1, password1, favouriteGenre1);
			this.addUser(michael);
			User hamster = new User(mail2, user2, password2, favouriteGenre2);
			this.addUser(hamster);
			logger.info("Kunde angelegt: " + hamster);	
	}
	
	@Lock(LockType.READ)
	public User findCustomerByMail(String mail) {
		return this.users.get(mail);
	}
	
	@Lock(LockType.WRITE)
	public void addUser(User newUser) {
		this.users.put(newUser.getMail(), newUser);
	}

}
