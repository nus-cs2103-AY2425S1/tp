package seedu.address.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * EncryptionManager is a utility class that handles encryption and decryption of data using the AES algorithm.
 * It also manages the generation and storage of encryption keys, saving the key to a local file and
 * retrieving it when necessary.
 */
public class EncryptionManager {
    private static final String ALGORITHM = "AES";

    // File path to where the key is stored
    private static final String FILE_PATH = "vbook.jks";
    private static final String KEYSTORE_ALIAS = "vbook-encryption";
    private static final String KEYSTORE_ALGORITHM = "JCEKS";
    private static final String KEYSTORE_PASSWORD = "changeit";

    /**
     * Encrypts the given data using the AES algorithm with a pre-generated secret key.
     *
     * @param data The plain text data to encrypt.
     * @return A byte array containing the encrypted data.
     * @throws Exception If any encryption errors occur.
     */
    public static byte[] encrypt(String data, String keyPath) throws Exception {
        if (keyPath == null) {
            keyPath = FILE_PATH;
        }
        SecretKey key = getKey(keyPath);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * Decrypts the given encrypted data using the AES algorithm with a pre-generated secret key.
     *
     * @param data The byte array containing the encrypted data.
     * @return A string containing the decrypted plain text data.
     * @throws Exception If any decryption errors occur.
     */
    public static String decrypt(byte[] data, String keyPath) throws Exception {
        if (keyPath == null) {
            keyPath = FILE_PATH;
        }
        SecretKey key = getKey(keyPath);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(data);
        return new String(decryptedData);
    }

    /**
     * Generates a new AES secret key and stores it in a local file.
     * If the key file already exists, it will be overwritten.
     *
     * @throws Exception If any errors occur during key generation or file writing.
     */
    static void generateKey(String keyPath) throws Exception {
        if (keyPath == null) {
            keyPath = FILE_PATH;
        }

        // 1. Create or get keystore
        KeyStore ks = KeyStore.getInstance(KEYSTORE_ALGORITHM);
        char[] pwdArray = KEYSTORE_PASSWORD.toCharArray();

        // 2. Load keystore
        ks.load(null, pwdArray);

        // 3. Check if alias exists
        if (ks.containsAlias(KEYSTORE_ALIAS)) {
            System.out.println("Alias already exists. Not saving keystore.");
            return;
        }

        // 4. Save key into keystore
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection(pwdArray);
        ks.setEntry(KEYSTORE_ALIAS, secret, password);

        // 5. Save keystore
        try (FileOutputStream fos = new FileOutputStream(keyPath)) {
            ks.store(fos, pwdArray);
        }
    }

    static SecretKey getKey(String keyPath) throws Exception {
        if (keyPath == null) {
            keyPath = FILE_PATH;
        }

        File file = new File(keyPath);
        if (!file.exists()) {
            generateKey(keyPath);
        }

        // 1. Get keystore
        KeyStore ks = KeyStore.getInstance(KEYSTORE_ALGORITHM);
        char[] pwdArray = KEYSTORE_PASSWORD.toCharArray();

        // 2. Load keystore
        ks.load(new FileInputStream(keyPath), pwdArray);
        SecretKey encryptionKey = (SecretKey) ks.getKey(KEYSTORE_ALIAS, pwdArray);

        return encryptionKey;

    }
}
