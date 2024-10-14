package seedu.ddd.model.contact.common;

import java.util.function.Predicate;

import seedu.ddd.model.contact.client.Client;

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
}
