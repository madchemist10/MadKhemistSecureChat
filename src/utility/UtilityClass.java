package utility;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Contains utility methods used throughout code.
 */
public class UtilityClass {
    /**
     * Compute a sha1Hex checksum for the given message.
     * @param message message to compute checksum for.
     * @return String representation of the hex string checksum.
     */
    public static String computeCheckSum(byte[] message){
        return DigestUtils.sha1Hex(message);
    }
}
