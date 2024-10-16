package seedu.address.model.preferredtime;

/**
 * Represents the TIme of PreferredTime to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in range from 0000 to 2359.";

    // TODO: change the REGEX
    public static final String VALIDATION_REGEX = "^([01][0-9]|2[0-3])[0-5][0-9]$";



    /**
     * Returns true if a given string is a valid Time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
