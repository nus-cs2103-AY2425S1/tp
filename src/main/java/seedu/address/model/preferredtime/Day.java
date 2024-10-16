package seedu.address.model.preferredtime;

/**
 * Represents the Day of PreferredTime to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}.
 */
public class Day {

    public static final String MESSAGE_CONSTRAINTS =
            "Day should only contain alphanumeric characters and it should not be blank.\n"
            + "It should be one of the {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}.\n"
            + "Or corresponding abbreviations {Mon, Tue, Wed, Thu, Fri, Sat, Sun}";

    // TODO: change the REGEX
    public static final String REGEX_DAY_COMPLETE = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday";
    public static final String REGEX_DAY_ABBR = "Mon|Tue|Wed|Thu|Fri|Sat|Sun";
    // public static final String VALIDATION_REGEX = "^(?i)(Mon|Tue|Wed|Thu|Fri|Sat|Sun)(day)?$";
    public static final String VALIDATION_REGEX = "^(?i)(" + REGEX_DAY_COMPLETE + "|"
            + REGEX_DAY_ABBR + ")$";


    /**
     * Returns true if a given string is a valid Day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
