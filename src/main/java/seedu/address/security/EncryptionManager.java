package seedu.address.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * EncryptionManager is a utility class that handles encryption and decryption of data using the AES algorithm.
 * It also manages the generation and storage of encryption keys, saving the key to a local file and
 * retrieving it when necessary.
 */
public class EncryptionManager {
    private static final String ALGORITHM = "AES";

    // File path to where the key is stored
    private static final String FILE_PATH = "key.txt";

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
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        File file = new File(keyPath);
        file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(encodedKey);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            byte[] decodedKey = Base64.getDecoder().decode(reader.readLine());
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            return originalKey;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
