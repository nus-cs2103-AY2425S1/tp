package seedu.address.authentication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Credentials represents the HR account manager.
 */
public class Credentials {
    private final File credentials;
    private static final Logger logger = LogsCenter.getLogger(Credentials.class);

    /**
     * Create a Credential Manager
     */
    public Credentials(String filepath) {
        this.credentials = new File(filepath);
    }
    /**
     * Reads Credentials
     * @return a list of credentials
     */
    private HashMap<String, String> readCredentials() {
        logger.info("reading credentials");
        HashMap<String, String> users = new HashMap<>();
        try {
            Scanner s = new Scanner(credentials);
            while (s.hasNextLine()) {
                String[] details = s.nextLine().split(" ");
                users.put(details[0], details[1]);
            }
            s.close();
        } catch (FileNotFoundException e) {
            logger.info(e.toString());
        }
        return users;
    }
    public boolean findUser(String username, String password) {
        logger.info("find user");
        HashMap<String, String> credentials = readCredentials();
        if (!credentials.containsKey(username)) {
            logger.info("user not found");
            return false;
        }
        return credentials.get(username).equals(password);
    }
}
