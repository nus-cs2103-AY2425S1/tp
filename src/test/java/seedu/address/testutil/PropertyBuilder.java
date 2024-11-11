package seedu.address.testutil;

import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
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
    public static final String DEFAULT_ASK = "60000";
    public static final String DEFAULT_BID = "20000";

    private Unit unit;
    private PostalCode postalCode;
    private Type type;
    private Ask ask;
    private Bid bid;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PropertyBuilder() {
        unit = new Unit(DEFAULT_UNIT);
        postalCode = new PostalCode(DEFAULT_POSTALCODE);
        type = new Type(DEFAULT_TYPE);
        ask = new Ask(DEFAULT_ASK);
        bid = new Bid(DEFAULT_BID);
        SampleDataUtil.getSamplePropertyBook();
    }

    /**
     * Initializes the PropertyBuilder with the data of {@code propertyToCopy}.
     */
    public PropertyBuilder(Property propertyToCopy) {
        unit = propertyToCopy.getUnit();
        postalCode = propertyToCopy.getPostalCode();
        type = propertyToCopy.getType();
        ask = propertyToCopy.getAsk();
        bid = propertyToCopy.getBid();
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

    /**
     * Sets the {@code ask} of the {@code Property} that we are building.
     */
    public PropertyBuilder withAsk(String ask) {
        this.ask = new Ask(ask);
        return this;
    }

    /**
     * Sets the {@code bid} of the {@code Property} that we are building.
     */
    public PropertyBuilder withBid(String bid) {
        this.bid = new Bid(bid);
        return this;
    }

    public Property build() {
        return new Property(postalCode, unit, type, ask, bid);
    }
}
