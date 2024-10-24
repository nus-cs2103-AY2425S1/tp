package seedu.address.authentication;

/**
 * Handles the authentication function.
 */
public class Authentication {
    private static final Credentials credentials = new Credentials("/config/credentials.txt");

    /**
     * Checks if user is allowed to log in.
     * Returns True if allowed, false if not.
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean authenticate(String username, String password) {
        assert username != null: "username should not be null";
        assert password != null: "password should not be null";
        if (credentials.findUser(username, password)) {
            return true;
        } else {
            return false;
        }
    }
}
