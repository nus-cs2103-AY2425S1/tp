package seedu.address.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * The PasswordManager class provides functionalities for securely hashing
 * passwords and storing them in a file. It uses PBKDF2 with HMAC-SHA1
 * for hashing, and it includes methods for saving, reading, and verifying
 * passwords.
 */
public class PasswordManager {
    private static final String PASSWORD_FILE = "password.txt";
    private static final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int HASHING_ITERATION_COUNT = 65536;
    private static final int HASHING_KEY_LENGTH = 128;

    /**
     * Reads the hashed password from the password file.
     *
     * @return The stored hashed password, or null if the file does not exist.
     */
    public static String readPassword() {
        File file = new File(PASSWORD_FILE);
        if (!file.exists()) {
            return null; // No password set
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves the hashed password to the password file.
     *
     * @param password The plaintext password to hash and store.
     */
    public static void savePassword(String password) {
        try {
            File file = new File(PASSWORD_FILE);
            // Create a new file if it doesn't exist
            file.createNewFile();

            // Write the hashed password to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(hashPassword(password, generateSalt()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Verifies if the provided password matches the stored hashed password.
     *
     * @param inputPassword The plaintext password to verify.
     * @return True if the password matches, false otherwise.
     */
    public static boolean isPasswordCorrect(String inputPassword) {
        String storedPasswordHash = readPassword();
        if (storedPasswordHash == null) {
            return false; // No password set
        }

        String[] parts = storedPasswordHash.split(":");
        if (parts.length != 2) {
            throw new IllegalStateException("Stored password format is incorrect.");
        }

        String storedSaltString = parts[0];
        String storedHashString = parts[1];

        byte[] salt = Base64.getDecoder().decode(storedSaltString);
        String hashedInputPasswordWithSalt = hashPassword(inputPassword, salt);

        String[] hashedInputParts = hashedInputPasswordWithSalt.split(":");
        String hashedInputPassword = hashedInputParts[1];

        return hashedInputPassword.equals(storedHashString);
    }

    // Hash the password: https://stackoverflow.com/a/2861125
    private static String hashPassword(String password, byte[] salt) {
        try {
            // Hash the password using provided HASHING_ALGORITHM
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                        HASHING_ITERATION_COUNT, HASHING_KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASHING_ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Encode both salt and hash as Base64 strings for storage
            Base64.Encoder encoder = Base64.getEncoder();
            String saltString = encoder.encodeToString(salt);
            String hashString = encoder.encodeToString(hash);

            // Return both the salt and the hash
            return saltString + ":" + hashString;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }
}
