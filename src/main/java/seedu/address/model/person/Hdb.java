package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an HDB in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Hdb extends Property {

    public static final String MESSAGE_CONSTRAINTS = "HDB names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the HDB name is alphanumeric


    public Hdb(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    /**
    * Returns true if a given string is a valid HDB name.
    */
    public static boolean isValidHdbName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
