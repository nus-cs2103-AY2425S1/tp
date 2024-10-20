package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code Person}'s features matches any in the list.
 */
public class PersonHasFeaturePredicate implements Predicate<Person> {
    private Tag tag;
    private Phone phone;

    /**
     * @param tag
     * @param phone
     *
     */
    public PersonHasFeaturePredicate(Tag tag, Phone phone) {
        this.tag = tag;
        this.phone = phone;
    }
    /**
     * Tests if a {@code Person}'s tag and phone number meet specific criteria.
     *
     * @param person The {@code Person} to test.
     * @return {@code true} if both tag and phone satisfy the conditions, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        return isTagTrue(person.getTag()) && isPhoneTrue(person.getPhone());
    }

    /**
     * Checks if the specified tag matches the current tag.
     * If the current tag is null, all tags are considered acceptable.
     *
     * @param otherTag The tag to compare.
     * @return {@code true} if the tags match or if the current tag is null, {@code false} otherwise.
     */
    public boolean isTagTrue(Tag otherTag) {
        if (tag == null) { //all tags are acceptable
            return true;
        }
        return tag.equals(otherTag);
    }

    /**
     * Checks if the specified phone number matches the current phone number.
     * If the current phone number is null, all phone numbers are considered acceptable.
     *
     * @param otherPhone The phone number to compare.
     * @return {@code true} if the phone numbers match or if the current phone is null, {@code false} otherwise.
     */
    public boolean isPhoneTrue(Phone otherPhone) {
        if (phone == null) { //all phone numbers are acceptable
            return true;
        }
        return phone.equals(otherPhone);
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
        return isTagTrue(otherPersonHasFeaturePredicate.tag) && isPhoneTrue(otherPersonHasFeaturePredicate.phone);
    }
}
