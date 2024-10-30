package seedu.eventtory.testutil;

import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;

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
    public static EventTory getTypicalAddressBook() {
        EventTory et = new EventTory();
        for (Event event : TypicalEvents.getTypicalEvents()) {
            et.addEvent(event);
        }
        for (Vendor vendor : TypicalVendors.getTypicalVendors()) {
            et.addVendor(vendor);
        }
        return et;
    }
}
