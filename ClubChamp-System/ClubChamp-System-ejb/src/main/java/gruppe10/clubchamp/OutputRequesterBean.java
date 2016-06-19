package gruppe10.clubchamp;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.*;

/**
 * OutputRequesterBean Stateless Session Bean, eine Methode zur Überbringung
 * Strings die Queue "Queue1".Dient zur Führung des Logs. E-Mail versand auch
 * 
 * @author Chrisitan Förster
 */

@Stateless
@LocalBean
public class OutputRequesterBean {

	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory jmsFactory;

	@Resource(mappedName = "java:/jms/queue/Queue1")
	private Queue outputQueue;

	public void printLetter(String letter) {

		try (JMSContext context = jmsFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
			TextMessage message = context.createTextMessage();
			message.setStringProperty("DocType", "Letter");
			message.setText(letter);
			context.createProducer().send(outputQueue, message);
		} catch (JMSException e) {
			throw new EJBException(e);
		}
	}
}
