package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Schedule schedule;
    private final Subject subject;
    private final Rate rate;
    private final Paid paid;
    private final OwedAmount owedAmount;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Schedule schedule,
            Subject subject, Rate rate, Paid paid, OwedAmount owedAmount) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.schedule = schedule;
        this.subject = subject;
        this.rate = rate;
        this.paid = paid;
        this.owedAmount = owedAmount;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public Subject getSubject() {
        return subject;
    }

    public Rate getRate() {
        return rate;
    }
    public Paid getPaid() {
        return paid;
    }

    public OwedAmount getOwedAmount() {
        return owedAmount;
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
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Determines whether there is a scheduling conflict between this person and another person.
     * @param otherPerson The person to compare against for scheduling conflicts.
     * @return true if there is a clash in schedules with the specified other person.
     */
    public boolean isClash(Person otherPerson) {
        if (otherPerson == this) {
            return false;
        }

        return otherPerson != null && getSchedule().isClash(otherPerson.getSchedule());
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
                && schedule.equals(otherPerson.schedule)
                && subject.equals(otherPerson.subject)
                && rate.equals(otherPerson.rate)
                && paid.equals(otherPerson.paid)
                && owedAmount.equals(otherPerson.owedAmount);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, schedule, subject, rate, paid, owedAmount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("schedule", schedule)
                .add("subject", subject)
                .add("rate", rate)
                .add("paid", paid)
                .add("owedAmount", owedAmount)
                .toString();
    }

}
