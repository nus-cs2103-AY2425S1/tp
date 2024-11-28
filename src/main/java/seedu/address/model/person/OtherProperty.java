package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Other Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OtherProperty extends Property {

    public static final String MESSAGE_CONSTRAINTS = "Other Property names should be alphanumeric";


    /**
     * Constructs a {@code OtherProperty}.
     *
     * @param postalCode A valid OtherProperty postalCode.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     */
    public OtherProperty(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        super(postalCode, unitNumber, price, tags);
    }

    public OtherProperty(PostalCode postalCode, UnitNumber unitNumber, Price price, Price actualPrice, Set<Tag> tags) {
        super(postalCode, unitNumber, price, actualPrice, tags);
    }

    @Override
    public boolean equals(Object otherProperty) {
        if (this == otherProperty) {
            return true;
        }
        if (!super.equals(otherProperty)) {
            return false;
        }
        if (!(otherProperty instanceof OtherProperty)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OtherProperty " + super.toString();
    }
}
