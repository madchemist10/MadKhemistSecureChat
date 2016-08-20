package connection;

import constants.Constants;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {

    private final Portal portal;

    boolean connectionShutdown = false;

    DatagramSocket sendingSocket;
    DatagramSocket receivingSocket;

    private static int receivingPort = Constants.DEFAULT_RECEIVING_PORT;
    private static int sendingPort = Constants.DEFAULT_SENDING_PORT;
    private final String hostName = Constants.DEFAULT_IP_ADDRESS;

    BlockingQueue<byte[]> sendingBytes = new ArrayBlockingQueue<>(10);

    public ConnectionManager(Portal portal){
        this.portal = portal;
        openConnection();
        startConnectionMonitor();
    }

    private void startConnectionMonitor(){
        Thread outGoingMonitor = new Thread(new OutGoingDataMonitor(this));
        outGoingMonitor.setName("OutGoingDataMonitor");
        outGoingMonitor.start();

        Thread incomingMonitor = new Thread(new IncomingDataMonitor(this));
        incomingMonitor.setName("IncomingDataMonitor");
        incomingMonitor.start();
    }

    private void openConnection(){
        try {
            this.receivingSocket = new DatagramSocket(receivingPort);
            this.sendingSocket = new DatagramSocket();
            this.sendingSocket.connect(InetAddress.getByName(this.hostName),sendingPort);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void closeConnection(){
        connectionShutdown = true;
        this.sendingSocket.close();
        this.receivingSocket.close();
    }

    void receiveData(byte[] bytesIn){
        this.portal.receiveFromPort(bytesIn);
    }

    void sendData(byte[] bytesOut){
        DatagramPacket outPacket = buildDatagramPacket(bytesOut);
        try {
            sendingSocket.send(outPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DatagramPacket buildDatagramPacket(byte[] bytes){
        return new DatagramPacket(bytes, bytes.length);
    }

    public static void setPorts(int receivingPort, int sendingPort){
        ConnectionManager.receivingPort = receivingPort;
        ConnectionManager.sendingPort = sendingPort;
    }

}
