package seedu.ddd.model.contact.common;

import java.util.function.Predicate;

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
}
