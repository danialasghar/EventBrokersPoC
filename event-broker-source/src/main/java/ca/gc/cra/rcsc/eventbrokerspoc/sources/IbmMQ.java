package ca.gc.cra.rcsc.eventbrokerspoc.sources;


import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;


public class IbmMQ {
    private int instanceId;

    // System exit status value (assume unset value to be 1)
    private static int status = 1;

    // Create variables for the connection to MQ
    private String ibmMqHost;
    private int ibmMqPort;
    private String ibmMqChannel;
    private String ibmMqQmgr;
    private String ibmMqUser;
    private String ibmMqPassword;
    private String ibmMqQueueName;

    
    public IbmMQ(int instanceId) {
        this.instanceId = instanceId;

        loadConfiguration();
    }


    public void connectSend() {
        //FIXME: Should we split connect and sendMessage?

        // Variables
        JMSContext context = null;
        Destination destination = null;
        JMSProducer producer = null;



        try {
            // Create a connection factory
            System.out.println("TRYING CONNECTION");
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();

            // Set the properties
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, ibmMqHost);
            cf.setIntProperty(WMQConstants.WMQ_PORT, ibmMqPort);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, ibmMqChannel);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, ibmMqQmgr);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, ibmMqUser);
            cf.setStringProperty(WMQConstants.PASSWORD, ibmMqPassword);
            //cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "*TLS12");

            System.out.println("AFTER CONNECTION");
            // Create JMS objects
            context = cf.createContext();
            destination = context.createQueue("queue:///" + ibmMqQueueName);

            String messageText = Utils.buildMessage(instanceId);
            TextMessage message = context.createTextMessage(messageText);

            producer = context.createProducer();
            producer.send(destination, message);
            System.out.println("Sent message:\n" + message);

            context.close();

            recordSuccess();
        } catch (JMSException jmsex) {
            recordFailure(jmsex);
        }

        System.exit(status);

    }

    /**
     * Record this run as successful.
     */
    private static void recordSuccess() {
        System.out.println("SUCCESS");
        status = 0;
        return;
    }

    /**
     * Record this run as failure.
     *
     * @param ex
     */
    private static void recordFailure(Exception ex) {
        if (ex != null) {
            if (ex instanceof JMSException) {
                processJMSException((JMSException) ex);
            } else {
                System.out.println(ex);
            }
        }
        System.out.println("FAILURE");
        status = -1;
        return;
    }

    /**
     * Process a JMSException and any associated inner exceptions.
     *
     * @param jmsex
     */
    private static void processJMSException(JMSException jmsex) {
        System.out.println(jmsex);
        Throwable innerException = jmsex.getLinkedException();
        if (innerException != null) {
            System.out.println("Inner exception(s):");
        }
        while (innerException != null) {
            System.out.println(innerException);
            innerException = innerException.getCause();
        }
        return;
    }

    private void loadConfiguration() {
		ibmMqHost = Utils.getStringProperty("ibmmq.host");
        ibmMqPort = Utils.getIntProperty("ibmmq.port");
		ibmMqChannel = Utils.getStringProperty("ibmmq.channel");
		ibmMqQmgr = Utils.getStringProperty("ibmmq.qmgr");
        ibmMqUser = Utils.getStringProperty("ibmmq.user");
		ibmMqPassword = Utils.getStringProperty("ibmmq.password");
		ibmMqQueueName = Utils.getStringProperty("ibmmq.queue.name");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("IBM MQ Config");
		System.out.println("ibmMqHost=" + ibmMqHost);
		System.out.println("ibmMqPort=" + ibmMqPort);
		System.out.println("ibmMqChannel=" + ibmMqChannel);
		System.out.println("ibmMqQmgr=" + ibmMqQmgr);
		System.out.println("ibmMqUser=" + ibmMqUser);
        System.out.println("ibmMqPassword=" + ibmMqPassword);
        System.out.println("ibmMqQueueName=" + ibmMqQueueName);
	}

}