package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Id;

/**
 * Tests that a {@code Contact}'s {@code Id} matches the given id.
 */
public class ContactIdPredicate implements Predicate<Contact> {
    private final Id id;

    public ContactIdPredicate(Id id) {
        this.id = id;
    }
    @Override
    public boolean test(Contact contact) {
        return contact.getId().equals(this.id);
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
        return id.equals(otherContactIdPredicate.id);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("id", id).toString();
    }
}
