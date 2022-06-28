package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import java.util.logging.*;
import javax.jms.Destination;
import javax.jms.JMSProducer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.JMSRuntimeException;
import ca.gc.cra.rcsc.eventbrokerspoc.dependencies.ConsumerHelper;
import ca.gc.cra.rcsc.eventbrokerspoc.dependencies.ConnectionHelper;
import ca.gc.cra.rcsc.eventbrokerspoc.dependencies.LoggingHelper;

public class IbmMQ {
    private static final Logger logger = Logger.getLogger("com.ibm.mq.samples.jms");

    public static final String PRODUCER_PUT = "queue";
    public static final String PRODUCER_PUB = "topic";

    private JMSContext context = null;
    private Destination destination = null;
    private JMSProducer producer = null;
    private ConnectionHelper ch = null;

    public IbmMQ(String type) {
        String id = null;

        switch(type){
            case PRODUCER_PUT :
                id = "Basic put";
                break;
            case PRODUCER_PUB :
                id = "Basic pub";
                break;
        }

        LoggingHelper.init(logger);
        logger.info("Sub application is starting");

        ch = new ConnectionHelper(id, ConnectionHelper.USE_CONNECTION_STRING);
        logger.info("created connection factory");

        context = ch.getContext();
        logger.info("context created");

        switch(type){
            case PRODUCER_PUB :
                destination = ch.getTopicDestination();
                break;
            case PRODUCER_PUT :
                destination = ch.getDestination();
                break;
        }

        // Set so no JMS headers are sent.
        ch.setTargetClient(destination);

        logger.info("destination created");

        producer = context.createProducer();
    }

    public void send(String message, int n_messages) {
        for (int i = 0; i < n_messages; i++) {
            logger.info("Publishing messages.\n");

            try {
                producer.send(destination, message);
                logger.info("message was sent");
                Thread.sleep(2000);
            } catch (JMSRuntimeException jmsex) {
                jmsex.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public void close() {
        ch.closeContext();
        ch = null;
    }
}
