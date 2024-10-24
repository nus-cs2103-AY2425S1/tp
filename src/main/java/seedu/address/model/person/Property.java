package seedu.address.model.person;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.tag.Tag;


/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Property {

    public static final String MESSAGE_CONSTRAINTS = "Property names should be alphanumeric";

    private final PostalCode postalCode;
    private final UnitNumber unitNumber;
    private final Price price;
    private Price actualPrice;
    private final Set<Tag> tags;

    /**
     * Constructs a {@code Property}.
     *
     * @param postalCode A valid Property postalCode.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     * @param tags A set of tags associated to the Property
     */
    public Property(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
        this.price = price;
        this.tags = tags;
        this.actualPrice = new Price("0");
    }

    /**
     * Constructs a {@code Property}.
     *
     * @param postalCode A valid Property postalCode.
     * @param unitNumber A valid unit number.
     * @param price A valid price.
     * @param actualPrice A valid price.
     * @param tags A set of tags associated to the Property
     */
    public Property(PostalCode postalCode, UnitNumber unitNumber, Price price, Price actualPrice, Set<Tag> tags) {
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
        this.price = price;
        this.actualPrice = actualPrice;
        this.tags = tags;
    }

    /**
     * Returns the postal code associated with this property
     *
     * @return the {@code PostalCode} of this property
     */
    public PostalCode getPostalCode() {
        return postalCode;
    }

    /**
     * Returns the unit number associated with this property
     *
     * @return the {@code UnitNumber} of this property
     */
    public UnitNumber getUnitNumber() {
        return unitNumber;
    }

    /**
     * Returns the price associated with this property
     *
     * @return the {@code Price} of this property
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Returns the actual price associated with this property
     *
     * @return the {@code actualPrice} of this property
     */
    public Price getActualPrice() {
        return actualPrice;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the property is the same as the other property.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && this.getClass().equals(otherProperty.getClass())
                && otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnitNumber().equals(getUnitNumber());
    }

    /**
     * Sets the actual price {@code Price} if the user specifies an actual price.
     * Else set actual price {@code Price} with the original price.
     *
     * @param newPrice Price {@code Price} that the user specifies as the actual price.
     */
    public void setActualPrice(Price newPrice) {
        actualPrice = newPrice;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return postalCode.equals(otherProperty.postalCode) && unitNumber.equals(otherProperty.unitNumber);
    }

    @Override
    public int hashCode() {
        return postalCode.hashCode();
    }
    /**
     * Format state as text for viewing.
     */
    public String toString() {
        String formattedTags = tags.stream()
                .map(Tag::toString) // Convert each Tag object to its String representation
                .collect(Collectors.joining(", ")); // Join with a comma and space
        return "Postal Code: " + postalCode + "; " + " Unit Number: " + unitNumber + "; " + " Price: " + price + "; "
                + " Actual Price: " + actualPrice + "; Tags: " + formattedTags;
    }
    /*public String toString() {
        return "[" + postalCode + "]" + " Unit Number: " + unitNumber;
    }*/

}
