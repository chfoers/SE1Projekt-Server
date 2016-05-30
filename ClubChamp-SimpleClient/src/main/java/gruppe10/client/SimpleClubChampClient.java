package gruppe10.client;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.common.SignUpFailedException;
import gruppe10.musik.Music;


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
			   szenarioMusikWunsch("40.Sinfonie","Mozart");
			   szenarioClubBewertung(4);
			   szenarioMusikListeAnzeigen();
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
		  * Test-Szenario: Musikliste zurückgeben.
		  */
		 private static void szenarioMusikListeAnzeigen() {
			 System.out.println("============================================================");	
			 ArrayList<Music> musikListe = new ArrayList<Music>();
			 musikListe = remoteSystem.musikWuenscheAusgeben(); //musikWuenscheAusgeben();
			 System.out.println("Aktuell Wunschliste des Clubs:");
			 for(Music tmp: musikListe){
				 System.out.println(" "+tmp);
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
		
		/**
	     * Test-Szenario: Musikwunsch.
	     */
		 private static void szenarioMusikWunsch(String song, String artist) {
			 System.out.println("============================================================");	
			 remoteSystem.musikWuenschen(song, artist);
			 System.out.println("Musik[Song="+song+",Artist=" +artist+ "] wurde gewünscht.");
		}
		 
		 /**
		  * Test-Szenario: Bewertung des Clubs.
		  */
		 private static void szenarioClubBewertung(int rating) {
			 System.out.println("============================================================");	
			 String sessionID = null;
					try {
						sessionID = remoteSystem.login("michael", "123");
					} catch (LoginFailedException e) {
						System.out.println(e);	
					} catch (Exception e) {
						System.out.println(e);
					}					
			  remoteSystem.clubBewerten(sessionID, rating);
			  System.out.println("Clubbewertung angelegt: ["+rating+"].");
					try {
						 remoteSystem.logout(sessionID);
					} catch (NoSessionException e) {
						e.printStackTrace();
					}
					   
			}
}