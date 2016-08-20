package io;

import encryption.AES;
import encryption.Base64code;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * Responsible for parsing the data as it enters
 * the system. Decrypting the data so that it may
 * be presented to the user.
 */
public class Parser {

    /**Reference to the controller that created this parser.*/
    private final Controller controller;

    /**
     * Initialize this parser to handle
     * parsing of incoming bytes of data.
     */
    public Parser(Controller controller){
        this.controller = controller;
    }

    /**
     * Parse the incoming bytes by decoding the base64 encoded bytes
     * as well as decrypting the AES encryption layer.
     * Restructure a message object from the decrypted byte[] and
     * pass on to the controller.
     * @param bytesIn received bytes as they enter the system.
     * @throws Exception if parsing fails.
     */
    public void parseBytes(byte[] bytesIn) throws Exception{
        byte[] decodedBytes = Base64code.base64Decode(bytesIn);
        byte[] decryptedBytes = AES.decrypt(controller.getUserKey(), controller.getInitialValue(), decodedBytes);
        Message message = deserializePacket(decryptedBytes);
        sendToController(message);
    }

    /**
     * Send the message to the controller to be handled.
     * @param message constructed message object that has data for the user.
     */
    private void sendToController(Message message){
        this.controller.handleIncomingMessage(message);
    }

    /**DeserializePacket and build a Message object.
     * @param myBytes bytes that represent the message object.
     * @return Message Object.
     */
    private static Message deserializePacket(byte[] myBytes){
        //http://stackoverflow.com/questions/3736058/java-object-to-byte-and-byte-to-object-converter-for-tokyo-cabinet
        try{
            ByteArrayInputStream myByteArrayInStream = new ByteArrayInputStream(myBytes);
            ObjectInputStream myObjInStream = new ObjectInputStream(myByteArrayInStream);
            Object myMessage = myObjInStream.readObject();
            myByteArrayInStream.close();
            myObjInStream.close();
            return (Message) myMessage;
        }catch(Exception e){
            System.out.println(e.getClass().getName()+": "+e.getMessage());
        }
        return null;
    }

}
