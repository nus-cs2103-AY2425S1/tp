package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.contactrecord.ContactRecordList;
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
    private final ContactRecordList contactRecords = new ContactRecordList();
    private final CallFrequency callFrequency;

    /**
     * Every field must be present and not null.
     */
    public Person(Nric nric, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  ContactRecordList contactRecords, CallFrequency callFrequency) {
        requireAllNonNull(nric, name, phone, email, address, tags, contactRecords, callFrequency);
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.contactRecords.addAll(contactRecords);
        this.callFrequency = callFrequency;
    }

    /**
     * For creating a new person. Every field but contact date must be present and not null.
     */
    public Person(Nric nric, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            CallFrequency callFrequency) {
        this(nric, name, phone, email, address, tags, new ContactRecordList(ContactRecord.createCurrentRecord("")),
                callFrequency);
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

    public ContactRecordList getContactRecords() {
        return contactRecords;
    }

    public ContactRecord getLastContacted() {
        return contactRecords.getLastContacted();
    }

    public CallFrequency getCallFrequency() {
        return callFrequency;
    }

    public ContactRecord getNextContactRecord() {
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
    public void markAsContacted(ContactRecord contactRecord) {
        contactRecords.markAsContacted(contactRecord);
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
     * Returns true if both persons are the same as defined in {@code isSamePerson(Person otherPerson)}
     * or if their name, phone number or email are the same.
     * This defines the weakest notion of equality between two persons.
     *
     * @param otherPerson The other person to compare with.
     * @return True if the persons are similar, false otherwise.
     */
    public boolean isSimilarPerson(Person otherPerson) {
        if (isSamePerson(otherPerson)) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return otherPerson.getName().equals(getName())
               || otherPerson.getPhone().equals(getPhone())
               || otherPerson.getEmail().equals(getEmail());
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
        return Objects.hash(nric, name, phone, email, address, tags, callFrequency);
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
                .add("call frequency", callFrequency)
                .toString();
    }

}
