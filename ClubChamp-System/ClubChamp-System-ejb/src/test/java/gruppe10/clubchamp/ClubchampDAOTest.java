/**
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

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "de/xbank")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
	

/**
* 

	
	@Test
	public void test1() throws Exception {
	Music music = dao.findMusic("Alle meine Entchen","Eskuche");
	assert music!=null : "Alle meine Entchen nicht gefunden.";
	assert music.getPassword().equals("mus") : "Emmas Passwort ist falsch.";
	assert emma.getAccounts().size()==2 : "Emma hat nicht 2 Konten.";
	}
	*/