package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // New fields
    private final Set<Interest> interests = new HashSet<>();
    private final University university;
    private final Major major;
    private final WorkExp workExp;

    /**
     * Every field must be present and not null.
     * New fields for university, major, interest, and experience are added.
     * Interest and experience are initialized to empty strings.
     */
    public Person(Name name, Phone phone, Email email, Address address, WorkExp workExp, Set<Tag> tags,
                  University university, Major major, Set<Interest> interests) {
        requireAllNonNull(name, phone, email, address, tags, university, major);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.workExp = workExp;
        this.tags.addAll(tags);
        this.university = university;
        this.major = major;
        this.interests.addAll(interests);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public University getUniversity() {
        return university;
    }

    public Major getMajor() {
        return major;
    }

    public Set<Interest> getInterests() {
        return Collections.unmodifiableSet(interests);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public WorkExp getWorkExp() {
        return workExp;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && workExp.equals(otherPerson.workExp)
                && tags.equals(otherPerson.tags)
                && university.equals(otherPerson.university)
                && major.equals(otherPerson.major)
                && interests.equals(otherPerson.interests);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, workExp, tags, university, major, interests);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("workExp", workExp)
                .add("tags", tags)
                .add("university", university)
                .add("major", major)
                .add("interests", interests)
                .toString();
    }
}
