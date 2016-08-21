package io;

import connection.Portal;
import constants.Constants;
import gui.MainFrame;

/**
 * Responsible for maintaining the in and out of bytes
 * of data to be parsed and framed in
 * the io module.
 */
public class Controller {
    /**Byte[] of the user specified key.*/
    private final byte[] userKey;
    /**Byte[] of the initial value to be used in the cipher.*/
    private final byte[] initialValue;
    /**Reference to the parser created by this controller.*/
    private final Parser parser;
    /**Reference to the portal that will handle socket transactions.*/
    private final Portal portal;
    /**Reference to the framer created by this controller.*/
    private final Framer framer;
    /**Reference to main frame so that incoming data can be displayed.*/
    private final MainFrame frame;

    /**
     * Create a new controller for this application.
     * @param userKey byte[] of the user's key.
     *                Must be 128 bits. 16 bytes.
     * @param initialValue byte[] of the initial value for the cipher.
     *                     Must be 16 bytes.
     */
    public Controller(byte[] userKey, byte[] initialValue, MainFrame frame){
        this.userKey = userKey;
        this.initialValue = initialValue;
        this.parser = new Parser(this);
        this.framer = new Framer(this);
        this.portal = new Portal(this);
        this.frame = frame;
    }

    /**
     * Shutdown this controller by shutting
     * down each of the portal, parser, and framer.
     */
    public void shutdown(){
        this.portal.shutdown();
        this.framer.shutdown();
        this.parser.shutdown();
    }

    /**
     * Parse bytes as they are sent to us by the portal.
     * @param bytesIn bytes to be parsed.
     */
    public void parseIncomingBytes(byte[] bytesIn){
        try {
            this.parser.parseBytes(bytesIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Frame a message to leave the system by transferring
     * the byte representation of the message to the portal.
     * @param message message to be sent.
     */
    public void frameOutgoingMessage(Message message){
        try {
            displayMessageToUser("\nSent>> "+message.getMessage());
            this.framer.frameMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process and handle incoming message after it has
     * been parsed.
     * @param message message that has been processed.
     */
    public void handleIncomingMessage(Message message){
        if(message == null){
            System.err.println("Message received is null.");
            return;
        }
        String messageType = message.getMessageType();
        switch(messageType){
            case Constants.DATA_MESSAGE:
                displayMessageToUser("\nReceived>> "+message.getMessage());
                break;
        }
    }

    /**
     * Process and handle outgoing bytes by passing them to the portal.
     * @param bytesOut bytes to be sent from this system.
     */
    public void handleOutgoingBytes(byte[] bytesOut){
        this.portal.sendOutPort(bytesOut);
    }

    /**
     * Retrieve the user specified encryption key.
     * @return this controller's user key for security.
     */
    public byte[] getUserKey() {
        return userKey;
    }

    /**
     * Retrieve the user specified initial value for the ciphers.
     * @return this controller's initial value for security.
     */
    public byte[] getInitialValue() {
        return initialValue;
    }

    /**
     * Display a message to the user.
     * @param message message to show the user.
     */
    public void displayMessageToUser(String message){
        frame.getUserDialog().addDataToConversation(message);
    }
}
