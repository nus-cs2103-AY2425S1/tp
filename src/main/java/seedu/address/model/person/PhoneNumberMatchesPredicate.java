package seedu.address.model.person;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Inspired by NameContainsKeywordPredicate
 * Tests that a {@code Person}'s {@code Phone} matches exactly the given phone number.
 */
public class PhoneNumberMatchesPredicate implements Predicate<Person> {
    private final String phoneNumber;

    public PhoneNumberMatchesPredicate(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean test(Person person) {
        return person.hasPhoneNumber(phoneNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PhoneNumberMatchesPredicate)) {
            return false;
        }
        PhoneNumberMatchesPredicate otherPredicate = (PhoneNumberMatchesPredicate) other;
        return phoneNumber.equals(otherPredicate.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "PhoneNumberMatchesPredicate{phoneNumber='" + phoneNumber + "'}";
    }
}
