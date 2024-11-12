package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Student.STUDENT_TYPE;
import static seedu.address.model.person.Teacher.TEACHER_TYPE;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.InvalidPersonTypeException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {


    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Gender gender;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Subject> subjects = new HashSet<>();
    private final Set<String> classes = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags,
                  Set<Subject> subjects, Set<String> classes) {
        requireAllNonNull(name, phone, gender, email, address, tags, subjects, classes);
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.subjects.addAll(subjects);
        this.classes.addAll(classes);
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

    public Gender getGender() {
        return this.gender;
    }

    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    public Set<String> getClasses() {
        return this.classes;
    }

    public DaysAttended getDaysAttended() {
        return null;
    }

    public Name getNextOfKinName() {
        return null;
    }

    public Phone getEmergencyContact() {
        return null;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same Email or Phone Number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
            && (otherPerson.getEmail().equals(getEmail()) || otherPerson.getPhone().equals(getPhone()));
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
        return getName().equals(otherPerson.getName())
            && getGender().equals(otherPerson.getGender())
            && getPhone().equals(otherPerson.getPhone())
            && getEmail().equals(otherPerson.getEmail())
            && getAddress().equals(otherPerson.getAddress())
            && getTags().equals(otherPerson.getTags())
            && getSubjects().equals(otherPerson.getSubjects())
            && getClasses().equals(otherPerson.getClasses());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gender, phone, email, address, tags, subjects, classes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", getName())
            .add("gender", getGender())
            .add("phone", getPhone())
            .add("email", getEmail())
            .add("address", getAddress())
            .add("tags", getTags())
            .add("subject", getSubjects())
            .add("classes", getClasses())
            .toString();
    }

    /**
     * Returns a new {@code Person} object with attendance incremented by one day.
     * Implementations of this method in subclasses should define how attendance
     * is managed and incremented based on specific requirements.
     *
     * @return A new {@code Person} instance with incremented attendance.
     */
    public abstract Person withIncrementedAttendance();

    /**
     * Returns a new {@code Person} object with attendance decremented by one day.
     * Implementations in subclasses should ensure that this method properly handles
     * decrementing attendance, if applicable.
     *
     * @return A new {@code Person} instance with decremented attendance.
     * @throws CommandException if attendance cannot be decremented (e.g., if unsupported for a given subclass).
     */
    public abstract Person withDecrementedAttendance() throws CommandException;

    /**
     * Returns a new {@code Person} object with attendance reset.
     * Implementations in subclasses should define what a reset means in the context of that specific type.
     *
     * @return A new {@code Person} instance with attendance reset.
     */
    public abstract Person withResetAttendance();

    /**
     * Creates a new Person object based on the specified type.
     *
     * @param type The type of person to create (e.g., "student" or "teacher").
     * @param name The name of the person.
     * @param gender The gender of the person.
     * @param phone The phone number of the person.
     * @param email The email address of the person.
     * @param address The address of the person.
     * @param tags The set of tags associated with the person.
     * @param subjects The set of subjects associated with the person.
     * @param classes The set of classes associated with the person.
     * @param daysAttended The number of days attended by the person (applicable for students).
     * @return A new Person object of the specified type.
     * @throws InvalidPersonTypeException if the specified type is not recognized.
     */
    public static Person createPerson(String type, Name name, Gender gender, Phone phone, Email email, Address address,
                                      Set<Tag> tags, Set<Subject> subjects, Set<String> classes,
                                      DaysAttended daysAttended, Name nextOfKinName, Phone emergencyContact) {
        switch (type) {
        case STUDENT_TYPE:
            return new Student(name, gender, phone, email, address, tags, subjects, classes, daysAttended,
                    nextOfKinName, emergencyContact);
        case TEACHER_TYPE:
            return new Teacher(name, gender, phone, email, address, tags, subjects, classes);
        default:
            throw new InvalidPersonTypeException();
        }
    }

    /**
     * Returns the type of the person.
     *
     * @return The type of the person.
     * @throws InvalidPersonTypeException if the type of the person is not recognized.
     */
    public String getType() {
        throw new InvalidPersonTypeException();
    }

    /**
     * Returns the number of days attended by the person.
     *
     * @return The number of days attended by the person.
     */
    public abstract int getDaysAttendedValue() throws CommandException;
}
