package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the phone number given.
 */
public class PhonePredicate implements Predicate<Person> {
    private final String phoneNumber;

    public PhonePredicate(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean test(Person person) {
        return person.getPhone().equals(new Phone(phoneNumber));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PhonePredicate)) {
            return false;
        }

        PhonePredicate otherPhonePredicate = (PhonePredicate) other;
        return phoneNumber.equals(otherPhonePredicate.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phone", phoneNumber).toString();
    }
}
