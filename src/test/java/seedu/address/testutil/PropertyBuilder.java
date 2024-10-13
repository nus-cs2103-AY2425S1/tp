package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilder {

    public static final String DEFAULT_POSTAL_CODE = "123456";
    public static final String DEFAULT_UNIT_NUMBER = "10-01";
    public static final String DEFAULT_PRICE = "1.5M";

    private final PostalCode postalCode;
    private final UnitNumber unitNumber;
    private final Price price;
    private final Set<Tag> tags;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public PropertyBuilder() {
        postalCode = new PostalCode(DEFAULT_POSTAL_CODE);
        unitNumber = new UnitNumber(DEFAULT_UNIT_NUMBER);
        price = new Price(DEFAULT_PRICE);
        tags = new HashSet<>();
    }

    public Property build() {
        return new Property(postalCode, unitNumber, price, tags) {};
    }
}
