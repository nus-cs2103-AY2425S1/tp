package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final int personId;

    private final PersonDescriptor personDescriptor;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.personDescriptor = new PersonDescriptor(
                name, phone, email, address, tags
        );

        // Increment the static counter and assign a unique ID to the person
        this.personId = 0;
    }


    /**
     * Builds a person given a personId and a personDescriptor.
     * @param personId
     * @param personDescriptor
     */
    public Person(int personId, PersonDescriptor personDescriptor) {
        this.personId = personId;
        this.personDescriptor = personDescriptor;
    }

    public int getPersonId() {
        return personId;
    }

    public Name getName() {
        return personDescriptor.getName();
    }

    public Phone getPhone() {
        return personDescriptor.getPhone();
    }

    public Email getEmail() {
        return personDescriptor.getEmail();
    }

    public Address getAddress() {
        return personDescriptor.getAddress();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return personDescriptor.getTags();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        return personDescriptor.isSamePerson(otherPerson.personDescriptor);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return personDescriptor.equals(otherPerson.personDescriptor);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personId, personDescriptor);
    }

    @Override
    public String toString() {
        return personDescriptor.toString();
    }

}
