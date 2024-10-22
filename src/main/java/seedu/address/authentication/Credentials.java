package seedu.address.authentication;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Credentials represents the HR account manager.
 */
public class Credentials {
    private static final Logger logger = LogsCenter.getLogger(Credentials.class);
    // private final File credentials;
    private final String username = "test";
    private final String password = "password1";

    /**
     * Create a Credential Manager
     */
    public Credentials(String filepath) {
        // this.credentials = new File(filepath);
    }

    /**
     * Reads Credentials
     * @return a list of credentials
     */
    private HashMap<String, String> readCredentials() {
        logger.info("reading credentials");
        HashMap<String, String> users = new HashMap<>();
        // try {
        //     Scanner s = new Scanner(credentials);
        //     while (s.hasNextLine()) {
        //         String[] details = s.nextLine().split(" ");
        //         users.put(details[0], details[1]);
        //     }
        //    s.close();
        // } catch (FileNotFoundException e) {
        //     logger.info(e.toString());
        // }
        users.put(username, password);
        return users;
    }

    /**
     * Checks if the correct combination of username and password exists.
     * @param username
     * @param password
     * @return True if such user is found
     */
    public boolean findUser(String username, String password) {
        assert username != null: "username should not be null";
        assert password != null: "password should not be null";
        logger.info("find user");
        HashMap<String, String> credentials = readCredentials();
        if (!credentials.containsKey(username)) {
            logger.info("user not found");
            return false;
        }
        return credentials.get(username).equals(password);
    }
}
