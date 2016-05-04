package gruppe10.user;

import java.io.Serializable;

/**
 * Dieses Klasse bildet einen User ab.
 * 
 * @author M.Tork  
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User [userName = " + userName+"]";
	}

}