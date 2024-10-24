package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * A utility class containing a list of {@code Event} and a list of {@code Vendor} objects to be used in
 * tests.
 */
public class TypicalVendorsEventsCombined {

    private TypicalVendorsEventsCombined() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events and vendors.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : TypicalEvents.getTypicalEvents()) {
            ab.addEvent(event);
        }
        for (Vendor vendor : TypicalVendors.getTypicalVendors()) {
            ab.addVendor(vendor);
        }
        return ab;
    }
}
