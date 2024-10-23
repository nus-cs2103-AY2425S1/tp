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
import keycontacts.model.lesson.MakeupLesson;
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
    private final Set<MakeupLesson> makeupLessons = new HashSet<>();

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
                   Set<PianoPiece> pianoPieces, RegularLesson regularLesson, Set<CancelledLesson> cancelledLessons,
                   Set<MakeupLesson> makeupLessons) {
        requireAllNonNull(name, phone, address, gradeLevel, pianoPieces, cancelledLessons, makeupLessons);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gradeLevel = gradeLevel;
        this.pianoPieces.addAll(pianoPieces);
        this.regularLesson = regularLesson;
        this.cancelledLessons.addAll(cancelledLessons);
        this.makeupLessons.addAll(makeupLessons);
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

    public RegularLesson getRegularLesson() {
        return regularLesson;
    }

    /**
     * Returns {@code regularLesson} wrapped in a Optional.
     */
    public Optional<RegularLesson> getRegularLessonOptional() {
        return Optional.ofNullable(regularLesson);
    }

    /**
     * Returns an immutable cancelled lesson set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<CancelledLesson> getCancelledLessons() {
        return Collections.unmodifiableSet(cancelledLessons);
    }

    public Set<MakeupLesson> getMakeupLessons() {
        return Collections.unmodifiableSet(makeupLessons);
    }

    /**
     * Returns string representation of the {@code regularLesson}
     */
    public String getRegularLessonString() {
        return getRegularLessonOptional().map(RegularLesson::toString).orElse(null);
    }

    /**
     * Returns user-friendly display string of the {@code regularLesson}.
     */
    public String getRegularLessonDisplay() {
        return getRegularLessonOptional().map(RegularLesson::toDisplay)
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

        return new Updater().withName(updatedName)
                .withPhone(updatedPhone)
                .withAddress(updatedAddress)
                .withGradeLevel(updatedGradeLevel)
                .update();
    }

    /**
     * Creates and returns a new {@code Student} with the {@code addedPianoPieces} added.
     */
    public Student withAddedPianoPieces(Set<PianoPiece> addedPianoPieces) {
        Set<PianoPiece> updatedPianoPieces = new HashSet<>(pianoPieces);
        updatedPianoPieces.addAll(addedPianoPieces);

        return new Updater().withPianoPieces(updatedPianoPieces).update();
    }

    /**
     * Creates and returns a new {@code Student} with the {@code removedPianoPieces} removed.
     */
    public Student withRemovedPianoPieces(Set<PianoPiece> removedPianoPieces) {
        Set<PianoPiece> updatedPianoPieces = new HashSet<>(pianoPieces);
        updatedPianoPieces.removeAll(removedPianoPieces);

        return new Updater().withPianoPieces(updatedPianoPieces).update();
    }


    /**
     * Creates and returns a new {@code Student} with the updated {@code regularLesson}.
     */
    public Student withRegularLesson(RegularLesson regularLesson) {
        return new Updater().withRegularLesson(regularLesson).update();
    }

    /**
     * Returns a new student with an additional {@code CancelledLesson}.
     */
    public Student withAddedCancelledLesson(CancelledLesson cancelledLesson) {
        Set<CancelledLesson> updatedCancelledLessons = new HashSet<>(cancelledLessons);
        updatedCancelledLessons.add(cancelledLesson);

        return new Updater().withCancelledLessons(updatedCancelledLessons).update();
    }

    /**
     * Creates and returns a new {@code Student} with the {@code makeupLesson} added.
     */
    public Student withAddedMakeupLesson(MakeupLesson makeupLesson) {
        Set<MakeupLesson> updatedMakeupLessons = new HashSet<>(makeupLessons);
        updatedMakeupLessons.add(makeupLesson);

        return new Updater().withMakeupLessons(updatedMakeupLessons).update();
    }

    /**
     * Creates and returns a new {@code Student} with the {@code makeupLesson} removed.
     */
    public Student withoutMakeupLesson(MakeupLesson makeupLesson) {
        Set<MakeupLesson> updatedMakeupLessons = new HashSet<>(makeupLessons);
        updatedMakeupLessons.remove(makeupLesson);

        return new Updater().withMakeupLessons(updatedMakeupLessons).update();
    }

    /**
     * Returns a new student with an remove {@Code RegularLesson}.
     */
    public Student withoutRegularLesson() {
        return new Updater().withoutRegularLesson().update();
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
                && getRegularLessonOptional().equals(otherStudent.getRegularLessonOptional())
                && cancelledLessons.equals(otherStudent.cancelledLessons)
                && makeupLessons.equals(otherStudent.makeupLessons);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, gradeLevel, pianoPieces, regularLesson,
            cancelledLessons, makeupLessons);
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
                .add("cancelledLessons", cancelledLessons)
                .add("makeupLessons", makeupLessons)
                .toString();
    }

    /**
     * Returns true if the {@code date} and {@code startTime} of {@code regularLesson} match the parameters.
     */
    public boolean matchesLesson(Date date, Time startTime) {
        return this.getRegularLessonOptional()
                .map(lesson -> lesson.isOnDayAndTime(date.convertToDay(), startTime))
                .orElse(false);
    }

    /**
     * Returns an {@code Optional<MakeupLesson>} if the student has a makeup lesson matching the paramters.
     */
    public Optional<MakeupLesson> findMakeupLesson(Date date, Time startTime) {
        return this.getMakeupLessons()
                .stream()
                .filter(ml -> ml.getLessonDate().equals(date) && ml.getStartTime().equals(startTime))
                .findFirst();
    }

    /**
     *  Inner private class for updating the student object.
     *  {@code with...} methods in {@code Student} serve as an abstraction over the inner methods.
     */
    private class Updater {
        private Name name = Student.this.name;
        private Phone phone = Student.this.phone;
        private Address address = Student.this.address;
        private GradeLevel gradeLevel = Student.this.gradeLevel;
        private Set<PianoPiece> pianoPieces = new HashSet<>(Student.this.pianoPieces);
        private RegularLesson regularLesson = Student.this.regularLesson;
        private Set<CancelledLesson> cancelledLessons = new HashSet<>(Student.this.cancelledLessons);
        private Set<MakeupLesson> makeupLessons = new HashSet<>(Student.this.makeupLessons);

        private Updater withName(Name name) {
            this.name = name;
            return this;
        }

        private Updater withPhone(Phone phone) {
            this.phone = phone;
            return this;
        }

        private Updater withAddress(Address address) {
            this.address = address;
            return this;
        }

        private Updater withGradeLevel(GradeLevel gradeLevel) {
            this.gradeLevel = gradeLevel;
            return this;
        }

        private Updater withPianoPieces(Set<PianoPiece> pianoPieces) {
            this.pianoPieces = pianoPieces;
            return this;
        }

        private Updater withRegularLesson(RegularLesson regularLesson) {
            this.regularLesson = regularLesson;
            return this;
        }

        private Updater withCancelledLessons(Set<CancelledLesson> cancelledLessons) {
            this.cancelledLessons = cancelledLessons;
            return this;
        }

        private Updater withMakeupLessons(Set<MakeupLesson> makeupLessons) {
            this.makeupLessons = makeupLessons;
            return this;
        }

        private Updater withoutRegularLesson() {
            this.regularLesson = null;
            return this;
        }

        private Student update() {
            return new Student(name, phone, address, gradeLevel, pianoPieces, regularLesson, cancelledLessons,
                    makeupLessons);
        }
    }
}
