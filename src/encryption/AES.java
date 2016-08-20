package encryption;

import constants.Constants;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES module for encryption and decryption.
 */
public class AES{

    /**
     * Encrypt a given set of data based on the initial value and key given.
     * @param keyBytes byte[] representation of the key.
     * @param ivBytes byte[] representation of the initial value for the cipher.
     * @param messageBytes byte[] representation of the message bytes.
     * @return byte[] of encrypted bytes.
     * @throws Exception if encryption fails.
     */
    public static byte[] encrypt(final byte[] keyBytes, final byte[] ivBytes, final byte[] messageBytes) throws Exception{
        return AES.transform(Cipher.ENCRYPT_MODE, keyBytes, ivBytes, messageBytes);
    }


    /**
     * Decrypt a given set of data based on the initial value and key given.
     * @param keyBytes byte[] representation of the key.
     * @param ivBytes byte[] representation of the initial value for the cipher.
     * @param messageBytes byte[] representation of the message bytes.
     * @return byte[] of decrypted bytes.
     * @throws Exception if decryption fails.
     */
    public static byte[] decrypt(final byte[] keyBytes, final byte[] ivBytes, final byte[] messageBytes)throws Exception{
        return AES.transform(Cipher.DECRYPT_MODE, keyBytes, ivBytes, messageBytes);
    }

    /**
     * Transform encrypted data to decrypted or vice versa.
     * @param mode Cipher.ENCRYPT or Cipher.DECRYPT mode.
     * @param keyBytes byte[] representation of the key.
     * @param ivBytes byte[] representation of the initial value for the cipher.
     * @param messageBytes byte[] representation of the message bytes.
     * @return byte[] of transformed data.
     * @throws Exception if transformation fails.
     */
    private static byte[] transform(final int mode, final byte[] keyBytes, final byte[] ivBytes, final byte[] messageBytes) throws Exception{
        final SecretKeySpec keySpec = new SecretKeySpec(keyBytes, Constants.AES);
        final IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        final Cipher cipher = Cipher.getInstance(Constants.AES_CBC_PKCS);

        cipher.init(mode, keySpec, ivSpec);

        return cipher.doFinal(messageBytes);
    }
}
