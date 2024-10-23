package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;


/**
 * Tests that a {@Code Contact} 's {@code Email} matches the given phone number partially or fully.
 */
public class ContactEmailPredicate implements Predicate<Contact> {
    private final Email email;

    public ContactEmailPredicate(Email email) {
        this.email = email;
    }

    @Override
    public boolean test(Contact contact) {
        return this.email.value.contains(contact.getEmail().value);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactEmailPredicate)) {
            return false;
        }
        ContactEmailPredicate otherContactEmailPredicate = (ContactEmailPredicate) other;
        return this.email.equals(otherContactEmailPredicate.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("email", email).toString();
    }
}
