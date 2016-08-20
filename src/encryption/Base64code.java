package encryption;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64 encode and decode methods.
 */
public class Base64code {

    /**
     * Base 64 decode the bytes given.
     * @param bytesIn bytes to decode.
     * @return Base64code decoded string.
     */
    public static byte[] base64Decode(byte[] bytesIn){
        return Base64.decodeBase64(bytesIn);
    }

    /**
     * Base 64 encode the bytes given.
     * @param bytesIn bytes to encode.
     * @return Base64code encoded string.
     */
    public static byte[] base64Encode(byte[] bytesIn){
        return Base64.encodeBase64(bytesIn);
    }
}
