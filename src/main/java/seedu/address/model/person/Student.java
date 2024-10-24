package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Represents a student in the address book.
 */
public class Student extends Person {

    private final Education education;
    private final Grade grade;
    private final Name parentName;
    private final Phone parentPhone;
    private final Email parentEmail;


    /**
     * Constructs a {@code Student} with the given details.
     * Students constructed with this constructor will have isPinned set to false by default.
     */
    public Student(Name name, Phone phone, Email email, Address address, Education education, Grade grade,
                   Name parentName, Phone parentPhone, Email parentEmail, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.education = education;
        this.grade = grade;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    /**
     * Constructs a {@code Student} with the given details.
     */
    public Student(Name name, Phone phone, Email email, Address address, Education education, Grade grade,
                   Name parentName, Phone parentPhone, Email parentEmail, Set<Tag> tags, boolean isPinned) {
        super(name, phone, email, address, tags, isPinned);
        this.education = education;
        this.grade = grade;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    /**
     * Constructs a {@code Student} with the given {@code Person} as a base.
     */
    public Student(Person person, Education education, Grade grade, Name parentName, Phone parentPhone,
                   Email parentEmail) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags());
        this.education = education;
        this.grade = grade;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    public Education getEducation() {
        return education;
    }

    public Grade getGrade() {
        return grade;
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
        return super.equals(otherStudent) && education.equals(otherStudent.education)
                && grade.equals(otherStudent.grade) && parentName.equals(otherStudent.parentName)
                && parentPhone.equals(otherStudent.parentPhone) && parentEmail.equals(otherStudent.parentEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(), this.getAddress(), this.getEducation(),
                this.getGrade(), this.getParentName(), this.getParentPhone(), this.getParentEmail(), this.getTags(),
                this.getPinned());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("education", this.getEducation())
                .add("grade", this.getGrade())
                .add("parent name", this.getParentName())
                .add("parent phone", this.getParentPhone())
                .add("parent email", this.getParentEmail())
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
                .append("; Education: ")
                .append(getEducation())
                .append("; Grade: ")
                .append(getGrade())
                .append("; Parent Name: ")
                .append(getParentName())
                .append("; Parent Phone: ")
                .append(getParentPhone())
                .append("; Parent Email: ")
                .append(getParentEmail())
                .append("; Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
