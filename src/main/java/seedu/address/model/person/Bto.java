package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a BTO in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bto extends Property {

    public static final String MESSAGE_CONSTRAINTS = "BTO names should be alphanumeric";

    /**
     * Constructs a {@code Bto}.
     *
     * @param postalCode A valid Bto postalCode.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     */
    public Bto(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    public Bto(PostalCode postalCode, UnitNumber unitNumber, Price actualPrice, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, actualPrice, tags);
    }

    @Override
    public boolean equals(Object otherBto) {
        if (!super.equals(otherBto)) {
            return false;
        }
        if (!(otherBto instanceof Bto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bto " + super.toString();
    }
}
