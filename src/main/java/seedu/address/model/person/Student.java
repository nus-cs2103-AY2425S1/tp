package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Represents a student in the address book.
 */
public class Student extends Person {

    private final Name parentName;
    private final Phone parentPhone;
    private final Email parentEmail;
    private final Grade grade;

    /**
     * Constructs a {@code Student} with the given details.
     * Students constructed with this constructor will have isPinned set to false by default.
     */
    public Student(Name name, Phone phone, Email email, Address address, Name parentName, Phone parentPhone,
            Email parentEmail, Grade grade, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.grade = grade;
    }

    /**
     * Constructs a {@code Student} with the given details.
     */
    public Student(Name name, Phone phone, Email email, Address address, Name parentName, Phone parentPhone,
                   Email parentEmail, Grade grade, Set<Tag> tags, boolean isPinned) {
        super(name, phone, email, address, tags, isPinned);
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.grade = grade;
    }

    /**
     * Constructs a {@code Student} with the given {@code Person} as a base.
     */
    public Student(Person person, Name parentName, Phone parentPhone, Email parentEmail, Grade grade) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags());
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.grade = grade;
    }

    public Name getParentName() {
        return parentName;
    }

    public Phone getParentPhone() {
        return parentPhone;
    }

    public Email getParentEmail() {
        return parentEmail;
    }

    public Grade getGrade() {
        return grade;
    }

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
        return super.equals(otherStudent) && parentName.equals(otherStudent.parentName)
                && parentPhone.equals(otherStudent.parentPhone) && parentEmail.equals(otherStudent.parentEmail)
                && grade.equals(otherStudent.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(), this.getAddress(),
                this.getParentName(), this.getParentPhone(), this.getParentEmail(), this.getGrade(),
                this.getTags(), this.getPinned());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("parent name", this.getParentName())
                .add("parent phone", this.getParentPhone())
                .add("parent email", this.getParentEmail())
                .add("grade", this.getGrade())
                .add("tags", this.getTags())
                .toString();
    }

    @Override
    public String toMessageString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Parent Name: ")
                .append(getParentName())
                .append("; Parent Phone: ")
                .append(getParentPhone())
                .append("; Parent Email: ")
                .append(getParentEmail())
                .append("; Grade: ")
                .append(getGrade())
                .append("; Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
