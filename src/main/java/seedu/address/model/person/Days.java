package seedu.address.model.person;

/**
 * Represents a set of days in the address book.
 */
public enum Days {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(String test) {
        try {
            Days.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
