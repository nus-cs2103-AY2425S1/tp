package tutorease.address.model.person;

import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {
    private static Logger logger = LogsCenter.getLogger(Person.class);
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Role role;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags) {
        logger.log(Level.INFO, "Creating Person object with name: " + name
                + " phone: " + phone + " email: " + email + " address: " + address
                + " role: " + role + " tags: " + tags);
        requireAllNonNull(name, phone, email, address, role, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.tags.addAll(tags);
        logger.log(Level.INFO, "Created Person object with name: " + name
                + " phone: " + phone + " email: " + email + " address: " + address
                + " role: " + role + " tags: " + tags);
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

    public String getAddressString() {
        return address.toString();
    }

    public abstract Role getRole();
    public abstract boolean isGuardian();
    public abstract boolean isStudent();

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
     * Returns true if both persons have the same email address.
     */
    public boolean hasSameEmail(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getEmail() != null
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same phone number.
     */
    public boolean hasSamePhone(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone() != null
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
            logger.log(Level.WARNING, "Person is not an instance of Person");
            return false;
        }

        Person otherPerson = (Person) other;
        boolean isNameEqual = name.equals(otherPerson.name);
        boolean isPhoneEqual = phone.equals(otherPerson.phone);
        boolean isEmailEqual = email.equals(otherPerson.email);
        boolean isAddressEqual = address.equals(otherPerson.address);
        boolean isTagsEqual = tags.equals(otherPerson.tags);
        boolean isRoleEqual = role.equals(otherPerson.role);

        logger.log(Level.INFO, "Comparing Person: " + this + " with " + otherPerson);
        logger.log(Level.INFO, "Comparing name: " + isNameEqual
                + " Comparing phone: " + isPhoneEqual
                + " Comparing email: " + isEmailEqual
                + " Comparing address: " + isAddressEqual
                + " Comparing tags: " + isTagsEqual
                + " Comparing role: " + isRoleEqual);

        return isNameEqual && isPhoneEqual && isEmailEqual && isAddressEqual && isTagsEqual && isRoleEqual;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("role", role)
                .toString();
    }
}
