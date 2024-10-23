package seedu.address.model.wedding;

import seedu.address.model.person.Address;

/**
 * Represents a wedding venue in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue extends Address {
    public static final String MESSAGE_CONSTRAINTS = "Venue can take any values, and it should not be blank";

    /**
     *  Constructs and {@code Venue}
      * @param value
     */
    public Venue(String value) {
        super(value);
    }

    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}

