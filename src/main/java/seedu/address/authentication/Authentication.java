package seedu.address.authentication;

/**
 * Handles the authentication function.
 */
public class Authentication {
    private static final Credentials credentials = new Credentials("./credentials.txt");
    /**
     * Checks if user is allowed to log in.
     * Returns True if allowed, false if not.
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean authenticate(String username, String password) {
        if (credentials.findUser(username, password)) {
            return true;
        } else {
            return false;
        }
    }
}
