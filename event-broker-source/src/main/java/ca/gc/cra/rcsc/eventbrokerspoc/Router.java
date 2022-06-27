package ca.gc.cra.rcsc.eventbrokerspoc;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Router {

	/*
    private static int instanceID;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        instanceID = 0;
        printSelections();
        int selection = scan.nextInt();
        starter(selection);

        int redo = 1;
        while (redo == 1){
            System.out.println("Would you like to go again? 1: Yes, 0: No");
            int selectionAgain = scan.nextInt();
            if (selectionAgain == 1){
                printSelections();
                int selection3 = scan.nextInt();
                starter(selection);
            }
            else {
                redo = 0;
            }
        }

    }

    public static void printSelections(){
        System.out.println("Enter which broker:");
        System.out.println("1: RabbitMQ");
        System.out.println("2: AMQ Broker");
        System.out.println("3: IBM MQ");
        System.out.println("4: NATS Server");
        System.out.println("5: Solace");
    }

    public static void starter(int a){
        switch(a){
            case 1:
                Thread t = new Thread(() -> {
                    instanceID += 1;
                    RabbitMQ rabbitBroker = new RabbitMQ(instanceID);
                    rabbitBroker.sendMessage();
                });
                t.start();
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
    */
}
