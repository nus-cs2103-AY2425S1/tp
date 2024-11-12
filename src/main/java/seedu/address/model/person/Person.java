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
import seedu.address.model.tag.Tags;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields

    private final Name name;
    private final Phone phone;

    // Data fields
    private final StudentClass studentClass;
    private final Tags tags = new Tags();

    private final Set<Group> groups = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StudentClass studentClass, Phone phone, Tags tags) {
        requireAllNonNull(name, phone, studentClass);
        this.name = name;
        this.studentClass = studentClass;
        this.phone = phone;
        if (tags != null) {
            this.tags.addAllTags(tags);
        }
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
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTagSet() {
        return Collections.unmodifiableSet(this.tags.getTags());
    }

    public Tags getTags() {
        return this.tags;
    }

    /**
     * Returns a new Person object with a new set of tags
     * with new tags added.
     */
    public Person addTags(Tags newTags) {
        this.tags.addAllTags(newTags);
        return new Person(this.name, this.studentClass, this.phone, this.tags);
    }

    /**
     * Returns a new Person object with a new set of tags
     * with specified tags deleted.
     */
    public Person deleteTags(Tags tagsToBeDeleted) {
        this.tags.removeAllTags(tagsToBeDeleted);
        return new Person(this.name, this.studentClass, this.phone, this.tags);
    }

    /**
     * Returns true if tags already exist in the existing tags.
     */
    public boolean tagExists(Tags tags) {
        return tags.tagExists(this.tags);
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
     * Returns an immutable group set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    /**
     * Adds a group to the group field of the person.
     *
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
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && studentClass.equals(otherPerson.studentClass)
                && phone.equals(otherPerson.phone)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentClass, phone, tags);
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
