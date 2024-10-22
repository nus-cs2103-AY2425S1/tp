package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
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

    // Each student can only attend up to 12 tutorials.
    public static final int MAXIMUM_TUTORIALS = 12;

    // Identity fields
    private final Name name;
    private final StudentId studentId;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Map<Tutorial, AttendanceStatus> tutorials = new LinkedHashMap<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StudentId studentId, Phone phone, Email email, Set<Tag> tags,
                  Map<Tutorial, AttendanceStatus> tutorials) {
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
    public Map<Tutorial, AttendanceStatus> getTutorials() {
        return Collections.unmodifiableMap(tutorials);
    }

    /**
     * Returns the relevant CSS class based on the status of the tutorial.
     */
    public String getAttendanceCssClass(String index) {
        if (tutorials.get(new Tutorial(index)) == AttendanceStatus.NOT_TAKEN_PLACE) {
            return "tutorial-not-taken-place";
        } else if (tutorials.get(new Tutorial(index)) == AttendanceStatus.PRESENT) {
            return "tutorial-present";
        } else {
            return "tutorial-absent";
        }
    }

    public AttendanceStatus getAttendanceStatus(Tutorial tutorial) {
        return tutorials.get(tutorial);
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
