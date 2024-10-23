package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Vendor;

/**
 * A utility class containing a list of {@code Vendor} objects to be used in tests.
 */
public class TypicalVendors {

    public static final Vendor ALISON = new VendorBuilder().withName("Alison Longwood")
            .withAddress("234, Changi Street 12, #08-734").withEmail("amy@example.com")
            .withPhone("92834726")
            .withTags("florist").build();

    public static final Vendor BORIS = new VendorBuilder().withName("Boris Lee")
            .withAddress("98, Bishan Street 12, #01-029").withEmail("boris@example.com")
            .withPhone("81723624")
            .withTags("caterer").build();

    private TypicalVendors() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        for (Vendor vendor : getTypicalVendors()) {
            ab.addPerson(vendor);
        }
        return ab;
    }

    public static List<Vendor> getTypicalVendors() {
        return new ArrayList<>(Arrays.asList(ALISON, BORIS));
    }
}
