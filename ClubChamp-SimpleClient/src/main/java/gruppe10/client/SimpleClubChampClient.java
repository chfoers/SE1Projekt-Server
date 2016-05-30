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
	 	       szenarioLoginLogout();
	 	       szenarioLoginLogout();
			   szenarioRegistrierung();
			   szenarioMusikWunsch("40.Sinfonie","Mozart");
			   szenarioClubBewertung(4);
			   szenarioMusikListeAnzeigen();
			   szenarioMusikLiken("40.Sinfonie","Mozart");
			   szenarioMusikDoppelt("40.Sinfonie","Mozart");
			   szenarioMusikListeAnzeigen();
			   
			   //logins und logouts auslagern
			} catch (Exception e) {				
			   	System.out.println(e);
			}
		}
			
		/**
	     * Test-Szenario: Benutzer Michael - Login und Logout.
	     */
		private static void szenarioLoginLogout() {
			try {
			   System.out.println("============================================================");	
			   System.out.println("**szenarioLoginLogout**");	
			   String mail = "michael@123.de";
			   String passwort = "123";
		       String sessionID = remoteSystem.login(mail, passwort);
			   System.out.println(mail+" hat sich angemeldet.");
			   System.out.println(" -> Zugewiesene SessionID: " +sessionID+".");
		       remoteSystem.logout(sessionID);
			   System.out.println(mail+" hat sich abgemeldet.");
			} catch (LoginFailedException e) {				
			   	System.out.println(e);
			} catch (NoSessionException e) {				
			   	System.out.println(e);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/**
	     * Test-Szenario: Otto registriert sich.
	     */
		 private static void szenarioRegistrierung() {
			 System.out.println("============================================================");
			 System.out.println("**szenarioRegistrierung**");
			 String mail = "otto@123.de";
			 String userName = "otto";
			 String favGenre = "Hip Hop";
			 String passwort = "123";
			 try {	
			     boolean success = remoteSystem.signUp(mail, userName, passwort, favGenre);
			     if(success){
			    	 System.out.println(mail+" hat sich registriert.");			    	 
			     } else {
			    	 System.out.println("Registrierung von "+mail+" fehlgeschlagen.");
			     }
			 } catch (SignUpFailedException e) {	
				 System.out.println(e);
			 } catch (Exception e) {
				System.out.println(e);
			 }
		}		
			 
		 /**
		  * Test-Szenario: Musikwunsch.
		  */
		 private static void szenarioMusikWunsch(String song, String artist) {			 
			System.out.println("============================================================");
			System.out.println("**szenarioMusikWunsch**");
			String sessionId = login("michael@123.de", "123");
			remoteSystem.musikWuenschen(sessionId, song, artist);
			System.out.println("Musik[Song="+song+",Artist=" +artist+ "] wurde gewünscht.");
			logout(sessionId);
			}
		 
		 /**
		  * Test-Szenario: Bewertung des Clubs.
		  */
		 private static void szenarioClubBewertung(int rating) {
			 System.out.println("============================================================");
			 System.out.println("**szenarioClubBewertung**");
			 String sessionId = login("michael@123.de", "123");
			 remoteSystem.clubBewerten(sessionId, rating);
			 System.out.println("Clubbewertung angelegt: ["+rating+"].");
			 logout(sessionId);					   
			}
		 
		 /**
		  * Test-Szenario: Musikliste zurückgeben.
		  */
		 private static void szenarioMusikListeAnzeigen() {
			 System.out.println("============================================================");
			 System.out.println("**szenarioMusikListeAnzeigen**");
			 ArrayList<Music> musikListe = new ArrayList<Music>();
			 musikListe = remoteSystem.musikWuenscheAusgeben(); 
			 System.out.println("Aktuell Wunschliste des Clubs:");
			 for(Music tmp: musikListe){
				 System.out.println(" "+tmp);
			 }					   
		}
			 
		/**
	     * Test-Szenario: Musiksong liken
	     */
		 private static void szenarioMusikLiken(String song, String artist) {
			 System.out.println("============================================================");
			 System.out.println("**szenarioMusikLiken**");
			 String sessionId = login("hamster@123.de", "123");			 
			 String success = remoteSystem.musikLiken(sessionId, song, artist);	
			 System.out.println(success);
			 logout(sessionId);			 
		 }
		 
		/**
		 * Test-Szenario: Erneutes Anlegen eines Musikstückes, welches schon vorhanden ist. Likes+1
		 */
		private static void szenarioMusikDoppelt(String song, String artist) {
			 System.out.println("============================================================");
			 System.out.println("**szenarioMusikDoppelt**");
			 String sessionId = login("michael@123.de", "123");
			 String success = remoteSystem.musikWuenschen(sessionId, song, artist);
			 System.out.println(success);
			 logout(sessionId);
		} 
		
		private static String login(String mail, String passwort){
			 String sessionId = null;
			 try {
				sessionId = remoteSystem.login(mail, passwort);
			 } catch (LoginFailedException e) {
				System.out.println(e);
			 }
			 return sessionId;
		 }
		 
		 private static void logout(String sessionId){
			 try {
				remoteSystem.logout(sessionId);
			 } catch (NoSessionException e) {
				System.out.println(e);
			 } catch (Exception e) {
				System.out.println(e);
			}
		 } 
		 
}