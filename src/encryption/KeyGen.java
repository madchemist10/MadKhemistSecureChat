package encryption;

/**
 * Generator a secret key for
 * use in the encryption module.
 */
public class KeyGen {

    /** Pre-defined salt for using with cipher generations.*/
    private final static byte[] PRE_DEF_SALT = "KqxmrXcIOkJSET1NNkpt".getBytes();

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
        byte[] salt = new byte[size];
        /*Copy from the Pre-defined salt*/
        System.arraycopy(PRE_DEF_SALT, 0, salt, 0, size);
        return salt;
    }

    /**
     * Wrapper for creating an initial value to seed the cipher with.
     * @return byte[] representation of the generated IV.
     * @throws Exception if IV creation fails.
     */
    public static byte[] generateIV() throws Exception{
        return generateSalt(16);
    }
}
