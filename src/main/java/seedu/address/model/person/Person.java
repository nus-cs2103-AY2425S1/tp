package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contactdate.ContactDate;
import seedu.address.model.contactdate.ContactDateList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Nric nric;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final ContactDateList contactDates = new ContactDateList();
    private final CallFrequency callFrequency;

    /**
     * Every field must be present and not null.
     */
    public Person(Nric nric, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            ContactDateList contactDates, CallFrequency callFrequency) {
        requireAllNonNull(nric, name, phone, email, address, tags);
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.contactDates.addAll(contactDates);
        this.callFrequency = callFrequency;
    }

    /**
     * For creating a new person. Every field but contact date must be present and not null.
     */
    public Person(Nric nric, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  CallFrequency callFrequency) {
        this(nric, name, phone, email, address, tags, new ContactDateList(ContactDate.getCurrentDate()), callFrequency);
    }

    public Nric getNric() {
        return nric;
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

    public ContactDateList getContactDates() {
        return contactDates;
    }

    public ContactDate getLastContacted() {
        return contactDates.getLastContacted();
    }

    public CallFrequency getCallFrequency() {
        return callFrequency;
    }

    public ContactDate getNextContactDate() {
        return getLastContacted().add(callFrequency);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Mark the person as contacted today.
     */
    public void markAsContacted() {
        contactDates.markAsContacted();
    }

    /**
     * Returns true if both persons have the same nric.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getNric().equals(getNric());
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
        return nric.equals(otherPerson.nric)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(nric, name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nric", nric)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
