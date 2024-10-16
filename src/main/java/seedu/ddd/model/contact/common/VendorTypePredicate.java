package seedu.ddd.model.contact.common;

import java.util.function.Predicate;

import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Tests that a {@code Contact} is of type {@code Vendor}.
 */
public class VendorTypePredicate implements Predicate<Contact> {
    public VendorTypePredicate() {}

    @Override
    public boolean test(Contact contact) {
        if (contact instanceof Vendor) {
            return true;
        } else {
            return false;
        }
    }
}
