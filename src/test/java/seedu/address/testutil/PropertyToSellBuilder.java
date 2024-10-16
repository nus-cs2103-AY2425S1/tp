package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyToSellBuilder {

    public static final String DEFAULT_POSTAL_CODE = "121212";
    public static final String DEFAULT_UNIT_NUMBER = "10-02";
    public static final String DEFAULT_PRICE = "1600000";

    private PostalCode postalCode;
    private UnitNumber unitNumber;
    private Price price;
    private Set<Tag> tags;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public PropertyToSellBuilder() {
        postalCode = new PostalCode(DEFAULT_POSTAL_CODE);
        unitNumber = new UnitNumber(DEFAULT_UNIT_NUMBER);
        price = new Price(DEFAULT_PRICE);
        tags = new HashSet<>();
    }

    /**
     * Sets the {@code PostalCode} of the {@code Property} that we are building.
     */
    public PropertyToSellBuilder withPostalCode(String postalCode) {
        this.postalCode = new PostalCode(postalCode);
        return this;
    }

    /**
     * Sets the {@code UnitNumber} of the {@code Property} that we are building.
     */
    public PropertyToSellBuilder withUnitNumber(String unitNumber) {
        this.unitNumber = new UnitNumber(unitNumber);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Property} that we are building.
     */
    public PropertyToSellBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PropertyToSellBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Property build() {
        return new Property(postalCode, unitNumber, price, tags) {};
    }
}
