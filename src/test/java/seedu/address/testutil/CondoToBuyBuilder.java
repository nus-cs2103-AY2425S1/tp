package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Condo;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class CondoToBuyBuilder {
    public static final String DEFAULT_POSTAL_CODE = "212121";
    public static final String DEFAULT_UNIT_NUMBER = "22-22";
    public static final String DEFAULT_PRICE = "2100000";

    private PostalCode postalCode;
    private UnitNumber unitNumber;
    private Price price;
    private Set<Tag> tags;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public CondoToBuyBuilder() {
        postalCode = new PostalCode(DEFAULT_POSTAL_CODE);
        unitNumber = new UnitNumber(DEFAULT_UNIT_NUMBER);
        price = new Price(DEFAULT_PRICE);
        tags = new HashSet<>();
    }

    /**
     * Sets the {@code PostalCode} of the {@code Property} that we are building.
     */
    public CondoToBuyBuilder withPostalCode(String postalCode) {
        this.postalCode = new PostalCode(postalCode);
        return this;
    }

    /**
     * Sets the {@code UnitNumber} of the {@code Property} that we are building.
     */
    public CondoToBuyBuilder withUnitNumber(String unitNumber) {
        this.unitNumber = new UnitNumber(unitNumber);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Property} that we are building.
     */
    public CondoToBuyBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Property} that we are building.
     */
    public CondoToBuyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Condo build() {
        return new Condo(postalCode, unitNumber, price, tags) {};
    }
}
