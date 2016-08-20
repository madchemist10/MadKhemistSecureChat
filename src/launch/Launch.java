package launch;

import encryption.*;


/**
 * Launch the application.
 */
public class Launch {
    public static void main(String[] args) {
        try {
            String data = "abcdefghijklmnopqrstuvwxyz";
            String userKey = "Bar12345Bar12345";
            System.out.println(userKey.getBytes().length);
            System.out.println(KeyGen.generateKey("abcdefg".getBytes()).length);
            String initValue = "RandomInitVector";
            byte[] encryptedData = AES.encrypt(KeyGen.generateKey(userKey.getBytes()), initValue.getBytes(), data.getBytes());
            byte[] base64Encoded = Base64code.base64Encode(encryptedData);
            System.out.println(new String(base64Encoded));
            byte[] base64Decoded = Base64code.base64Decode(base64Encoded);
            byte[] decryptedData = AES.decrypt(userKey.getBytes(), initValue.getBytes(), base64Decoded);
            System.out.println(new String(decryptedData));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
