package seedu.address.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a SHA256 hash.
 */
public class Hashing {
    private static final Logger logger = LogsCenter.getLogger(Hashing.class);

    /**
     * Generates a SHA256 hash
     * @param input
     * @return a hash
     */
    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.info(e.toString());
            throw new RuntimeException("This should never happen");
        }
    }
}
