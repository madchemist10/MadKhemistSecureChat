package connection;

import constants.Constants;

import java.util.concurrent.TimeUnit;

public class OutGoingDataMonitor implements Runnable {

    private final ConnectionManager connectionManager;


    OutGoingDataMonitor(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        while(true){
            if(this.connectionManager.connectionShutdown){
                break;
            }
            /*Handle outgoing data.*/
            byte[] outBytes = null;
            try {
                outBytes = this.connectionManager.sendingBytes.poll(Constants.MAX_OUT_POLL_TIME, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(outBytes != null){
                this.connectionManager.sendData(outBytes);
            }
        }
    }
}
