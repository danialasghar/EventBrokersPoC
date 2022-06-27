import java.util.logging.*;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class ConsumerHelper {
    private static final Logger logger = Logger.getLogger("com.ibm.mq.samples.jms");

    public ConsumerHelper(Message receivedMessage){
        if (receivedMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) receivedMessage;
            try {
                logger.info("Received message: " + textMessage.getText());
            } catch (JMSException jmsex) {
                recordFailure(jmsex);
            }
        } else if (receivedMessage instanceof Message) {
            logger.info("Message received was not of type TextMessage.\n");
        } else {
            logger.info("Received object not of JMS Message type!\n");
        }
    }

    private void recordFailure(Exception ex) {
        if (ex != null) {
            if (ex instanceof JMSException) {
                processJMSException((JMSException) ex);
            } else {
                logger.warning(ex.getMessage());
            }
        }
        logger.info("FAILURE");
    }

    private void processJMSException(JMSException jmsex) {
        logger.warning(jmsex.getMessage());
        Throwable innerException = jmsex.getLinkedException();
        logger.warning("Exception is: " + jmsex);
        if (innerException != null) {
            logger.warning("Inner exception(s):");
        }
        while (innerException != null) {
            logger.warning(innerException.getMessage());
            innerException = innerException.getCause();
        }
    }

}