package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
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
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final StudentId studentId;
    private final TutorialClass tutorialClass;
    private final Set<Tag> tags = new HashSet<>();

    private final PresentDates presentDates;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address,
                   StudentId studentId, TutorialClass tutorialClass, Set<Tag> tags, PresentDates presentDates) {
        requireAllNonNull(name, phone, email, address, studentId, tutorialClass, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.presentDates = presentDates != null ? presentDates : new PresentDates(new ArrayList<>());
        this.studentId = studentId;
        this.tutorialClass = tutorialClass;
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

    public PresentDates getPresentDates() {
        return presentDates;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
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
                && phone.equals(otherStudent.phone)
                && email.equals(otherStudent.email)
                && address.equals(otherStudent.address)
                && tags.equals(otherStudent.tags)
                && studentId.equals(otherStudent.studentId)
                && tutorialClass.equals(otherStudent.tutorialClass);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, studentId, tutorialClass, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("student id", studentId)
                .add("tutorial class", tutorialClass)
                .add("tags", tags)
                .toString();
    }

}
