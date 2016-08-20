package launch;


import connection.ConnectionManager;
import constants.Constants;
import encryption.KeyGen;
import io.Controller;
import io.Message;
import io.PayloadMessage;

import java.util.Scanner;

/**
 * Launch the application.
 */
public class Launch {

    public static void main(String[] args) {
        try {
            Scanner userInput = new Scanner(System.in);
            System.out.print("INBOUND_PORT = ");
            String inBoundPort = userInput.nextLine();
            int receivePort = Integer.parseInt(inBoundPort);
            System.out.print("OUTBOUND_PORT = ");
            String outBoundPort = userInput.nextLine();
            int sendPort = Integer.parseInt(outBoundPort);
            ConnectionManager.setPorts(receivePort, sendPort);
            byte[] userKey = KeyGen.generateKey("Alpha1234".getBytes());
            byte[] IV = KeyGen.generateIV();
            Controller controller = new Controller(userKey,IV);
            while(true){
                System.out.print("Input>> ");
                String userString = userInput.nextLine();
                if(userString.equals("EXIT")){
                    break;
                }
                Message message = new PayloadMessage(userString, Constants.DATA_MESSAGE);
                controller.frameOutgoingMessage(message);
            }
            controller.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
