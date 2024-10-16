package seedu.address.model.preferredtime;

/**
 * Represents the Day of PreferredTime to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}.
 */
public class Day {

    public static final String MESSAGE_CONSTRAINTS =
            "Day should only contain alphanumeric characters and it should not be blank.\n"
            + "It should be one of the {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}.";

    // TODO: change the REGEX
    public static final String VALIDATION_REGEX = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)$";


    /**
     * Returns true if a given string is a valid Day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
