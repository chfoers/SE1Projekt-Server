package gruppe10.output;

import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.jboss.logging.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/Queue1"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "DocType LIKE 'Letter'") })
public class OutputRequestProcessor implements MessageListener {
	
	private static final Logger logger = Logger.getLogger(OutputRequestProcessor.class);

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			logger.info("Received message from jms/queue/Queue1: " + msg.getText());
		} catch (JMSException e) {
			throw new EJBException(e);
		}
	
	
	
	Properties mprops = new Properties();
	mprops.put("mail.transport.protocol", "smtp");
	mprops.put("mail.smtp.host", "smtp.sun.com");
	
	//HoleSessionInformation
	//Erzeugt eine mail session zwischen dem Mailclient und dem Mailserver
	Session session = Session.getDefaultInstance(mprops, null);
	try {
	String mail = null;
	
	MimeMessage msg = new MimeMessage(session);
	
	msg.setFrom(new InternetAddress("tempest1993@gmx.de"));
	msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail));
	msg.setSubject("Bestätigung");
	msg.setText("Danke für Ihre Regirstrierung, Herzlich Wilkommen");
	
	Transport.send(msg);
	
	logger.info("Bestaetigungsmail an "+mail+" versendet!");
	} catch (MessagingException e) {
	throw new RuntimeException(e);
	}
	}
}
