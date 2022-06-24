import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestService {

	@GET
    @Path("/rabbitmq")
    public void testRabbitMq() {
    	RabbitMQ rabbitMQ = new RabbitMQ(1);

    	rabbitMQ.sendMessage();
    }

	@GET
    @Path("/solace")
    public void testSolace() {
    	SolacePubSub solace = new SolacePubSub(2);

    	solace.sendMessage();
    }
        
}