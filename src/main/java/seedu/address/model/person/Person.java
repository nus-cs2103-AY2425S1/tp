package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.participation.Participation;
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
    private List<Participation> participationList;
    private Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Payment payment, List<Participation> participationList, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, payment, participationList, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.payment = payment;
        this.participationList = participationList;
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

    public List<Participation> getParticipation() {
        return participationList;
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

    public void setParticipationList(List<Participation> changedList) {
        this.participationList = changedList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if the person already contains a participation
     * @param participation the participation object to check
     * @return true if it already contains the participation. false otherwise
     */
    public boolean hasParticipation(Participation participation) {
        return participationList.stream()
                .anyMatch(currentParticipation -> currentParticipation.isSameParticipation(participation));
    }

    /**
     * Adds a participation object to the participation list
     * @param participation object to be added
     */
    public void addParticipation(Participation participation) {
        participationList.add(participation);
    }

    /**
     * Removes a participation object from the participation list
     * @param participation object to be removed
     */
    public void removeParticipation(Participation participation) {
        participationList.remove(participation);
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
                && participationList.equals(otherPerson.participationList)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, payment, participationList, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("payment", payment)
                .add("participation", participationList)
                .add("tags", tags)
                .toString();
    }

}
