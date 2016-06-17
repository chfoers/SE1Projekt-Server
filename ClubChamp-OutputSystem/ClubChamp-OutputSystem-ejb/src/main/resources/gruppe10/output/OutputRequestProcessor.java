package gruppe10.output;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.logging.Logger;

/**
 * Message-Driven Bean implementation class for: OutputRequestProcessor
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms/queue/Queue1"),
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
				propertyName ="messageSelector",
				propertyValie ="DocType LIKE 'Letter'")
		})
		
public class OutputRequestProcessor implements MessageListener {

    private static final Logger logger = Logger.getLogger(OutputRequestProcessor.class);
    
    @Override
    public void onMessage(Message message) {
        try{
        	TestMessage msg = (TextMessage) message;
        	logger.info("Received message from jms/queue/Queue1:" + msg.getText());
        }
        catch (JMSException e){
        	throw new EJBException(e);
        }
        }
    }


