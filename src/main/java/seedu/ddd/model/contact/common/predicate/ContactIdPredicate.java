package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.contact.common.Contact;

/**
 * Tests that a {@code Contact}'s {@code Id} matches the given id.
 */
public class ContactIdPredicate implements Predicate<Contact> {
    private final Id contactId;

    public ContactIdPredicate(Id contactId) {
        this.contactId = contactId;
    }
    @Override
    public boolean test(Contact contact) {
        return contact.getId().equals(this.contactId);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactIdPredicate)) {
            return false;
        }

        ContactIdPredicate otherContactIdPredicate = (ContactIdPredicate) other;
        return contactId.equals(otherContactIdPredicate.contactId);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("id", contactId).toString();
    }
}
