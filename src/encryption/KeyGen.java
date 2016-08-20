package encryption;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generator a secret key for
 * use in the encryption module.
 */
public class KeyGen {

    /**
     * Generate a secret key for use in encryption and decryption.
     * @param userBytes user's specified key for key generation.
     * @return SecretKey for encryption and decryption.
     * @throws Exception when secret could not be generated.
     */
    public static byte[] generateKey(byte[] userBytes) throws Exception{
        byte[] salt = generateSalt(16-userBytes.length);
        byte[] phrase = new byte[userBytes.length + salt.length];
        System.arraycopy(userBytes, 0, phrase, 0, userBytes.length);
        System.arraycopy(salt, 0, phrase, userBytes.length, salt.length);
        return phrase;
    }

    /**
     * Generate a salt for this secret key.
     * @return byte[] of the generated salt.
     * @throws Exception if salt generation failed.
     */
    private static byte[] generateSalt(int size) throws Exception{
        Random random = new SecureRandom();
        byte[] salt = new byte[size];
        random.nextBytes(salt);
        return salt;
    }
}
