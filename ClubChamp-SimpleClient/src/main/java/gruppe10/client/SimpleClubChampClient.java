package gruppe10.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.common.SignUpFailedException;


/**
 * Diese Klasse realisiert einen rudimentaeren Java-Client für den Zugriff auf das ClubChampSystem, inkl. Test-Szenarien.
 * 
 * @author M.Tork
 */
public class SimpleClubChampClient {
		
		private static ClubChampService remoteSystem;
		
		/**
		 * In dieser Main-Methode werden Requests an den ClubChamp-Server abgeschickt. 
		 * @param args
		 */
		public static void main(String[] args) {
			try {
	           Context context = new InitialContext();
		       
		       //Lookup-String für eine EJB besteht aus: Name_EAR/Name_EJB-Modul/Name_EJB-Klasse!Name_RemoteInterface
		       String lookupString = "ClubChamp-System-ear/ClubChamp-System-ejb-0.0.1/ClubChampServiceBean!gruppe10.common.ClubChampService";
		       remoteSystem = (ClubChampService) context.lookup(lookupString);
	 	       
	 	       //Zeige, welche Referenz auf das Server-Objekt der Client erhalten hast:
	 	       System.out.println("Client hat folgendes Server-Objekt nach dem Lookup erhalten:");
	 	       System.out.println(remoteSystem.toString());
	 	       System.out.println();
	 	       
	 	       //Test-Szeanarien ausfuehren:
	 	       szenarioLoginLogout("michael", "123");
	 	       szenarioLoginLogout("michael", "123");
			   szenarioRegistrierung("Otto", "otto");
			}
			catch (Exception e) {				
			   	System.out.println(e);
			   	e.printStackTrace();
			}
		}
		
		/**
	     * Test-Szenario: Otto registriert sich, loggt sich ein und loggt sich aus.
	     */
		 private static void szenarioRegistrierung(String username, String passwort) {
			 try {
				   System.out.println("============================================================");			
			       boolean success = remoteSystem.signUp(username, passwort);
			       if(success){
			    	   System.out.println(username+" hat sich registriert.");
			       }
			       else{
			    	   System.out.println("Registrierung von "+username+" fehlgeschlagen.");
			       }
			 }
			 catch (SignUpFailedException e) {				
				   	System.out.println(e);
				   	//e.printS
			 }				
			catch (Exception e) {
					System.out.println(e);
					e.printStackTrace();
			 }
		}
		
		/**
	     * Test-Szenario: Login und Logout.
	     */
		private static void szenarioLoginLogout(String username, String passwort) {
			try {
			   System.out.println("============================================================");			
		       String sessionID = remoteSystem.login(username, passwort);
			   System.out.println(username+" hat sich angemeldet.");
			   System.out.println(" -> Zugewiesene SessionID: " +sessionID+".");
		       remoteSystem.logout(sessionID);
			   System.out.println(username+" hat sich abgemeldet.");
			}
			catch (LoginFailedException e) {				
			   	System.out.println(e);
			   	//e.printStackTrace();
			}
			catch (NoSessionException e) {				
			   	System.out.println(e);
			   	//e.printStackTrace();
			}
			catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
}