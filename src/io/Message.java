package io;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

/**
 * Representation of a message to be send and
 * received by the controller.
 */
public class Message implements Serializable{

    /** Message to be send with this message object.*/
    private final String message;
    /** Checksum of this message.*/
    private final String checkSum;
    /** Type of message in transfer.*/
    private final String messageType;

    /**
     * Create a new message with the user's message.
     * @param message String to send with this message.
     */
    public Message(String message, String messageType){
        this.message = message;
        this.messageType = messageType;
        this.checkSum = computeCheckSum(message);
    }

    /**
     * Compute a sha1Hex checksum for the given message.
     * @param message message to compute checksum for.
     * @return String representation of the hex string checksum.
     */
    public static String computeCheckSum(String message){
        return DigestUtils.sha1Hex(message);
    }

    /**
     * Retrieve the message contents from this message.
     * @return the message's contents.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieve the checksum computed at object creation.
     * @return this message's checksum.
     */
    public String getCheckSum() {
        return checkSum;
    }

    /**
     * Retrieve this message's message type.
     * @return message type for this message.
     */
    public String getMessageType() {
        return messageType;
    }
}
