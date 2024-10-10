package seedu.address.testutil;

import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Unit;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilder {
    public static final String DEFAULT_POSTALCODE = "123456";
    public static final String DEFAULT_UNIT = "08-20";

    private Unit unit;
    private PostalCode postalCode;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PropertyBuilder() {
        unit = new Unit(DEFAULT_UNIT);
        postalCode = new PostalCode(DEFAULT_POSTALCODE);
    }

    /**
     * Initializes the PropertyBuilder with the data of {@code propertyToCopy}.
     */
    public PropertyBuilder(Property propertyToCopy) {
        unit = propertyToCopy.getUnit();
        postalCode = propertyToCopy.getPostalCode();
    }

    /**
     * Sets the {@code Unit} of the {@code Property} that we are building.
     */
    public PropertyBuilder withUnit(String unit) {
        this.unit = new Unit(unit);
        return this;
    }

    /**
     * Sets the {@code PostalCode} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPostalCode(String postalCode) {
        this.postalCode = new PostalCode(postalCode);
        return this;
    }

    public Property build() {
        return new Property(postalCode, unit);
    }
}
