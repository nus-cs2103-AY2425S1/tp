package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

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

    // Manually added - Vendor's details found in {@code CommandTestUtil}
    public static final Vendor FIONA = new VendorBuilder().withName(VALID_NAME_FIONA).withPhone(VALID_PHONE_FIONA)
            .withEmail(VALID_EMAIL_FIONA).withAddress(VALID_ADDRESS_FIONA).withTags(VALID_TAG_FRIEND).build();

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
