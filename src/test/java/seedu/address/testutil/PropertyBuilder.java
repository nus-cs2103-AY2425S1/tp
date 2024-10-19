package seedu.address.testutil;

import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilder {
    public static final String DEFAULT_POSTALCODE = "123456";
    public static final String DEFAULT_UNIT = "08-20";
    public static final String DEFAULT_TYPE = "CONDO";

    private Unit unit;
    private PostalCode postalCode;
    private Type type;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PropertyBuilder() {
        unit = new Unit(DEFAULT_UNIT);
        postalCode = new PostalCode(DEFAULT_POSTALCODE);
        type = new Type(DEFAULT_TYPE);
        SampleDataUtil.getSamplePropertyBook();
    }

    /**
     * Initializes the PropertyBuilder with the data of {@code propertyToCopy}.
     */
    public PropertyBuilder(Property propertyToCopy) {
        unit = propertyToCopy.getUnit();
        postalCode = propertyToCopy.getPostalCode();
        type = propertyToCopy.getType();
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

    /**
     * Sets the {@code Type} of the {@code Property} that we are building.
     */
    public PropertyBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    public Property build() {
        return new Property(postalCode, unit, type);
    }
}
