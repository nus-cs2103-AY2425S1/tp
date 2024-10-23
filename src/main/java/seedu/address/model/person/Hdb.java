package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an HDB in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Hdb extends Property {

    public static final String MESSAGE_CONSTRAINTS = "HDB names should be alphanumeric";


    /**
     * Constructs a {@code Hdb}.
     *
     * @param postalCode A valid Hdb postalCode.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     */
    public Hdb(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    public Hdb(PostalCode postalCode, UnitNumber unitNumber, Price price, Price actualPrice, Set<Tag> tags) {
        super(postalCode, unitNumber, price, actualPrice, tags);
    }

    @Override
    public boolean equals(Object otherHdb) {
        if (this == otherHdb) {
            return true;
        }
        if (!super.equals(otherHdb)) {
            return false;
        }
        if (!(otherHdb instanceof Hdb)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hdb " + super.toString();
    }
}
