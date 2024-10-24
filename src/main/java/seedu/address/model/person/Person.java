package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

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
    private final Set<Tag> tags = new HashSet<>();
    private final ProjectStatus projectStatus;
    private final PaymentStatus paymentStatus;
    private final ClientStatus clientStatus;
    private final Deadline deadline;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, ProjectStatus projectStatus,
                  PaymentStatus paymentStatus, ClientStatus clientStatus, Deadline deadline) {
        requireAllNonNull(name, phone, email, address, tags, deadline);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.projectStatus = (projectStatus != null)
                ? projectStatus : new ProjectStatus("in progress"); // Default value
        this.paymentStatus = (paymentStatus != null)
                ? paymentStatus : new PaymentStatus("pending"); // Default value
        this.clientStatus = (clientStatus != null)
                ? clientStatus : new ClientStatus("active"); // Default value
        this.deadline = deadline;
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

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    /**
     * Returns a string representation of the project deadline.
     * If the deadline has passed and the project is not completed,
     * then the [OVERDUE] flag is added
     */
    public String checkAndGetDeadline() {
        if (deadline.isOverdue() && !projectStatus.isComplete) {
            return deadline.toString() + " [OVERDUE]";
        } else {
            return deadline.toString();
        }
    }


    /**
     * Returns true if both persons have the same name, email and phone.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if the person has been blacklisted by the user.
     */
    public boolean isBlacklisted() {
        return clientStatus.isBlacklisted();
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
                && tags.equals(otherPerson.tags)
                && projectStatus.equals(otherPerson.projectStatus)
                && paymentStatus.equals(otherPerson.paymentStatus)
                && clientStatus.equals(otherPerson.clientStatus)
                && deadline.equals(otherPerson.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, tags, projectStatus, paymentStatus, clientStatus, deadline);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("projectStatus", projectStatus)
                .add("paymentStatus", paymentStatus)
                .add("clientStatus", clientStatus)
                .add("deadline", deadline)
                .toString();
    }
}
