package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.car.Car;
import seedu.address.model.car.Vrn;
import seedu.address.model.issue.Issue;

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
    private final Car car;

    private final Set<Issue> issues = new HashSet<>();
    private boolean isServicing;

    private final Logger logger = LogsCenter.getLogger(Person.class);
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Issue> issues) {
        requireAllNonNull(name, phone, email, address, issues);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.isServicing = false;
        this.issues.addAll(issues);
        this.car = null;
        logger.info("Person without car created");
    }

    /**
     * Constructor for Person with Car
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param issues
     * @param car
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Issue> issues, Car car) {
        requireAllNonNull(name, phone, email, address, issues);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.isServicing = false;
        this.car = car;
        this.issues.addAll(issues);
        logger.info("Person with car created" + car);
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

    public Car getCar() {
        return car;
    }

    public Vrn getVrn() {
        return this.getCar().getVrn();
    }

    /**
     * Returns an immutable issue set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Issue> getIssues() {
        return Collections.unmodifiableSet(issues);
    }

    /**
     * Servicing status of a Client's Car.
     *
     * @return true if Checked in. False if not Checked in or no Car.
     */
    public boolean isServicing() {
        if (this.car == null) {
            return false; // Safeguard.
        }
        return this.isServicing;
    }

    /**
     * Toggle isServicing for Clients with Car.
     */
    public void setServicing() {
        if (this.car != null) {
            this.isServicing = !this.isServicing;
            logger.info("Client " + name + " has been " + (this.isServicing ? "Checked-In" : "Checked-Out"));
        }
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

        if (car == null && otherPerson.car == null) {
            return name.equals(otherPerson.name)
                    && phone.equals(otherPerson.phone)
                    && email.equals(otherPerson.email)
                    && address.equals(otherPerson.address)
                    && issues.equals(otherPerson.issues);
        } else if (car == null || otherPerson.car == null) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && issues.equals(otherPerson.issues)
                && car.equals(otherPerson.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, issues, car);
    }

    @Override
    public String toString() {
        if (car == null) {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("issues", issues)
                    .toString();
        }
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("issues", issues)
                .add("car", car)
                .toString();
    }

}
