package io;

/**
 */
public class PayloadMessage extends Message{
    /**
     * Create a new message with the user's message.
     *
     * @param message     String to send with this message.
     * @param messageType type of message.
     */
    public PayloadMessage(String message, String messageType) {
        super(message, messageType);
    }
}
