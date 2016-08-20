package launch;

import constants.Constants;
import encryption.KeyGen;
import io.Controller;
import io.Message;


/**
 * Launch the application.
 */
public class Launch {

    public static byte[] bytesOut;
    public static void main(String[] args) {
        try {
            byte[] userKey = KeyGen.generateKey("Alpha1234".getBytes());
            byte[] IV = KeyGen.generateIV();
            Controller controller = new Controller(userKey,IV);
            Message message = new Message("HelloWorld!", Constants.DATA_MESSAGE);
            controller.frameOutgoingMessage(message);
            controller.parseIncomingBytes(bytesOut);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
