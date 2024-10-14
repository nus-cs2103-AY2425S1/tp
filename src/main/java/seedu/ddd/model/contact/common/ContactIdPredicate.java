package seedu.ddd.model.contact.common;

import java.util.function.Predicate;

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
}
