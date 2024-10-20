package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.group.Group;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Email email;
    private final StudentId studentId;

    // Data fields
    private final Major major;
    private final Year year;
    private final Set<Group> groups = new HashSet<>();
    private final Comment comment;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StudentId studentId, Email email, Major major, Set<Group> groups,
                  Year year, Comment comment) {
        requireAllNonNull(name, studentId, email, major, groups, year, comment);
        this.name = name;
        this.studentId = studentId;
        this.email = email;
        this.major = major;
        this.year = year;
        this.groups.addAll(groups);
        this.comment = comment;
    }

    public Name getName() {
        return name;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Email getEmail() {
        return email;
    }

    public Major getMajor() {
        return major;
    }

    public Year getYear() {
        return year;
    }

    public Comment getComment() {
        return comment;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    /**
     * Retrieves the first {@link Group} from the set of groups.
     *
     * <p>This method returns the first element of the unmodifiable set of groups
     * if available. If the set is empty, it returns {@code null}.</p>
     *
     * @return the first {@link Group} if it exists, or {@code null} if the set
     *         of groups is empty.
     *
     */
    public Group getGroup() {
        Iterator<Group> iterator = getGroups().iterator();
        if (iterator.hasNext()) {
            return iterator.next(); // Returns the first element
        } else {
            return null;
        }
    }

    /**
     * Returns true if both persons have the same StudentId.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getStudentId().equals(getStudentId());
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
                && studentId.equals(otherPerson.studentId)
                && email.equals(otherPerson.email)
                && major.equals(otherPerson.major)
                && year.equals(otherPerson.year);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, studentId, email, major, year, groups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("studentId", studentId)
                .add("email", email)
                .add("major", major)
                .add("year", year)
                .add("groups", groups)
                .add("comment", comment)
                .toString();
    }

}
