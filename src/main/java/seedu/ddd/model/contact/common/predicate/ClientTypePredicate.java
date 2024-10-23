package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;

/**
 * Tests that a {@code Contact} is of type {@code Client}.
 */
public class ClientTypePredicate implements Predicate<Contact> {
    public ClientTypePredicate() {}

    @Override
    public boolean test(Contact contact) {
        if (contact instanceof Client) {
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
        if (!(other instanceof ClientTypePredicate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
