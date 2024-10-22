package seedu.address.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptionManagerTest {

    private static final String TEST_DATA = "This is a secret message";
    private static final String KEY_FILE_PATH = "key.txt";
    private File keyFile;

    @BeforeEach
    public void setUp() {
        keyFile = new File(KEY_FILE_PATH);
        // Delete the key file if it exists before each test
        if (keyFile.exists()) {
            keyFile.delete();
        }
    }

    @Test
    public void testGenerateKey() throws Exception {
        assertFalse(keyFile.exists(), "Key file should not exist before generating a key");

        EncryptionManager.generateKey();
        assertTrue(keyFile.exists(), "Key file should be created after generating a key");

        // Check that the key file contains a Base64 encoded string
        String encodedKey = Files.readString(keyFile.toPath(), StandardCharsets.UTF_8);
        assertNotNull(encodedKey, "Key file should contain a Base64 encoded key");
        assertTrue(encodedKey.length() > 0, "Encoded key should not be empty");
    }

    @Test
    public void testGetKey() throws Exception {
        assertFalse(keyFile.exists(), "Key file should not exist before retrieving a key");
        SecretKey key = EncryptionManager.getKey();
        assertNotNull(key, "SecretKey should not be null");
        assertTrue(keyFile.exists(), "Key file should be created if it didn't exist");

        // Fetch the key again and ensure it’s the same
        SecretKey sameKey = EncryptionManager.getKey();
        assertEquals(key, sameKey, "The retrieved key should be the same");
    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        byte[] encryptedData = EncryptionManager.encrypt(TEST_DATA);
        assertNotNull(encryptedData, "Encrypted data should not be null");
        assertTrue(encryptedData.length > 0, "Encrypted data should not be empty");

        // Decrypt the data
        String decryptedData = EncryptionManager.decrypt(encryptedData);

        // Ensure the decrypted data matches the original plain text
        assertEquals(TEST_DATA, decryptedData, "Decrypted data should match the original text");
    }

    @Test
    public void testDecryptWithWrongKey() throws Exception {
        byte[] encryptedData = EncryptionManager.encrypt(TEST_DATA);

        // Manually generate a new key to simulate using the wrong key for decryption
        EncryptionManager.generateKey();
        SecretKey wrongKey = EncryptionManager.getKey();

        // Attempt to decrypt the encrypted data with the wrong key should fail
        assertThrows(Exception.class, () -> {
            // Use a new encryption manager instance
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, wrongKey);
            cipher.doFinal(encryptedData);
        }, "Decryption with the wrong key should throw an exception");
    }
}
