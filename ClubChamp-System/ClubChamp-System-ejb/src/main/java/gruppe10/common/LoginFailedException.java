package gruppe10.common;

/**
 * Dieses Exception-Klasse definiert eine Exception für den Fall, dass es im
 * Login-Vorgang zu einem Fehler kommt.
 * 
 * @author M.Tork
 */
public class LoginFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
	}

	public LoginFailedException(String message) {
		super(message);
	}

}
