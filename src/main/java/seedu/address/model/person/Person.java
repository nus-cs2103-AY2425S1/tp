package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final StudentClass studentClass;
    private final Set<Tag> tags = new HashSet<>();

    private final Set<Group> groups = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StudentClass studentClass, Phone phone, Set<Tag> tags) {
        requireAllNonNull(name, phone, studentClass, tags);
        this.name = name;
        this.studentClass = studentClass;
        this.phone = phone;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a new Person object with a new set of tags
     * with new tags added.
     */
    public Person addTags(Set<Tag> newTags) {
        this.tags.addAll(newTags);
        return new Person(this.name, this.studentClass, this.phone, this.tags);
    }

    /**
     * Returns a new Person object with a new set of tags
     * with specified tags deleted.
     */
    public Person deleteTags(Set<Tag> tagsToBeDeleted) {
        this.tags.removeAll(tagsToBeDeleted);
        return new Person(this.name, this.studentClass, this.phone, this.tags);
    }

    public boolean tagExists(Set<Tag> tags) {
        return this.tags.containsAll(tags);
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
     * Returns an immutable group set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    /**
     * Adds a group to the group field of the person.
     * @param group
     */
    public void addGroups(Group group) {
        requireNonNull(group);
        groups.add(group);
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
                && studentClass.equals(otherPerson.studentClass)
                && phone.equals(otherPerson.phone)
                && tags.equals(otherPerson.tags)
                && groups.equals(otherPerson.groups);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, studentClass, phone, tags, groups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("studentClass", studentClass)
                .add("phone", phone)
                .add("tags", tags)
                .add("groups", groups)
                .toString();
    }

}
