package seedu.address.testutil;

import seedu.address.model.property.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperty {
    public static final Property ADMIRALTY = new PropertyBuilder().withUnit("02-22").withPostalCode("654321").build();
    public static final Property BEDOK = new PropertyBuilder().withUnit("11-12").withPostalCode("321456").build();
}
