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
}
