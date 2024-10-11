package keycontacts.model.student;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.tag.Tag;

/**
 * Represents a Student in the student directory.
 * Guarantees: details are present, field values are validated, immutable
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // A LinkedHashSet is used for piano pieces to maintain order of insertion
    private final Set<PianoPiece> pianoPieces = new HashSet<>();

    // Associations
    private final RegularLesson regularLesson;

    /**
     * Constructor for a new student. Uses default associations.
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.tags.addAll(tags);

        this.regularLesson = null;
    }

    /**
     * Constructor for a new student with non-default student associations. Identity and data fields must be
     * present and not null. This is mainly used in {@code AddCommand}
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<PianoPiece> pianoPieces,
                    RegularLesson regularLesson) {
        requireAllNonNull(name, phone, email, address, tags, pianoPieces);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.tags.addAll(tags);
        this.pianoPieces.addAll(pianoPieces);

        this.regularLesson = regularLesson;
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
        Email updatedEmail = editStudentDescriptor.getEmail().orElse(email);
        Address updatedAddress = editStudentDescriptor.getAddress().orElse(address);
        Set<Tag> updatedTags = editStudentDescriptor.getTags().orElse(tags);
        return new Student(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                pianoPieces, regularLesson);
    }

    /**
     * Creates and returns a new {@code Student} with the updated {@code regularLesson}.
     */
    public Student withRegularLesson(RegularLesson regularLesson) {
        return new Student(name, phone, email, address, tags, pianoPieces, regularLesson);
    }

    /**
     * Creates and returns a new {@code Student} with the {@code addedPianoPieces} added.
     */
    public Student withAddedPianoPieces(Set<PianoPiece> addedPianoPieces) {
        Set<PianoPiece> updatedPianoPieces = new HashSet<>(pianoPieces);
        updatedPianoPieces.addAll(addedPianoPieces);

        return new Student(name, phone, email, address, tags, updatedPianoPieces, regularLesson);
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
                && email.equals(otherStudent.email)
                && address.equals(otherStudent.address)
                && tags.equals(otherStudent.tags)
                && pianoPieces.equals(otherStudent.pianoPieces)
                && getRegularLesson().equals(otherStudent.getRegularLesson());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, pianoPieces, regularLesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("regularLesson", regularLesson)
                .toString();
    }

}
