package connection;

import constants.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.TimeUnit;

public class IncomingDataMonitor implements Runnable {

    private final ConnectionManager connectionManager;

    IncomingDataMonitor(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        while(true){
            if(this.connectionManager.connectionShutdown){
                break;
            }
            byte[] inBytes = new byte[Constants.MESSAGE_OBJ_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(inBytes, inBytes.length);
            try {
                this.connectionManager.receivingSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] receivedData = receivePacket.getData();
            this.connectionManager.receiveData(receivedData);
        }
    }
}
