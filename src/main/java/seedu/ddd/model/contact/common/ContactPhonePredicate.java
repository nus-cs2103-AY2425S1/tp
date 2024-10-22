package seedu.ddd.model.contact.common;

import java.util.function.Predicate;

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
}
