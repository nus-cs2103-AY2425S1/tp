package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code Person}'s features matches any in the list.
 */
public class PersonHasFeaturePredicate implements Predicate<Person> {
    private Tag tag;
    private Phone phone;

    private Email email;
    private Address address;

    /**
     * @param tag
     * @param phone
     * @param email   The email to filter by (can be null).
     * @param address The address to filter by (can be null).
     */
    public PersonHasFeaturePredicate(Tag tag, Phone phone, Email email, Address address) {
        this.tag = tag;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    /**
     * Tests if a {@code Person}'s tag and phone number meet specific criteria.
     *
     * @param person The {@code Person} to test.
     * @return {@code true} if both tag and phone satisfy the conditions, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        return isTagTrue(person.getTag())
                && isPhoneTrue(person.getPhone())
                && isEmailTrue(person.getEmail())
                && isAddressTrue(person.getAddress());
    }

    /**
     * Checks if the specified tag matches the current tag.
     * If the current tag is null, all tags are considered acceptable.
     *
     * @param otherTag The tag to compare.
     * @return {@code true} if the tags match or if the current tag is null, {@code false} otherwise.
     */
    public boolean isTagTrue(Tag otherTag) {
        return tag == null || tag.equals(otherTag);
    }

    /**
     * Checks if the specified phone number matches the current phone number.
     * If the current phone number is null, all phone numbers are considered acceptable.
     *
     * @param otherPhone The phone number to compare.
     * @return {@code true} if the phone numbers match or if the current phone is null, {@code false} otherwise.
     */
    public boolean isPhoneTrue(Phone otherPhone) {
        return phone == null || phone.equals(otherPhone);
    }

    /**
     * Checks if the specified email matches the current email.
     * If the current email is null, all emails are considered acceptable.
     */
    public boolean isEmailTrue(Email otherEmail) {
        return email == null || email.equals(otherEmail);
    }

    /**
     * Checks if the specified address matches the current address.
     * If the current address is null, all addresses are considered acceptable.
     */
    public boolean isAddressTrue(Address otherAddress) {
        return address == null || address.equals(otherAddress);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasFeaturePredicate)) {
            return false;
        }

        PersonHasFeaturePredicate otherPersonHasFeaturePredicate = (PersonHasFeaturePredicate) other;
        return isTagTrue(otherPersonHasFeaturePredicate.tag) && isPhoneTrue(otherPersonHasFeaturePredicate.phone)
                && isEmailTrue(otherPersonHasFeaturePredicate.email)
                && isAddressTrue(otherPersonHasFeaturePredicate.address);
    }
}
