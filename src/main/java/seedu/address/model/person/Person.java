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
    private final LastSeen lastSeen;
    private final Organisation organisation;
    private final Set<Tag> tags = new HashSet<>();
    private final Priority priority;
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Organisation organisation,
                LastSeen lastSeen, Set<Tag> tags, Priority priority, Remark remark) {
        requireAllNonNull(name, phone, email, tags, organisation, lastSeen);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organisation = organisation;
        this.lastSeen = lastSeen;
        this.tags.addAll(tags);
        this.priority = priority;
        this.remark = remark;
    }

    public Name getName() {
        return name;
    }

    public Remark getRemark() {
        return remark;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public LastSeen getLastSeen() {
        return lastSeen;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
                && organisation.equals(otherPerson.organisation)
                && lastSeen.equals(otherPerson.lastSeen)
                && tags.equals(otherPerson.tags)
                && priority.equals(otherPerson.priority)
                && remark.equals(otherPerson.remark);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, organisation, lastSeen, tags, priority, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("organisation", organisation)
                .add("last seen", lastSeen)
                .add("tags", tags)
                .add("priority", priority)
                .add("remark", remark)
                .toString();
    }

}
