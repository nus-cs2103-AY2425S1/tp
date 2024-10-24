package seedu.address.model.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    private final DaysAttended daysAttended;

    /**
     * Every field must be present and not null.
     *
     * @param name     Name of the student
     * @param gender    Gender of the student
     * @param phone    Phone number of the student
     * @param email    Email of the student
     * @param address  Address of the student
     * @param tags     Tags associated with the student
     * @param subjects  Subject the student is taking
     * @param classes  Set of class names the student is attending
     */
    public Student(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags,
                   Set<Subject> subjects, Set<String> classes, DaysAttended daysAttended) {
        super(name, gender, phone, email, address, addStudentTag(tags), subjects, classes);
        this.daysAttended = daysAttended;
    }

    private static Set<Tag> addStudentTag(Set<Tag> tags) {
        Set<Tag> modifiedTags = new HashSet<>(tags);
        modifiedTags.add(new Tag("student"));
        return modifiedTags;
    }

    public DaysAttended getDaysAttended() {
        return daysAttended;
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student: ")
                .append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Subject: ")
                .append(getSubjects())
                .append("; Classes: ")
                .append(String.join(", ", getClasses()))
                .append("; Days attended: ")
                .append(getDaysAttended());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(tag -> builder.append(tag).append(" "));
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getGender().equals(getGender())
                && otherStudent.getPhone().equals(getPhone())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getAddress().equals(getAddress())
                && otherStudent.getSubjects().equals(getSubjects())
                && otherStudent.getClasses().equals(getClasses())
                && otherStudent.getTags().equals(getTags())
                && otherStudent.getDaysAttended().equals(getDaysAttended());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGender(), getPhone(), getEmail(), getAddress(),
            getSubjects(), getClasses(), getTags(), getDaysAttended());
    }

    /**
     * Marks the attendance of the student.
     */
    public void markAttendance() {
        this.daysAttended.increment();
    }

    /**
     * Unmarks the attendance of the student.
     */
    public void unmarkAttendance() {
        this.daysAttended.decrement();
    }

    /**
     * Resets the attendance of the student.
     */
    public void resetAttendance() {
        this.daysAttended.reset();
    }
}
