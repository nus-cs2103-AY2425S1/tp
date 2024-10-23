package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Phone;


/**
 * Tests that a {@Code Contact} 's {@code Phone} matches the given phone number.
 */
public class ContactPhonePredicate implements Predicate<Contact> {
    private final Phone phoneNumber;
    public ContactPhonePredicate(Phone phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public boolean test(Contact contact) {
        return contact.getPhone().value.contains(this.phoneNumber.value);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactPhonePredicate)) {
            return false;
        }

        ContactPhonePredicate otherContactPhonePredicate = (ContactPhonePredicate) other;
        return phoneNumber.equals(otherContactPhonePredicate.phoneNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phoneNumber", phoneNumber).toString();
    }
}
