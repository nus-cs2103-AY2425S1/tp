package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.common.Contact;
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
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorTypePredicate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
