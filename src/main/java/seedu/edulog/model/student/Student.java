package seedu.edulog.model.student;

import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.model.tag.Tag;

/**
 * Represents a Student in the edulog book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final seedu.edulog.model.student.Phone phone;
    private final seedu.edulog.model.student.Email email;

    // Data fields
    private final seedu.edulog.model.student.Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Fee fee;
    private boolean isPresent = false;

    /**
     * Every field must be present and not null except isPresent. I suggest
     * we keep this constructor so that we do not break all the test cases
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.fee = new Fee(0);
        this.isPresent = false;
    }

    /**
     * New constructor with Fee
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Fee fee) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.fee = fee;
        this.isPresent = false;
    }

    /**
     * New constructor with Fee and isPresent
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Fee fee, boolean isPresent) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.fee = fee;
        this.isPresent = isPresent;
    }

    public Name getName() {
        return name;
    }

    public seedu.edulog.model.student.Phone getPhone() {
        return phone;
    }

    public seedu.edulog.model.student.Email getEmail() {
        return email;
    }

    public seedu.edulog.model.student.Address getAddress() {
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
     * Checks if the student is present.
     *
     * @return {@code true} if the student is present, {@code false} otherwise
     */
    public boolean getIsPresent() {
        return isPresent;
    }

    public Fee getFee() {
        return fee;
    }

    /**
     * Marks the student as present.
     */
    public void mark() {
        isPresent = true;
    }

    /**
     * Marks the student as absent.
     */
    public void unmark() {
        isPresent = false;
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
                && isPresent == otherStudent.isPresent
                && fee.equals(otherStudent.fee);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, isPresent, fee);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("edulog", address)
                .add("tags", tags)
                .add("isPresent", isPresent)
                .add("fee", fee)
                .toString();
    }

}
