import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Router {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter which broker:");
        System.out.println("1: RabbitMQ");
        System.out.println("2: AMQ Broker");
        System.out.println("3: IBM MQ");
        System.out.println("4: NATS Server");
        System.out.println("5: Solace");

        int selection = scan.nextInt();

        switch(selection){
            case 1:
                RabbitMQ rabbitBroker = new RabbitMQ();
                rabbitBroker.SendMessage();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}
