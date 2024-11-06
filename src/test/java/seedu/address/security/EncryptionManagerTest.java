package seedu.address.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptionManagerTest {

    private static final String TEST_DATA = "This is a secret message";
    private static final String KEY_FILE_PATH = "test-key.jks";

    // This is to prevent FileSystemException in Windows due to concurrency issues
    private static int tmpFileCount = 0;
    private File keyFile;



    @BeforeEach
    public void setUp() {
        keyFile = new File(tmpFileCount + KEY_FILE_PATH);
        tmpFileCount++;
    }

    @AfterAll
    public static void cleanUp() {
        try {
            for (int i = 0; i <= tmpFileCount; i++) {
                Files.deleteIfExists(Path.of(i + KEY_FILE_PATH));
            }
        } catch (IOException e) {
            if (System.getProperty("os.name").startsWith("Windows")) {
                // known issue where Windows is unable to delete files
                return;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testGenerateKey() throws Exception {
        assertFalse(keyFile.exists(), "Key file should not exist before generating a key");
        EncryptionManager.generateKey(keyFile.getPath());
        assertTrue(keyFile.exists(), "Key file should be created after generating a key");
    }

    @Test
    public void testGetKey() throws Exception {
        assertFalse(keyFile.exists(), "Key file should not exist before retrieving a key");
        SecretKey key = EncryptionManager.getKey(keyFile.getPath());
        assertNotNull(key, "SecretKey should not be null");
        assertTrue(keyFile.exists(), "Key file should be created if it didn't exist");

        // Fetch the key again and ensure it’s the same
        SecretKey sameKey = EncryptionManager.getKey(keyFile.getPath());
        assertEquals(key, sameKey, "The retrieved key should be the same");
    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        byte[] encryptedData = EncryptionManager.encrypt(TEST_DATA, keyFile.getPath());
        assertNotNull(encryptedData, "Encrypted data should not be null");
        assertTrue(encryptedData.length > 0, "Encrypted data should not be empty");

        // Decrypt the data
        String decryptedData = EncryptionManager.decrypt(encryptedData, keyFile.getPath());

        // Ensure the decrypted data matches the original plain text
        assertEquals(TEST_DATA, decryptedData, "Decrypted data should match the original text");
    }

    @Test
    public void testDecryptWithWrongKey() throws Exception {
        byte[] encryptedData = EncryptionManager.encrypt(TEST_DATA, keyFile.getPath());

        // Manually generate a new key to simulate using the wrong key for decryption
        String wrongPath = "wrong-path.jks";
        EncryptionManager.generateKey(wrongPath);
        SecretKey wrongKey = EncryptionManager.getKey(wrongPath);

        // Attempt to decrypt the encrypted data with the wrong key should fail
        assertThrows(Exception.class, () -> {
            // Use a new encryption manager instance
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, wrongKey);
            cipher.doFinal(encryptedData);
        }, "Decryption with the wrong key should throw an exception");
    }
}
