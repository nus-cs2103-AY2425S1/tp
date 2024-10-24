package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null (except the appointment),
 * field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Priority priority;
    private final Remark remark;
    private final DateOfBirth dateOfBirth;
    private final Income income;
    private final Appointment appointment;

    private final FamilySize familySize;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Priority priority, Remark remark,
                  DateOfBirth dateOfBirth, Income income, FamilySize familySize, Set<Tag> tags) {
        this(name, phone, email, address, priority, remark, dateOfBirth, income, null, familySize, tags);
    }

    /**
     * Every field must be present and not null, except the appointment.
     */
    public Person(Name name, Phone phone, Email email, Address address, Priority priority, Remark remark,
                  DateOfBirth dateOfBirth, Income income, Appointment appointment, FamilySize familySize,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, familySize, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.priority = priority;
        this.remark = remark;
        this.dateOfBirth = dateOfBirth;
        this.income = income;
        this.appointment = appointment;
        this.familySize = familySize;
        this.tags.addAll(tags);
    }

    /**
     * Constructs a {@code Person} with the specified appointment.
     *
     * @param appointment An {@code Appointment}
     * @return a new {@code Person} with the appointment
     */
    public Person withAppointment(Appointment appointment) {
        return new Person(name, phone, email, address, priority, remark, dateOfBirth, income, appointment,
                familySize, tags);
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

    public Priority getPriority() {
        return priority;
    }

    public Remark getRemark() {
        return remark;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Income getIncome() {
        return income;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public FamilySize getFamilySize() {
        return familySize;
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
                && otherPerson.getName().equals(getName());
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
                && priority.equals(otherPerson.priority)
                && remark.equals(otherPerson.remark)
                && dateOfBirth.equals(otherPerson.dateOfBirth)
                && income.equals(otherPerson.income)
                && Objects.equals(appointment, otherPerson.appointment)
                && familySize.equals(otherPerson.familySize)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, priority, remark, dateOfBirth, income, appointment, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("priority", priority)
                .add("remark", remark)
                .add("dateOfBirth", dateOfBirth)
                .add("income", income)
                .add("appointment", appointment)
                .add("familySize", familySize)
                .add("tags", tags)
                .toString();
    }
}
