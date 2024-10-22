package seedu.address.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PasswordManagerTest {

    private static final String TEST_PASSWORD = "TestPassword123!";
    private static final String PASSWORD_FILE = "password.txt";

    @BeforeEach
    void setUp() {
        // Ensure the password file is deleted before each test
        try {
            Files.deleteIfExists(Paths.get(PASSWORD_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up the password file after each test
        try {
            Files.deleteIfExists(Paths.get(PASSWORD_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSavePassword() {
        PasswordManager.savePassword(TEST_PASSWORD);

        // Verify the password is saved correctly
        String savedHash = PasswordManager.readPassword();
        assertNotNull(savedHash);
        assertTrue(savedHash.contains(":")); // Check if hash format is correct
    }

    @Test
    void testReadPasswordNoPasswordSet() {
        // When no password has been set, reading should return null
        assertNull(PasswordManager.readPassword());
    }

    @Test
    void testIsPasswordCorrectCorrectPassword() {
        PasswordManager.savePassword(TEST_PASSWORD);
        assertTrue(PasswordManager.isPasswordCorrect(TEST_PASSWORD), "Password should be correct");
    }

    @Test
    void testIsPasswordCorrectIncorrectPassword() {
        PasswordManager.savePassword(TEST_PASSWORD);
        assertFalse(PasswordManager.isPasswordCorrect("WrongPassword"), "Password should be incorrect");
    }

    @Test
    void testIsPasswordCorrectNoPasswordSet() {
        assertFalse(PasswordManager.isPasswordCorrect(TEST_PASSWORD), "No password set, should return false");
    }
}
