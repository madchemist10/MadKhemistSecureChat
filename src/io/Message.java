package io;

import java.io.Serializable;

/**
 * Representation of a message to be send and
 * received by the controller.
 */
public abstract class Message implements Serializable{

    /** Message to be send with this message object.*/
    private final String message;
    /** Type of message in transfer.*/
    private final String messageType;

    /**
     * Create a new message with the user's message.
     * @param message String to send with this message.
     * @param messageType type of message.
     */
    protected Message(String message, String messageType){
        this.message = message;
        this.messageType = messageType;
    }

    /**
     * Retrieve the message contents from this message.
     * @return the message's contents.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieve this message's message type.
     * @return message type for this message.
     */
    public String getMessageType() {
        return messageType;
    }
}
