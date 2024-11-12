package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import javafx.beans.property.IntegerProperty;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    public static final String STUDENT_TYPE = "student";
    private final DaysAttended daysAttended;
    private final Name nextOfKinName;
    private final Phone emergencyContact;

    /**
     * Every field must be present and not null.
     *
     * @param name             Name of the student
     * @param gender           Gender of the student
     * @param phone            Phone number of the student
     * @param email            Email of the student
     * @param address          Address of the student
     * @param tags             Tags associated with the student
     * @param subjects         Subject the student is taking
     * @param classes          Set of class names the student is attending
     * @param daysAttended     Number of days the student attended
     * @param nextOfKinName    Name of the student's next of kin
     * @param emergencyContact Emergency contact number
     */
    public Student(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags,
                   Set<Subject> subjects, Set<String> classes, DaysAttended daysAttended,
                   Name nextOfKinName, Phone emergencyContact) {
        super(name, gender, phone, email, address, tags, subjects, classes);
        this.daysAttended = daysAttended;
        this.nextOfKinName = nextOfKinName;
        this.emergencyContact = emergencyContact;
    }

    @Override
    public DaysAttended getDaysAttended() {
        return daysAttended;
    }

    @Override
    public int getDaysAttendedValue() {
        return daysAttended.getValue();
    }

    public Name getNextOfKinName() {
        return nextOfKinName;
    }

    public Phone getEmergencyContact() {
        return emergencyContact;
    }

    public IntegerProperty daysAttendedProperty() {
        return daysAttended.daysAttendedProperty();
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
                .append(getDaysAttended())
                .append("; Next of Kin: ")
                .append(getNextOfKinName())
                .append("; Emergency Contact: ")
                .append(getEmergencyContact());

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
                && otherStudent.getDaysAttended().equals(getDaysAttended())
                && otherStudent.getNextOfKinName().equals(getNextOfKinName())
                && otherStudent.getEmergencyContact().equals(getEmergencyContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGender(), getPhone(), getEmail(), getAddress(),
                getSubjects(), getClasses(), getTags(), getDaysAttended(), getNextOfKinName(), getEmergencyContact());
    }

    /**
     * Increments the attendance of the student by one day.
     * This method creates a new {@code Person} instance with updated attendance.
     *
     * @return A new {@code Person} instance representing the student with incremented attendance.
     */
    public Person withIncrementedAttendance() {
        DaysAttended updatedDaysAttended = daysAttended.incremented();

        return Person.createPerson(STUDENT_TYPE, getName(), getGender(), getPhone(), getEmail(), getAddress(),
                getTags(), getSubjects(), getClasses(), updatedDaysAttended, getNextOfKinName(), getEmergencyContact());
    }

    /**
     * Decrements the attendance of the student by one day.
     * This method creates a new {@code Person} instance with updated attendance.
     *
     * @return A new {@code Person} instance representing the student with decremented attendance.
     */
    public Person withDecrementedAttendance() throws CommandException {
        DaysAttended updatedDaysAttended = daysAttended.decremented();

        return Person.createPerson(STUDENT_TYPE, getName(), getGender(), getPhone(), getEmail(), getAddress(),
                getTags(), getSubjects(), getClasses(), updatedDaysAttended, getNextOfKinName(), getEmergencyContact());
    }

    /**
     * Resets the attendance of the student to zero days.
     * This method creates a new {@code Person} instance with attendance reset.
     *
     * @return A new {@code Person} instance representing the student with reset attendance.
     */
    public Person withResetAttendance() {
        DaysAttended updatedDaysAttended = daysAttended.reset();

        return Person.createPerson(STUDENT_TYPE, getName(), getGender(), getPhone(), getEmail(), getAddress(),
                getTags(), getSubjects(), getClasses(), updatedDaysAttended, getNextOfKinName(), getEmergencyContact());
    }

    @Override
    public String getType() {
        return STUDENT_TYPE;
    }
}
