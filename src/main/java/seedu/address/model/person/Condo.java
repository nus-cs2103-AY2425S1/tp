package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Condo in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Condo extends Property {

    public static final String MESSAGE_CONSTRAINTS = "Condo names should be alphanumeric";

    public Condo(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    @Override
    public boolean equals(Object otherCondo) {
        if (this == otherCondo) {
            return true;
        }
        if (!super.equals(otherCondo)) {
            return false;
        }
        if (!(otherCondo instanceof Condo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
