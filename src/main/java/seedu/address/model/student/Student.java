package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Email email;
    private final StudentNumber studentNumber;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Optional<String> group;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Email email, Set<Tag> tags, StudentNumber studentNumber) {
        requireAllNonNull(name, email, tags, studentNumber);
        this.name = name;
        this.email = email;
        this.tags.addAll(tags);
        this.studentNumber = studentNumber;
        this.group = Optional.empty();
    }

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Email email, Set<Tag> tags, StudentNumber studentNumber, Optional<String> group) {
        requireAllNonNull(name, email, tags, studentNumber, group);
        this.name = name;
        this.email = email;
        this.tags.addAll(tags);
        this.group = group;
        this.studentNumber = studentNumber;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    public Optional<String> getGroup() {
        return group;
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
    public boolean isSamePerson(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
            && otherStudent.getStudentNumber().equals(getStudentNumber())
            && otherStudent.getName().toString().toLowerCase().equals(getName().toString().toLowerCase());

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
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return name.equals(otherStudent.name)
            && email.equals(otherStudent.email)
            && tags.equals(otherStudent.tags)
            && studentNumber.equals(otherStudent.studentNumber);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email, studentNumber, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", name)
            .add("email", email)
            .add("tags", tags)
            .add("student number", studentNumber)
            .add("group", group.orElse(""))
            .toString();
    }

}
