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
    private final Tag tag;
    private final Set<Allergy> allergies = new HashSet<>();
    private final Date date;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Address address, Tag tag, Set<Allergy> allergies, Date date) {
        requireAllNonNull(name, phone, email, address, allergies, tag, date);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tag = tag;
        this.allergies.addAll(allergies);
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Tag getTag() {
        return tag;
    }

    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }

    /**
     * Checks if this person is considered the same as another person.
     * <p>
     * Two persons are considered the same if they have:
     * <ul>
     *   <li>Identical names and either identical phone numbers or identical email addresses.</li>
     * </ul>
     * This defines a weaker notion of equality based on commonly shared attributes,
     * primarily useful for identifying potential duplicates.
     *
     * @param otherPerson The person to compare with.
     * @return {@code true} if both persons have the same name and either the same phone number or email address.
     *         {@code false} if {@code otherPerson} is null or if neither of these conditions hold.
     */
    public boolean isSamePerson(Person otherPerson) {
        // Check if the otherPerson reference points to the same object instance as this one
        if (otherPerson == this) {
            return true;
        }

        // Check if otherPerson is null before further checks
        if (otherPerson == null) {
            return false;
        }

        Name otherName = otherPerson.getName();
        Phone otherPhone = otherPerson.getPhone();
        Email otherEmail = otherPerson.getEmail();

        // Define criteria for matching: names match and either phones match or emails match
        boolean hasMatchingName = getName().equals(otherName);
        boolean hasMatchingPhone = getPhone().equals(otherPhone);
        boolean hasMatchingEmail = getEmail().equals(otherEmail);

        // Check if names match and either phones or emails also match
        return hasMatchingName && (hasMatchingPhone || hasMatchingEmail);
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
                && tag.equals(otherPerson.tag)
                && allergies.equals(otherPerson.allergies);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tag, allergies);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tag", tag)
                .add("allergies", allergies)
                .add("date", date)
                .toString();
    }

}
