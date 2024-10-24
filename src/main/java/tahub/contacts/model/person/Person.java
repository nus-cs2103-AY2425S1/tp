package tahub.contacts.model.person;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Logic;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tag.Tag;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final MatriculationNumber matricNumber;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(MatriculationNumber matricNumber, Name name, Phone phone,
                  Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(matricNumber, name, phone, email, address, tags);
        this.matricNumber = matricNumber;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Creates a {@link Person} with generic details, taking in only a {@link MatriculationNumber}.
     *
     * @param matricNumber Matriculation number.
     * @return Person.
     */
    public static Person genericFromMatricNumber(MatriculationNumber matricNumber) {
        requireNonNull(matricNumber);
        return new Person(
                matricNumber,
                new Name("generic"),
                new Phone("99999999"),
                new Email("local@domain.tld"),
                new Address("404 Address Not Found"),
                new HashSet<>()
        );
    }

    public MatriculationNumber getMatricNumber() {
        return matricNumber;
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
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getMatricNumber().equals(getMatricNumber());
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
        return matricNumber.equals(otherPerson.matricNumber)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    /**
     * Returns the list of SCAs associated with the student in given logic.
     *
     * @param logic the logic to get the SCAs from
     * @return the list of SCAs associated with the student in given logic
     */
    public StudentCourseAssociationList getStudentCourseAssociations(Logic logic) {
        return logic.getStudentScas(this);
    }

    /**
     * Returns the list of courses taken by the student in given logic.
     *
     * @param logic the logic to get the courses from
     * @return the list of courses taken by the student in given logic
     */
    public UniqueCourseList getCourses(Logic logic) {
        return logic.getStudentCourses(this);
    }

    /**
     * Returns the list of tutorials attended by the student in given logic.
     *
     * @param logic the logic to get the tutorials from
     * @return the list of tutorials attended by the student in given logic
     */
    public List<Tutorial> getTutorials(Logic logic) {
        return logic.getStudentTutorials(this);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(matricNumber, name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("matricNumber", matricNumber)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

    /**
     * Shortened string representation of this {@link Person}. Only includes the matriculation number.
     *
     * @return String representation in "{@code matricNumber}" form
     */
    public String toStringShort() {
        return matricNumber.toString();
    }
}
