package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests that a {@code Person}'s {@code Phone} matches the phone number given.
 */
public class PhonePredicate implements Predicate<Person> {
    private final Phone phoneNumber;


    /**
     * Constructs a PhonePredicate with a specified phone number.
     *
     * @param phoneNumber The phone number to match against Person objects.
     */
    public PhonePredicate(String phoneNumber) {
        this.phoneNumber = new Phone(phoneNumber);
    }

    /**
     * Evaluates this predicate on the given Person instance.
     * Tests if the Person's phone number matches the phone number provided during instantiation.
     *
     * @param person The Person to be tested against this predicate.
     * @return true if the Person's phone number matches the predicate's phone number, otherwise false.
     */
    @Override
    public boolean test(Person person) {
        return person.getPhone().equals(phoneNumber);
    }

    /**
     * Compares this predicate with another object for equality.
     * Two PhonePredicate objects are considered equal if their phone numbers are equal.
     *
     * @param other The object to compare with this predicate.
     * @return true if the other object is an instance of PhonePredicate and their phone numbers are equal.
     */
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

    /**
     * Returns a hash code value for the phone number.
     * Ensures consistent behavior with equals when storing in hash-based collections.
     *
     * @return the hash code value of the phone number.
     */
    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }

    /**
     * Provides a string representation of this PhonePredicate.
     * Includes the phone number in the string output to aid in debugging and logging.
     *
     * @return a string representation of this predicate, including the phone number.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phone", phoneNumber).toString();
    }
}
