package seedu.address.authentication;

/**
 * Handles the authentication function.
 */
public class Authentication {
    // Using default username for testing
    private static final String hrStaff = "group1";
    private static final String staffPassword = "cooked";

    public static boolean authenticate(String username, String password) {
        if (hrStaff.equals(username) && staffPassword.equals(password)){
            // can add more functionality in the future
            return true;
        } else {
            return false;
        }
    }
}
