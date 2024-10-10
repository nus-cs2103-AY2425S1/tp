package seedu.address.model.person;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ContactType} matches the specified contact type.
 */
public class ContactTypePredicate implements Predicate<Person> {
    private final ContactType contactType;

    public ContactTypePredicate(ContactType contactType) {
        this.contactType = contactType;
    }

    @Override
    public boolean test(Person person) {
        // getContactType() to be implemented on next iteration
        // to add getContactType() method to the Person class and edit Person class fields
        // return person.getContactType().equals(contactType);
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactTypePredicate)) {
            return false;
        }

        ContactTypePredicate otherPredicate = (ContactTypePredicate) other;
        return contactType.equals(otherPredicate.contactType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("contactType", contactType).toString();
    }
}
