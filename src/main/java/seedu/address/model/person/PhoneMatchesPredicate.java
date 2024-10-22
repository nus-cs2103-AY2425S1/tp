package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the given phone number.
 */
public class PhoneMatchesPredicate implements Predicate<Person> {
    private final String phone;

    public PhoneMatchesPredicate(String phone) {
        this.phone = phone.trim();
    }

    @Override
    public boolean test(Person person) {
        return person.getPhone().value.equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PhoneMatchesPredicate
                && phone.equals(((PhoneMatchesPredicate) other).phone));
    }
}
