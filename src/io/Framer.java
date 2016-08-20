package io;

import constants.Constants;
import encryption.AES;
import encryption.Base64code;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Frames the outgoing data so that the
 * user can send encrypted data to another
 * user.
 */
public class Framer {

    /**Reference to the controller that created this framer.*/
    private Controller controller;

    /**
     * Initialize this parser to handle
     * parsing of incoming bytes of data.
     */
    public Framer(Controller controller){
        this.controller = controller;
    }

    /**
     * Shutdown this framer by removing reference to
     * the controller to allow for cleanup.
     */
    public void shutdown(){
        this.controller = null;
    }

    /**
     * Frame a message into a byte[] to be sent from this system.
     * @param message message to be encrypted and encoded.
     * @throws Exception if message framing fails.
     */
    public void frameMessage(Message message) throws Exception{
        if(message == null){
            System.err.println("Message to be framed is null.");
            return;
        }
        /*Serialize the payload message.*/
        byte[] serializedMessage = serializePacket(message);
        byte[] encryptedMessage = AES.encrypt(controller.getUserKey(), controller.getInitialValue(), serializedMessage);
        byte[] encodedMessage = Base64code.base64Encode(encryptedMessage);

        /*Create a checksum message.*/
        ChecksumMessage checksumMessage = new ChecksumMessage(encodedMessage);
        byte[] serializedChecksumMessage = serializeChecksumPacket(checksumMessage);
        byte[] encodedChecksumMessage = Base64code.base64Encode(serializedChecksumMessage);

        /*Send encoded checksum message to the controller to be sent
        * to the client.*/
        sendToController(encodedChecksumMessage);
    }


    /**
     * Send the message to the controller to be handled.
     * @param bytesOut bytes to be sent out to the recipient.
     */
    private void sendToController(byte[] bytesOut){
        this.controller.handleOutgoingBytes(bytesOut);
    }

    /**SerializePacket to be sent from this system.
     * @param message message object to be serialized.
     * @return byte[] representation of the serialized message.
     */
    public static byte[] serializePacket(Message message){
        //http://stackoverflow.com/questions/17940423/send-object-over-udp-in-java
        try{
            ByteArrayOutputStream myByteArrayOutStream = new ByteArrayOutputStream(Constants.MESSAGE_OBJ_SIZE);
            ObjectOutputStream myObjOutStream = new ObjectOutputStream(myByteArrayOutStream);
            myObjOutStream.writeObject(message);
            myObjOutStream.close();
            byte[] myObjInBytes = myByteArrayOutStream.toByteArray();
            myByteArrayOutStream.close();
            return myObjInBytes;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getClass().getName()+": "+e.getMessage());
        }
        return null;
    }

    /**SerializePacket to be sent from this system.
     * @param message message object to be serialized.
     * @return byte[] representation of the serialized message.
     */
    public static byte[] serializeChecksumPacket(ChecksumMessage message){
        //http://stackoverflow.com/questions/17940423/send-object-over-udp-in-java
        try{
            ByteArrayOutputStream myByteArrayOutStream = new ByteArrayOutputStream(Constants.MESSAGE_OBJ_SIZE);
            ObjectOutputStream myObjOutStream = new ObjectOutputStream(myByteArrayOutStream);
            myObjOutStream.writeObject(message);
            myObjOutStream.close();
            byte[] myObjInBytes = myByteArrayOutStream.toByteArray();
            myByteArrayOutStream.close();
            return myObjInBytes;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getClass().getName()+": "+e.getMessage());
        }
        return null;
    }
}
