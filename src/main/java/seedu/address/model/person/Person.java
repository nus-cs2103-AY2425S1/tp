package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.role.Role;
import seedu.address.ui.Observer;


/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;

    private final TelegramUsername telegramUsername;
    private final Set<Role> roles = new HashSet<>();
    private UUID uniqueId;

    private Observer observer;

    /**
     * Every field must be present and not null
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  TelegramUsername telegramUsername, Set<Role> roles) {
        requireAllNonNull(name, phone, email, address, telegramUsername, roles);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.telegramUsername = telegramUsername;
        this.roles.addAll(roles);
        this.uniqueId = UUID.randomUUID(); // Generate a unique ID
    }

    /**
     * Constructor to create Person from memory using UUID
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  TelegramUsername telegramUsername, Set<Role> roles,
                   UUID uniqueId) {
        requireAllNonNull(name, phone, email, address, telegramUsername, roles);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.telegramUsername = telegramUsername;
        this.roles.addAll(roles);
        this.uniqueId = uniqueId; // use pre-existing UUID
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
    public TelegramUsername getTelegramUsername() {
        return telegramUsername;
    }
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * Returns an immutable role set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    /**
     * Returns true if both persons have the same phone number or email or telegram handle.
     * If either one or both has no telegram handle, then ignore telegram handle.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        // If either party has no telegram username, then just check for uniqueness in phone and email
        if (!getTelegramUsername().hasUsername() || !otherPerson.hasTelegramUsername()) {
            return otherPerson.getPhone().equals(getPhone())
                    || otherPerson.getEmail().equals(getEmail());
        }

        // Otherwise, check that phone, email and telegram username are all unique
        return otherPerson.getPhone().equals(getPhone())
                || otherPerson.getEmail().equals(getEmail())
                || otherPerson.getTelegramUsername().equals(getTelegramUsername());
    }


    /**
     * Returns true if both persons have the same phone number.
     */
    public boolean isSamePhone(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same email.
     */
    public boolean isSameEmail(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same telegram handle.
     */
    public boolean isSameTelegram(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getTelegramUsername().equals(getTelegramUsername());
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
                && telegramUsername.equals(otherPerson.telegramUsername)
                && roles.equals(otherPerson.roles);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, telegramUsername, roles);
    }

    /**
     * Returns true if the person has the specified role.
     */
    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("telegram", telegramUsername)
                .add("roles", roles)
                .toString();
    }

    @Override
    public int compareTo(Person other) {
        return this.getName().compareTo(other.getName());
    }

    /**
     * Adds an observer to this object. The observer will be notified of any changes
     * through the observer pattern when specific events occur.
     *
     * @param observer the {@code Observer} instance to be added
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Retrieves the observer currently associated with this object.
     *
     * @return the current {@code Observer} instance observing this object, or {@code null}
     *         if no observer has been added
     */
    public Observer getObserver() {
        return this.observer;
    }

    /**
     * Notifies the observer of any changes or updates that are specific to event-specific
     * roles for a contact. This method checks if an observer exists and, if so, calls
     * its {@code update} method, passing this instance as a parameter.
     */
    public void showContactWithEventSpecificRoles() {
        if (this.observer != null) {
            this.observer.update(this);
        }
    }

    /**
     * Checks if the person has a Telegram username.
     *
     * @return {@code true} if the Telegram username is present; {@code false} otherwise.
     */
    public boolean hasTelegramUsername() {
        return this.telegramUsername.hasUsername();
    }
}
