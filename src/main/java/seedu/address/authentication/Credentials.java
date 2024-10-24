package seedu.address.authentication;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Credentials represents the HR account manager.
 */
public class Credentials {
    private static final Logger logger = LogsCenter.getLogger(Credentials.class);
    private final String credentials;

    /**
     * Create a Credential Manager
     */
    public Credentials(String filePath) {
        this.credentials = filePath;
    }

    /**
     * Process Credentials
     * @param file
     * @return users
     */
    private HashMap<String, String> processFile(InputStreamReader file) {
        HashMap<String, String> users = new HashMap<>();
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            String[] details = s.nextLine().split(" ");
            users.put(details[0], details[1]);
        }
        s.close();
        return users;
    }

    /**
     * Reads Credentials
     * @return a list of credentials
     */
    private HashMap<String, String> readCredentials() {
        logger.info("reading credentials");
        InputStream file = getClass().getResourceAsStream(credentials);
        if (file == null) {
            logger.info("Credentials not found!");
            return new HashMap<>();
        }
        return processFile(new InputStreamReader(file));
    }

    /**
     * Checks if the correct combination of username and password exists.
     * @param username
     * @param password
     * @return True if such user is found
     */
    public boolean findUser(String username, String password) {
        assert username != null : "username should not be null";
        assert password != null : "password should not be null";
        logger.info("find user");
        HashMap<String, String> credentials = readCredentials();
        if (!credentials.containsKey(username)) {
            logger.info("user not found");
            return false;
        }
        return credentials.get(username).equals(Hashing.hash(password));
    }
}
