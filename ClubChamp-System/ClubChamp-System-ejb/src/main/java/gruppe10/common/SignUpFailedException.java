package gruppe10.common;

/**
 * Dieses Exception-Klasse definiert eine Exception für den Fall, dass es im SignUp-Vorgang zu einem Fehler kommt.
 * 
 * @author M.Tork  
 */

public class SignUpFailedException extends Exception  {
	
	private static final long serialVersionUID = 1L;

	public SignUpFailedException() {
	}

	public SignUpFailedException(String message) {
		super(message);
	}

}
