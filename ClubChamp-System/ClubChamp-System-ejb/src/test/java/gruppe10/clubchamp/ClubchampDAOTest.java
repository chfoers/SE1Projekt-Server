package gruppe10.clubchamp;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import gruppe10.dao.ClubchampDAOLocal;
import gruppe10.entities.Music;



@RunWith(Arquillian.class)
public class ClubchampDAOTest {
	
	@EJB
	ClubchampDAOLocal dao;
	
	/**
	* Prueft, ob nach dem Startup ein Musikstück Titel:"Alle meine Entchen" vom DAO gefunden wird.
	*/

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "gruppe10")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
	}
	/**
	* Prueft, ob nach dem Startup ein Musikstück Titel:"Alle meine Entchen" vom DAO gefunden wird.
	*/
	@Test
	public void test1() {
	Music music = dao.findMusic("Alle meine Entchen","Eskuche");
	assert music!=null : "Alle meine Entchen nicht gefunden.";
	assert music.getSong().equals("Alle meine Freunde") : "Song Name ist falsch.";
	
	

	}
}
	