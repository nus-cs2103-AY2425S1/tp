package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Property {
    private static final Logger logger = LogsCenter.getLogger(Property.class);
    private final PostalCode postalCode;
    private final Unit unit;
    private final Type type;
    private final Ask ask;
    private final Bid bid;

    /**
     * Every field must be present and not null.
     * If the type is landed, then unit of a landed property will always default to 00-00
     */
    public Property(PostalCode postalCode, Unit unit, Type type, Ask ask, Bid bid) {
        logger.info(String.format("Creating Property object with PostalCode: %s, Unit: %s, Type: %s, Ask: %s, Bid: %s",
                        postalCode, unit, type, ask, bid));
        requireAllNonNull(postalCode, unit, type, ask, bid);
        this.postalCode = postalCode;
        this.type = type;
        this.ask = ask;
        this.bid = bid;
        if (this.type.isLandedType()) {
            this.unit = Unit.DEFAULT_LANDED_UNIT;
        } else {
            this.unit = unit;
        }
        logger.info("Property object created");
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public Unit getUnit() {
        return unit;
    }

    public Type getType() {
        return type;
    }

    public Ask getAsk() {
        return ask;
    }

    public Bid getBid() {
        return bid;
    }

    /**
     * Returns true if either property is landed and have same postal code
     * OR is not landed and different type and have same postal code
     * OR is not landed and same type and same postal code and unit number
     * This defines a weaker notion of equality between two properties.
     */
    public boolean isSameProperty(Property otherProperty) {
        logger.info(String.format("Checking property sameness between %s and %s", this, otherProperty));

        if (otherProperty == this) {
            return true;
        } else if (otherProperty == null) {
            return false;
        }

        boolean isLandedProperty = this.type.equals(new Type(PropertyType.LANDED.toString()));

        //If either property to be compared is LANDED, then their uniqueness can only be determined by their postal code
        //because their unit numbers are the same (i.e 00-00)
        //Likewise, if the properties to be compared are HDB and CONDO, then their uniqueness can already be determined
        //by their postal code because two different property types cannot exist in the same location
        //Finally, if HDB or CONDO are being compared within its type, then their uniqueness also needs to consider
        //unit number
        if (isLandedProperty || !otherProperty.getType().equals(getType())) {
            return otherProperty.getPostalCode().equals(getPostalCode());
        } else {
            return otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnit().equals(getUnit());
        }
    }

    /**
     * Returns true if both properties have the same identity and data fields.
     * This defines a stronger notion of equality between two properties.
     */
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

        return otherProperty != null
                && otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnit().equals(getUnit())
                && otherProperty.getType().equals(getType())
                && otherProperty.getAsk().equals(getAsk())
                && otherProperty.getBid().equals(getBid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, unit, type, ask, bid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("postalCode", postalCode)
                .add("unit", unit)
                .add("type", type)
                .add("ask", ask)
                .add("bid", bid)
                .toString();
    }
}
