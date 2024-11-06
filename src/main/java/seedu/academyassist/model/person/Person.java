package seedu.academyassist.model.person;

import static seedu.academyassist.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.academyassist.commons.util.CollectionUtil;
import seedu.academyassist.commons.util.ToStringBuilder;

/**
 * Represents a Person(student) in the management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Ic ic;
    private final YearGroup yearGroup;
    private final StudentId studentId;
    private final Set<Subject> subjects = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Ic ic, YearGroup yearGroup, StudentId studentId,
                  Set<Subject> subjects) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, ic, yearGroup, studentId, subjects);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.ic = ic;
        this.yearGroup = yearGroup;
        this.studentId = studentId;
        this.subjects.addAll(subjects);
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
    public Ic getIc() {
        return ic;
    }
    public YearGroup getYearGroup() {
        return yearGroup;
    }
    public StudentId getStudentId() {
        return studentId;
    }
    /**
     * Returns an immutable subject set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    /**
     * Returns true if both persons have the same student ID.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getStudentId().equals(getStudentId());
    }

    /**
     * Returns a new Person with the added subject.
     * The original person is not modified (immutability is preserved).
     */
    public Person addSubject(Subject subjectToAdd) {
        requireAllNonNull(subjectToAdd);

        // Create a new set of subjects by copying the existing subjects and adding the new one
        Set<Subject> updatedSubjects = new HashSet<>(this.subjects);
        updatedSubjects.add(subjectToAdd);

        // Return a new Person object with the updated subjects
        return new Person(this.name, this.phone, this.email, this.address, this.ic, this.yearGroup, this.studentId,
                updatedSubjects);
    }

    /**
     * Returns a new {@code Person} with student ID assigned.
     */
    public Person assignStudentId(int idGeneratedCount) {
        StudentId idToAssign = StudentId.generateNewStudentId(idGeneratedCount);

        // Return a new Person object with student ID assigned
        return new Person(this.name, this.phone, this.email, this.address, this.ic, this.yearGroup, idToAssign,
                this.subjects);
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
                && address.equals(otherPerson.address)
                && ic.equals(otherPerson.ic)
                && yearGroup.equals(otherPerson.yearGroup)
                && studentId.equals(otherPerson.studentId)
                && subjects.equals(otherPerson.subjects);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, ic, yearGroup, studentId, subjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("ic", ic)
                .add("year group", yearGroup)
                .add("studentId", studentId)
                .add("subjects", subjects)
                .toString();
    }

}
