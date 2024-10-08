package seedu.address.authentication;

/**
 * Handles the authentication function.
 */
public class Authentication {
    // Using default username for testing
    private static final String hrStaff = "group1";
    private static final String staffPassword = "cooked";

    /**
     * Checks if user is allowed to log in.
     * Returns True if allowed, false if not.
     * @param username
     * @param password
     * @return boolean
     */
    public static boolean authenticate(String username, String password) {
        if (hrStaff.equals(username) && staffPassword.equals(password)) {
            // can add more functionality in the future
            return true;
        } else {
            return false;
        }
    }
}
