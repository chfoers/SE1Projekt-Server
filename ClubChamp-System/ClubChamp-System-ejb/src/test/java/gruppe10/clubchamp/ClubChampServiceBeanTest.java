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
 * Klasse ClubChampServiceBeanTest zum Testen der Stateless Session Bean
 * ClubChampServiceBean.
 * 
 * @deprecated ClubChampServiceBean wurde durch ClubChampWebService ersetzt.
 * 
 * @author M.Tork
 */
@RunWith(Arquillian.class)
@Deprecated
public class ClubChampServiceBeanTest {

	/*@EJB
	ClubChampService bean;
	@EJB
	MusicRegistry musicReg;
	@EJB
	ClubBewertungenRegistry clubBewertungenReg;
	@EJB
	SessionRegistry sessionReg;

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "gruppe10")
				.addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
	}

	@Test
	*//**
	 * Prueft, ob Login für Michael funktioniert.
	 * 
	 *//*
	public void loginTest() {
		try {
			String sessionId = null;
			sessionId = bean.login("michael@123.de", "123");
			if (sessionId != null) {
				bean.logout(sessionId);
				assert true;
			} else {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	*//**
	 * Prueft, ob bei ungültigem Login eine LoginFailedException kommt.
	 * 
	 *//*
	public void ungültigesLogin() {
		try {
			bean.login("michael@123.de", "falschesPasswort");
			fail();
		} catch (LoginFailedException e) {
			assert true;
		}
	}

	@Test
	*//**
	 * Prueft, ob beim Logout ohne vorherigen Login die NoSessionException
	 * geworfen wird.
	 * 
	 *//*
	public void logoutOhneLogin() {
		try {
			bean.logout(null);
			fail();
		} catch (NoSessionException e) {
			assert true;
		}
	}

	@Test
	*//**
	 * Prueft, ob die Registrierung funktioniert.
	 * 
	 *//*
	public void registrierung() {
		try {
			boolean success = false;
			String username = "TestRegUser_" + zufallszahl();
			String mail = username + "@123.de";
			success = bean.signUp(mail, username, "123");
			if (success) {
				assert true;
			} else {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}

	private int zufallszahl() {
		Random random = new Random();
		return random.nextInt(100000 - 1 + 1) + 1;
	}

	@Test
	*//**
	 * Prueft, ob bei redundanten Benutzer bei der Registrierung die
	 * SignUpFailedException geworfen wird.
	 * 
	 *//*
	public void regRedundantUser() {
		try {
			String username = "TestRegUser_" + zufallszahl();
			String mail = username + "@123.de";
			bean.signUp(mail, username, "123");
			bean.signUp(mail, username, "123");
			fail();
		} catch (SignUpFailedException e) {
			assert true;
		}
	}

	@Test
	*//**
	 * Prueft, ob das Wünschen von Musik funktioniert.
	 * 
	 *//*
	public void wuenscheMusikTest() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.musikWuenschen(sessionId, "40.Sinfonie", "Mozart");
		Music tmp = musicReg.findMusic("40.Sinfonie", "Mozart");
		if (tmp != null) {
			this.logout(sessionId);
			assert true;
		} else {
			this.logout(sessionId);
			fail();
		}
	}

	@Test
	*//**
	 * Prueft die Methode clubBewerten (String sessionId, int rating), die zum
	 * Bewerten des Clubs gebraucht wird.
	 * 
	 *//*
	public void clubBewerten() {
		int rating = 4;
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.clubBewerten(sessionId, rating);
		UserSession userSession = sessionReg.findSession(sessionId);
		User user = userSession.getUser();
		ClubBewertung clubBewertung = clubBewertungenReg.findBewertungByUser(user);
		if (clubBewertung.getRating() == 4) {
			this.logout(sessionId);
			assert true;
		} else {
			this.logout(sessionId);
			fail();
		}
	}

	@Test
	*//**
	 * Prueft die Methode musikWuenscheAusgeben
	 * 
	 *//*
	public void musikWuenscheAusgeben() {
		ArrayList<Music> musikListe = new ArrayList<Music>();
		musikListe = bean.musikWuenscheAusgeben();
		if (musikListe == null) {
			fail();
		} else {
			assert true;
		}
	}

	@Test
	*//**
	 * Prueft die Methode musikLiken
	 * 
	 *//*
	public void musikLiken() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.musikWuenschen(sessionId, "s", "a");
		this.logout(sessionId);
		Music tmp = musicReg.findMusic("s", "a");
		if (tmp.getLikes() != 0) {
			fail();
		} else {
			sessionId = this.login("hamster@123.de", "123");
			bean.musikLiken(sessionId, "s", "a");
			this.logout(sessionId);
			if (tmp.getLikes() == 1) {
				assert true;
			} else {
				fail();
			}
		}
	}

	@Test
	*//**
	 * Musik mit dem selben Benutzer doppelt "aktivieren". Musik darf nur einmal
	 * pro Benutzer gewünscht oder geliked werden.
	 * 
	 *//*
	public void musikMitSelbenBenutzerDoppeltAktivieren() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.musikWuenschen(sessionId, "s2", "a2");
		Music tmp = musicReg.findMusic("s2", "a2");
		if (tmp.getLikes() != 0) {
			this.logout(sessionId);
			fail();
		} else {
			bean.musikLiken(sessionId, "s2", "a2");
			if (tmp.getLikes() == 0) {
				this.logout(sessionId);
				assert true;
			} else {
				this.logout(sessionId);
				fail();
			}
		}
	}

	@Test
	*//**
	 * Prueft den Fall, falls ein Musikstück zweimal gewünscht wird. Statt Musik
	 * ein zweites Mal anzulegen, erhöht sich die Anzahl an Likes. Ein User kann
	 * einen Song nicht zweimal "aktivieren" (wünschen und liken)
	 * 
	 *//*
	public void musikDoppeltWuenschen_DeswegenLikeErhoehen() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.musikWuenschen(sessionId, "S", "A");
		this.logout(sessionId);
		sessionId = this.login("hamster@123.de", "123");
		bean.musikWuenschen(sessionId, "S", "A");
		this.logout(sessionId);
		Music music = musicReg.findMusic("S", "A");
		if (music.getLikes() == 1) {
			assert true;
		} else {
			fail();
		}
	}

	private String login(String username, String password) {
		String sessionId = null;
		try {
			sessionId = bean.login(username, password);
			return sessionId;
		} catch (LoginFailedException e) {
			e.printStackTrace();
			fail();
		}
		return sessionId;
	}

	private void logout(String sessionId) {
		try {
			bean.logout(sessionId);
		} catch (NoSessionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	*//**
	 * Musikstück bewerten als DJ.
	 * 
	 *//*
	public void musikBewertenAlsDJTest() {
		String sessionId = null;
		sessionId = this.login("dj@123.de", "123");
		bean.feedbackGeben(sessionId, 1, "Alle meine Entchen", "Eskuche");
		Music music = musicReg.findMusic("Alle meine Entchen", "Eskuche");
		if (music.getFeedback().equals("Musikwunsch wird bald gespielt.")) {
			assert true;
		} else {
			fail();
		}
	}

	@Test
	*//**
	 * Musik bewerten als normaler User. Sollte dem normalen User verweigert
	 * werden.
	 * 
	 *//*
	public void musikBewertenAlsNormalerUserTest() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.feedbackGeben(sessionId, 1, "Hypnotize", "Notorius BIG");
		Music music = musicReg.findMusic("Hypnotize", "Notorius BIG");
		if (music.getFeedback() == null) {
			assert true;
		} else {
			fail();
		}

	}

	@Test
	*//**
	 * Wunschliste(n) wird geleert, als normaler User.
	 * 
	 *//*
	public void wunschlistenLeerenKeinDJTest() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.clearMusicWunschliste(sessionId);
		ArrayList<Music> musikListe = musicReg.musikListeAusgeben();
		if (musikListe.isEmpty()) {
			fail();
		} else {
			assert true;
		}
	}

	@Test
	*//**
	 * Wunschliste(n) wird geleert, als DJ.
	 * 
	 *//*
	public void wunschlistenLeerenDJTest() {
		String sessionId = null;
		sessionId = this.login("dj@123.de", "123");
		bean.clearMusicWunschliste(sessionId);
		ArrayList<Music> musikListe = musicReg.musikListeAusgeben();
		if (musikListe.isEmpty()) {
			Music newMusic = new Music("Hypnotize", "Notorius BIG");
			musicReg.addMusic(newMusic);
			newMusic = new Music("Alle meine Entchen", "Eskuche");
			musicReg.addMusic(newMusic);
			assert true;
		} else {
			fail();
		}
	}

	@Test
	*//**
	 * Wenn Musikstück gespielt wurde, wird es aus den Wunschliste(n) entfernt.
	 * Nur als DJ möglich. Hier als DJ.
	 * 
	 *//*
	public void musikWurdeGespieltDJTest() {
		String sessionId = null;
		sessionId = this.login("dj@123.de", "123");
		bean.musikWurdeGespielt(sessionId, "Hypnotize", "Notorius BIG");
		if (musicReg.findMusic("Hypnotize", "Notorius BIG") == null) {
			Music newMusic = new Music("Hypnotize", "Notorius BIG");
			musicReg.addMusic(newMusic);
			assert true;
		} else {
			fail();
		}

	}

	@Test
	*//**
	 * Wenn Musikstück gespielt wurde, wird es aus den Wunschliste(n) entfernt.
	 * Nur als DJ möglich. Hier als normaler User.
	 * 
	 *//*
	public void musikWurdeGespieltKeinDJTest() {
		String sessionId = null;
		sessionId = this.login("michael@123.de", "123");
		bean.musikWurdeGespielt(sessionId, "Hypnotize", "Notorius BIG");
		if (musicReg.findMusic("Hypnotize", "Notorius BIG") == null) {
			fail();
		} else {
			assert true;
		}

	}*/

}
