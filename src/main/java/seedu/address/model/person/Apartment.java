package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Apartment in the address book. A subclass of Property class
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Apartment extends Property {

    public static final String MESSAGE_CONSTRAINTS = "Apartment names should be alphanumeric";

    /**
     * Constructs a {@code Apartment}.
     *
     * @param postalCode A valid apartment postalCode.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     */
    public Apartment(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    public Apartment(PostalCode postalCode, UnitNumber unitNumber, Price price, Price actualPrice, Set<Tag> tags) {
        super(postalCode, unitNumber, price, actualPrice, tags);
    }

    @Override
    public boolean equals(Object otherApartment) {
        if (this == otherApartment) {
            return true;
        }
        if (!super.equals(otherApartment)) {
            return false;
        }
        if (!(otherApartment instanceof Apartment)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Apartment " + super.toString();
    }
}
