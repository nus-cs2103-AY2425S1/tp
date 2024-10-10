package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Condo in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Condo extends Property {

    public static final String MESSAGE_CONSTRAINTS = "Condo names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the Condo name is alphanumeric

    public Condo(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    /**
    * Returns true if a given string is a valid Condo name.
    */
    public static boolean isValidCondoName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
