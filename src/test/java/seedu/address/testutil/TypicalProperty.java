package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PropertyBook;
import seedu.address.model.property.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperty {
    public static final Property ADMIRALTY = new PropertyBuilder().withUnit("02-22").withPostalCode("654321")
            .withType("CONDO").withAsk("50000").withBid("10000").build();
    public static final Property BEDOK = new PropertyBuilder().withUnit("11-12").withPostalCode("321456")
            .withType("HDB").withAsk("60000").withBid("20000").build();
    public static final Property CLEMENTI = new PropertyBuilder().withUnit("00-00").withPostalCode("321499")
            .withType("LANDED").withAsk("70000").withBid("30000").build();

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static PropertyBook getTypicalPropertyBook() {
        PropertyBook ab = new PropertyBook();
        for (Property property : getTypicalProperties()) {
            ab.addProperty(property);
        }
        return ab;
    }

    public static List<Property> getTypicalProperties() {
        return new ArrayList<>(Arrays.asList(ADMIRALTY, BEDOK));
    }

}

