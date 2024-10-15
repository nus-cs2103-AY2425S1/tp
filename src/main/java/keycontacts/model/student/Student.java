package keycontacts.model.student;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.pianopiece.PianoPiece;

/**
 * Represents a Student in the student directory.
 * Guarantees: details are present, field values are validated, immutable
 */
public class Student {

    // Data fields
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final GradeLevel gradeLevel;

    // Associations
    private final Set<PianoPiece> pianoPieces = new HashSet<>();
    private final RegularLesson regularLesson;
    private final Set<CancelledLesson> cancelledLessons = new HashSet<>();

    /**
     * Constructor for a new student. Uses default associations.
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Address address, GradeLevel gradeLevel) {
        requireAllNonNull(name, phone, address, gradeLevel);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gradeLevel = gradeLevel;
        this.regularLesson = null;
    }

    /**
     * Constructor for a new student with non-default student associations. Identity and data fields must be
     * present and not null.
     */
    public Student(Name name, Phone phone, Address address, GradeLevel gradeLevel,
                   Set<PianoPiece> pianoPieces, RegularLesson regularLesson) {
        requireAllNonNull(name, phone, address, gradeLevel, pianoPieces);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gradeLevel = gradeLevel;
        this.pianoPieces.addAll(pianoPieces);
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

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    /**
     * Returns an immutable piano piece set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<PianoPiece> getPianoPieces() {
        return Collections.unmodifiableSet(pianoPieces);
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
        GradeLevel updatedGradeLevel = editStudentDescriptor.getGradeLevel().orElse(this.gradeLevel);
        return new Student(updatedName, updatedPhone, updatedAddress, updatedGradeLevel, pianoPieces, regularLesson);
    }

    /**
     * Creates and returns a new {@code Student} with the updated {@code regularLesson}.
     */
    public Student withRegularLesson(RegularLesson regularLesson) {
        return new Student(name, phone, address, gradeLevel, pianoPieces, regularLesson);
    }

    /**
     * Creates and returns a new {@code Student} with the {@code addedPianoPieces} added.
     */
    public Student withAddedPianoPieces(Set<PianoPiece> addedPianoPieces) {
        Set<PianoPiece> updatedPianoPieces = new HashSet<>(pianoPieces);
        updatedPianoPieces.addAll(addedPianoPieces);

        return new Student(name, phone, address, gradeLevel, updatedPianoPieces, regularLesson);
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
                && gradeLevel.equals(otherStudent.gradeLevel)
                && pianoPieces.equals(otherStudent.pianoPieces)
                && getRegularLesson().equals(otherStudent.getRegularLesson());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, gradeLevel, pianoPieces, regularLesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("gradeLevel", gradeLevel)
                .add("pianoPieces", pianoPieces)
                .add("regularLesson", regularLesson)
                .toString();
    }

    /**
     * A function to check if the {@code date} and {@code startTime} parameters match {@code regularLesson}.
     *
     * @param date date of the lesson to match with
     * @param startTime start time of the lesson to match with
     * @return a boolean representing whether the inputted params match {@code regularLesson}
     */
    public boolean matchesLesson(Date date, Time startTime) {
        return this.getRegularLesson()
                .map(lesson -> lesson.isOnDayAndTime(date.convertToDay(), startTime))
                .orElse(false);
    }

    /**
     * Adds a cancelled lesson to {@code cancelledLessons}.
     * @param cancelledLesson the cancelled lesson to be added
     */
    public void addCancelledLesson(CancelledLesson cancelledLesson) {
        this.cancelledLessons.add(cancelledLesson);
    }

}
