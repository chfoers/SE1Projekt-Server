package gruppe10.common;

/**
 * Dieses Exception-Klasse definiert eine Exception für den Fall, dass keine Session vorhanden ist.
 * 
 * @author M.Tork  
 */
public class NoSessionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NoSessionException() {
	}

	public NoSessionException(String message) {
		super(message);
	}

}