package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Phone} contains the given phone.
 */
public class PhoneExactPredicate implements Predicate<Person> {
    private final String phone;

    public PhoneExactPredicate(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean test(Person person) {
        return person.getPhone().value.equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PhonePredicate)) {
            return false;
        }

        PhoneExactPredicate otherPredicate = (PhoneExactPredicate) other;
        return phone.equals(otherPredicate.phone);
    }
}
