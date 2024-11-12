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
    private final Birthday birthday;
    private final Age age;
    private final Set<Tag> tags = new HashSet<>();
    private final Boolean hasPaid;
    private final LastPaidDate lastPaidDate;
    private final Frequency frequency;
    private final ProfilePicFilePath profilePicFilePath;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Birthday birthday,
                  Set<Tag> tags, Boolean hasPaid, LastPaidDate lastpaidDate, Frequency frequency,
                  ProfilePicFilePath profilePicFilePath) {

        requireAllNonNull(name, phone, email, address, birthday, tags);
        //hasPaid not required to be non-null for testing of commands that do not interact with paid status
        //e.g. edit command in AddressBookParserTest::parseCommand_edit()
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.age = new Age(birthday);
        this.tags.addAll(tags);
        this.hasPaid = hasPaid;
        this.lastPaidDate = lastpaidDate;
        this.frequency = frequency;
        this.profilePicFilePath = profilePicFilePath;
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

    public Birthday getBirthday() {
        return birthday;
    }

    public Age getAge() {
        return age;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Boolean getHasPaid() {
        return hasPaid;
    }
    public LastPaidDate getLastPaidDate() {
        return lastPaidDate;
    }
    public Frequency getFrequency() {
        return frequency;
    }
    public ProfilePicFilePath getProfilePicFilePath() {
        return profilePicFilePath;
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
                // && otherPerson.getName().equals(getName());
                && this.getName().isSameName(otherPerson.getName());
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
                && birthday.equals(otherPerson.birthday)
                && age.equals(otherPerson.age)
                && tags.equals(otherPerson.tags)
                && hasPaid.equals(otherPerson.hasPaid)
                && lastPaidDate.equals(otherPerson.lastPaidDate)
                && frequency.equals(otherPerson.frequency);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, birthday, age, tags, hasPaid, lastPaidDate, frequency);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("birthday", birthday)
                .add("age", age)
                .add("tags", tags)
                .add("hasPaid", hasPaid)
                .add("lastPaidDate", lastPaidDate)
                .add("frequency", frequency)
                .toString();
    }

}
