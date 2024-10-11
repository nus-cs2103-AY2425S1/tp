package keycontacts.model.student;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.model.lesson.RegularLesson;

/**
 * Represents a Student in the student directory.
 * Guarantees: details are present, field values are validated, immutable
 */
public class Student {

    // Data fields
    private final Name name;
    private final Phone phone;
    private final Address address;

    // Associations
    private final RegularLesson regularLesson;

    /**
     * Constructor for a new student. Uses default associations.
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Address address) {
        requireAllNonNull(name, phone, address);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.regularLesson = null;
    }

    /**
     * Constructor for a new student with non-default student associations. Identity and data fields must be
     * present and not null.
     */
    public Student(Name name, Phone phone, Address address, RegularLesson regularLesson) {
        requireAllNonNull(name, phone, address);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.regularLesson = regularLesson;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Optional<RegularLesson> getRegularLesson() {
        return Optional.ofNullable(regularLesson);
    }

    /**
     * Returns string representation of the {@code regularLesson}
     */
    public String getRegularLessonString() {
        return Optional.ofNullable(regularLesson).map(RegularLesson::toString).orElse(null);
    }

    /**
     * Returns user-friendly display string of the {@code regularLesson}.
     */
    public String getRegularLessonDisplay() {
        return Optional.ofNullable(regularLesson).map(RegularLesson::toDisplay)
                .orElse("No regular lesson scheduled");
    }

    /**
     * Creates and returns a new {@code Student} with the details edited with {@code editStudentDescriptor}.
     */
    public Student withEditStudentDescriptor(EditStudentDescriptor editStudentDescriptor) {
        Name updatedName = editStudentDescriptor.getName().orElse(name);
        Phone updatedPhone = editStudentDescriptor.getPhone().orElse(phone);
        Address updatedAddress = editStudentDescriptor.getAddress().orElse(address);

        return new Student(updatedName, updatedPhone, updatedAddress, regularLesson);
    }

    /**
     * Creates and returns a new {@code Student} with the updated {@code regularLesson}.
     */
    public Student withRegularLesson(RegularLesson regularLesson) {
        return new Student(name, phone, address, regularLesson);
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
     * Returns true if both students have the same identity, data fields and associations.
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
                && address.equals(otherStudent.address)
                && getRegularLesson().equals(otherStudent.getRegularLesson());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, regularLesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("regularLesson", regularLesson)
                .toString();
    }

}
