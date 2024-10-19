package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    /**
     * Represents the Attendance Status of a tutorial. Mainly used for GUI generation and testing.
     */
    public enum AttendanceStatus {
        ATTENDED,
        ABSENT,
        NOT_TAKEN_PLACE
    }

    // Identity fields
    private final Name name;
    private final StudentId studentId;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Map<Tutorial, Boolean> tutorials = new HashMap<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StudentId studentId, Phone phone, Email email, Set<Tag> tags,
                  Map<Tutorial, Boolean> tutorials) {
        requireAllNonNull(name, studentId, phone, email, tags, tutorials);
        this.name = name;
        this.studentId = studentId;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
        this.tutorials.putAll(tutorials);
    }

    public Name getName() {
        return name;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable tutorial map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<Tutorial, Boolean> getTutorials() {
        return Collections.unmodifiableMap(tutorials);
    }

    /**
     * Returns true if the person has attended the stated tutorial, false otherwise.
     */
    public AttendanceStatus hasAttendedTutorial(String index) {
        if (tutorials.containsKey(new Tutorial(index))) {
            return tutorials.get(new Tutorial(index))
                    ? AttendanceStatus.ATTENDED
                    : AttendanceStatus.ABSENT;
        }

        return AttendanceStatus.NOT_TAKEN_PLACE;
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
                && (otherPerson.getName().equals(getName())
                || otherPerson.getStudentId().equals(getStudentId())
                || otherPerson.getPhone().equals(getPhone())
                || otherPerson.getEmail().equals(getEmail()));
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
                && studentId.equals(otherPerson.studentId)
                && email.equals(otherPerson.email)
                && tags.equals(otherPerson.tags)
                && tutorials.equals(otherPerson.tutorials);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, studentId, phone, email, tags, tutorials);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("studentID", studentId)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .add("tutorials", tutorials) // Text Representation, should update to GUI in the future
                .toString();
    }

}
