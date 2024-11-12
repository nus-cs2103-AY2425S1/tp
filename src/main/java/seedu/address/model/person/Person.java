package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.role.Member;
import seedu.address.model.role.Role;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Telegram telegram;
    private final FavouriteStatus isFavourite;

    // Data fields
    private final Set<Role> roles = new HashSet<>();
    private final Set<Attendance> attendance = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Telegram telegram,
                  Set<Role> roles, Set<Attendance> attendance, FavouriteStatus isFavourite) {
        requireAllNonNull(name, phone, email, telegram, roles, isFavourite);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.roles.addAll(roles);
        this.attendance.addAll(attendance);
        this.isFavourite = isFavourite;
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

    public Telegram getTelegram() {
        return telegram;
    }

    public FavouriteStatus getFavouriteStatus() {
        return isFavourite;
    }

    /**
     * Returns an immutable role set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    /**
     * Check if Person is a Member
     * @return true if Person has a role "Member"
     */
    public boolean isMember() {
        Set<Role> roles = this.getRoles();
        return roles.contains(new Member());
    }

    /**
     * Returns an immutable attendance set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     * @return the attendance set encapsulated by Person
     */
    public Set<Attendance> getAttendance() {
        return Collections.unmodifiableSet(attendance);
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
                && otherPerson.getTelegram().equals(getTelegram());
    }

    /**
     *
     */
    public boolean doesNameHaveConsecutiveWhitespaces() {
        return this.name.hasConsecutiveWhitespaces();
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
                && telegram.equals(otherPerson.telegram)
                && roles.equals(otherPerson.roles);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, telegram, roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("telegram", telegram)
                .add("roles", roles)
                .toString();
    }

}
