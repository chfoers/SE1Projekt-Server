package gruppe10.clubchamp;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import gruppe10.club.ClubBewertung;
import gruppe10.club.ClubBewertungenRegistry;
import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.common.SignUpFailedException;
import gruppe10.musik.Music;
import gruppe10.musik.MusicRegistry;
import gruppe10.session.SessionRegistry;
import gruppe10.session.UserSession;
import gruppe10.user.User;

/**
* Klasse ClubChampServiceBeanTest zum Testen der Stateless Session Bean ClubChampServiceBean.
* 
* @author M.Tork
*/
@RunWith(Arquillian.class)
public class ClubChampServiceBeanTest {

	@EJB
	ClubChampService bean;
	@EJB
	MusicRegistry musicReg;
	@EJB
	ClubBewertungenRegistry clubBewertungenReg;
	@EJB
	SessionRegistry sessionReg;
	
	@Deployment
    public static WebArchive createDeployment() {
    	return ShrinkWrap.create(WebArchive.class, "test.war")
    			 .addPackages(true, "gruppe10")
                 .addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
    }

	 @Test
	/**
	 * Prueft, ob Login für Michael funktioniert.
	 * 
	 */
	public void loginTest(){
		try{
			String sessionid = null;
			sessionid = bean.login("michael", "123");
			if(sessionid!=null){
				assert true;
			}else{
				fail();
			}
		}catch(Exception e){
			fail();
		}		
	}

	@Test
	/**
	 * Prueft, ob bei ungültigem Login eine LoginFailedException kommt.
	 * 
	 */
	public void ungültigesLogin(){
		try {
			String login = null;
			login = bean.login("michael", "falschesPasswort");
			fail();
		} catch (LoginFailedException e) {
			assert true;
		}
	}
	
	@Test
	/**
	 * Prueft, ob beim Logout ohne vorherigen Login die NoSessionException geworfen wird.
	 * 
	 */
	public void logoutOhneLogin() {
		try {
			bean.logout(null);
			fail();			
		} catch (NoSessionException e) {
			assert true;
		}
	}
	
	@Test
	/**
	 * Prueft, ob die Registrierung funktioniert.
	 *  
	 */
	public void Registrierung() {
		try {
			boolean success = false;
			String username = "TestRegUser_" + zufallszahl();
			success = bean.signUp(username, "passwort");
			if(success){
				assert true;
			} else {
				fail();
			}					
		} catch (Exception e) {
			fail();
		}
	}
	
	private int zufallszahl(){
		Random random = new Random();
		return random.nextInt(100000-1+1)+1;
	}
	
	@Test
	/**
	 * Prueft, ob bei redundanten Benutzer bei der Registrierung die SignUpFailedException geworfen wird.
	 * 
	 */
	public void RegRedundantUser() {
		try {
			String username = "TestRegUser_" + zufallszahl();
			bean.signUp(username, "passwort");
			bean.signUp(username, "passwort");		
			fail();
		} catch (SignUpFailedException e) {
			assert true;
		}
	}
	
	@Test
	/**
	 * Prueft, ob das Wünschen von Musik funktioniert.
	 * 
	 */
	public void wünscheMusikTest() {
		bean.musikWuenschen("40.Sinfonie","Mozart");
		Music tmp = musicReg.findMusic("40.Sinfonie","Mozart");
		if(tmp != null){
			assert true;
		} else{
			fail();
		}
		
	}
	
	@Test
	/**
	 * Prueft die Methode clubBewerten (String sessionId, int rating), die zum Bewerten des Clubs gebraucht wird.
	 * 
	 */
	public void clubBewerten() {
		int rating = 4;
		String sessionId = null;
		try {
			sessionId = bean.login("michael", "123");
		} catch (LoginFailedException e) {
			fail();
		}
		bean.clubBewerten(sessionId, rating);
		UserSession userSession = sessionReg.findSession(sessionId);
		User user = userSession.getUser();		
		ClubBewertung clubBewertung = clubBewertungenReg.findBewertungByUser(user);
		if(clubBewertung.getRating()==4){
			assert true;
		} else {
			fail();
		}
	}
	
	@Test
	/**
	 * Prueft die Methode musikWuenscheAusgeben
	 * 
	 */
	public void musikWuenscheAusgeben() {
		ArrayList<Music> musikListe = new ArrayList<Music>();
		musikListe = bean.musikWuenscheAusgeben();
		if(musikListe==null){
			fail();
		} else {
			assert true;
		}
	}
	
}
