package gruppe10.clubchamp;

import static org.junit.Assert.fail;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import gruppe10.common.ClubChampService;
import gruppe10.common.LoginFailedException;
import gruppe10.common.NoSessionException;
import gruppe10.session.SessionRegistry;

@RunWith(Arquillian.class)
public class ClubChampServiceBeanTest {

	@EJB
	ClubChampService bean;
	
	@Deployment
    public static WebArchive createDeployment() {
    	return ShrinkWrap.create(WebArchive.class, "test.war")
               .addPackages(true,"gruppe10")
               .addClass(SessionRegistry.class)
               .addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml");
    }

	@Test
	/**
	 * Prueft, ob Login für Michael funktioniert.
	 * @throws LoginFailedException
	 */
	public void loginTestMichael() throws LoginFailedException{
		try{
			String login = bean.login("michael", "123");
				System.out.println(login);
		}catch(LoginFailedException e){
			fail();
		}
		
	}

	@Test
	/**
	 * Prueft, ob bei ungültigem Login eine LoginFailedException kommt.
	 * @throws LoginFailedException
	 */
	public void ungültigesLogin() throws LoginFailedException{
		try {
			String login = bean.login("michael", "falschesPasswort");
			fail();
		} catch (LoginFailedException e) {
			//LoginFailedException
			assert true;
		}
	}
	
	@Test
	/**
	 * Prueft, ob beim Logout ohne vorherigen Login die NoSessionException geworfen wird.
	 * @throwsNoSessionException 
	 */
	public void logoutOhneLogin() {
		try {
			bean.logout(null);
			fail();			
		} catch (NoSessionException e) {
			assert true;
		}
	}
	
}
