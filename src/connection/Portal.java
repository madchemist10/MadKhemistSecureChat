package connection;

import io.Controller;

/**
 * This is the portal for maintaining the incoming
 * and outgoing bytes of data. Maintains a connection point
 * between the controller and connection manager.
 */
public class Portal {

    /** Reference to the controller for sending data to and from
     * this portal.*/
    private Controller controller;
    /** Reference to the connection manager for reading in data
     * and sending data out.*/
    private ConnectionManager connectionManager;

    /**
     * Create a new portal for the controller to send and
     * received data from the connection manager.
     * @param controller reference to the creater of this portal.
     */
    public Portal(Controller controller){
        this.controller = controller;
        this.connectionManager = new ConnectionManager(this);
    }

    /**
     * Data needs to be sent to another client. Add
     * bytes of data to the queue where the outgoing thread
     * can process the data blocks.
     * @param bytesOut bytes to sent to another client.
     */
    public void sendOutPort(byte[] bytesOut){
        this.connectionManager.sendingBytes.add(bytesOut);
    }

    /**
     * Data has been received from the inbound port.
     * Pass these bytes to the parser through the controller
     * so the data can be decoded and presented to the user.
     * @param bytesIn bytes that are being received.
     */
    public void receiveFromPort(byte[] bytesIn){
        controller.parseIncomingBytes(bytesIn);
    }

    /**
     * Shut down this portal by removing
     * references to the connection manager and
     * the controller.
     */
    public void shutdown(){
        this.connectionManager.closeConnection();
        this.connectionManager = null;
        this.controller = null;
    }
}
