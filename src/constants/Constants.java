package constants;

/**
 * Constant file for constants used throughout
 * this secure chat application.
 */
public interface Constants {

    /** Encryption algorithm to be used in the encryption module.*/
    String AES = "AES";

    /** Encryption mode for encrypt and decrypt actions.*/
    String AES_CBC_PKCS = "AES/CBC/PKCS5Padding";

    /** Size of serialized message object.*/
    int MESSAGE_OBJ_SIZE = 2048;

    /*Message types*/
    String DATA_MESSAGE = "DATA_MESSAGE";

    /** Default port to listen for connection on.*/
    int DEFAULT_RECEIVING_PORT = 2500;

    /** Default port to send data over.*/
    int DEFAULT_SENDING_PORT = 4000;

    /** Home as default ip address.*/
    String DEFAULT_IP_ADDRESS = "127.0.0.1";

    /** Maximum time to wait on polling for outgoing data.*/
    int MAX_OUT_POLL_TIME = 500;
}
