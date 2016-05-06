package gruppe10.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;


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
			   szenarioMichael();		   	       
			   szenarioMichael();			   
			}
			catch (Exception e) {				
			   	System.out.println(e);
			   	e.printStackTrace();
			}
		}
		
		 /**
	     * Test-Szenario: Michael meldet sich an und wieder ab.
	     */
		private static void szenarioMichael() {
			try {
			   System.out.println("============================================================");			
		       String sessionID = remoteSystem.login("michael", "123");
			   System.out.println("Michael hat sich angemeldet.");
			   System.out.println(" -> Zugewiesene SessionID: " +sessionID+".");
			   System.out.println("Club: "+remoteSystem.getClubname());
		       remoteSystem.logout(sessionID);
			   System.out.println("Michael hat sich abgemeldet.");
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