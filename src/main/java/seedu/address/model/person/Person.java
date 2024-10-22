package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
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
    private final Course course;
    private final Set<Tag> tags = new HashSet<>();
    private final GradeList gradeList;
    private final AttendanceList attendanceList;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Course course, Set<Tag> tags, GradeList gradeList,
            AttendanceList attendanceList) {
        requireAllNonNull(name, phone, email, course, tags, gradeList, attendanceList);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.course = course;
        this.tags.addAll(tags);
        this.gradeList = gradeList;
        this.attendanceList = attendanceList;
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

    public Course getCourse() {
        return course;
    }

    public GradeList getGradeList() {
        return gradeList;
    }

    public AttendanceList getAttendanceList() {
        return attendanceList;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Add grade to gradeList
     *
     * @param grade New grade to be added
     * @return new immutable Person
     */
    public Person addGrade(Grade grade) {
        requireAllNonNull(grade);

        GradeList newGradeList = this.gradeList.addGrade(grade);

        return new Person(this.name, this.phone, this.email, this.course, this.tags, newGradeList,
                          this.attendanceList);
    }

    /**
     * Remove grade from gradeList based on index.
     *
     * @param testName The name of the test whose grade needs to be removed.
     * @return new immutable Person
     */
    public Person removeGrade(String testName) {
        requireNonNull(testName);

        GradeList newGradelist = this.gradeList.removeGrade(testName);

        return new Person(this.name, this.phone, this.email, this.course, this.tags, newGradelist,
                          this.attendanceList);
    }

    /**
     * Retrieves the grade for a specific test from the person's GradeList.
     * Returns the {@code Grade} object if found, or null if no grade is recorded for the test.
     *
     * @param testName The name of the test.
     * @return The {@code Grade} object for the test, or null if no grade is found.
     */
    public Grade getGrade(String testName) {
        requireNonNull(testName);
        return this.gradeList.getGrade(testName);
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
     * Sets the attendance for a specific date and returns a new Person object with the updated attendance list.
     *
     * @param date The date for which the attendance is to be set.
     * @param attendance The attendance status to be set for the specified date.
     * @return A new Person object with the updated attendance list.
     */
    public Person setAttendance(LocalDateTime date, Attendance attendance) {
        AttendanceList newAttendanceList = attendanceList.setAttendance(date, attendance);
        return new Person(name, phone, email, course, tags, gradeList, newAttendanceList);
    }

    /**
     * Removes the attendance record for the specified date from the person's attendance list.
     *
     * @param date The date of the attendance record to be removed.
     * @return A new Person object with the updated attendance list.
     */
    public Person removeAttendance(LocalDateTime date) {
        AttendanceList newAttendanceList = attendanceList.removeAttendance(date);
        return new Person(name, phone, email, course, tags, gradeList, newAttendanceList);
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
                && course.equals(otherPerson.course)
                && tags.equals(otherPerson.tags)
                && gradeList.equals(otherPerson.gradeList)
                && attendanceList.equals(otherPerson.attendanceList);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, course, tags, gradeList, attendanceList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("course", course)
                .add("tags", tags)
                .add("gradeList", gradeList)
                .add("attendanceList", attendanceList)
                .toString();
    }

}
