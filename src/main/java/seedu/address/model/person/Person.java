package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
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
    private Name name;
    private Phone phone;
    private Email email;

    // Data fields
    private Address address;
    private Payment payment;
    private Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Payment payment, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, payment, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.payment = payment;
        this.tags.addAll(tags);
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

    public Payment getPayment() {
        return payment;
    }

    public String getFullName() {
        return name.fullName;
    }

    public String getPhoneValue() {
        return phone.value;
    }

    public String getEmailValue() {
        return email.value;
    }

    public String getAddressValue() {
        return address.value;
    }

    public void setName(Name changedName) {
        this.name = changedName;
    }

    public void setPhone(Phone changedPhone) {
        this.phone = changedPhone;
    }

    public void setEmail(Email changedEmail) {
        this.email = changedEmail;
    }

    public void setAddress(Address changedAddress) {
        this.address = changedAddress;
    }

    public void setPayment(Payment changedPayment) {
        this.payment = changedPayment;
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
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
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
                && payment.equals(otherPerson.payment)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, payment, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("payment", payment)
                .add("tags", tags)
                .toString();
    }

}
