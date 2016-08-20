package io;

import launch.Launch;

/**
 * This is the portal for maintaining the incoming
 * and outgoing bytes of data.
 */
public class Portal {
    private final Controller controller;

    public Portal(Controller controller){
        this.controller = controller;
    }

    public void sendOutPort(byte[] bytesOut){
        Launch.bytesOut = bytesOut;
        System.out.println(new String(bytesOut));
    }

    public void receiveFromPort(byte[] bytesIn){
        controller.parseIncomingBytes(bytesIn);
    }
}
