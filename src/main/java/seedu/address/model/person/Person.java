package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.lang.reflect.Field;
import java.util.Arrays;
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
     * Check is Person is a member
     * @return true if Person has a role "Member"
     */
    public boolean isMember() {
        Set<Role> roles = this.getRoles();
        return roles.contains(new Role(Member.MEMBER_ROLE));
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
     * Generates the string representation of the instance's specified field of type 'c'
     * @param c class that corresponds with a field of the instance
     * @return string representation of that field
     */
    public String getString(Class c) {
        if (c.equals(Name.class)) {
            return this.getName().toString();
        } else if (c.equals(Phone.class)) {
            return this.getPhone().toString();
        } else if (c.equals(Email.class)) {
            return this.getEmail().toString();
        } else if (c.equals(Telegram.class)) {
            return this.getTelegram().toString();
        } else if (c.equals(Role.class)) {
            StringBuilder r = new StringBuilder("| ");
            Set<Role> roles = this.getRoles();
            for (Role role : roles) {
                r.append(" " + role + " |");
            }
            return r.toString();
        } else if (this.isMember() && c.equals(Attendance.class)) {
            StringBuilder a = new StringBuilder("| ");
            Set<Attendance> sessions = this.getAttendance();
            for (Attendance sesh : sessions) {
                a.append(" " + sesh + " |");
            }
            return a.toString();
        } else {
            return "";
        }
    }

    /**
     * Formulates a message that displays all information of the specified contact
     * @return String message which contains all the information of the specified contact
     */
    public String generateContactInformation() {
        Field[] fields = Person.class.getDeclaredFields();
        StringBuilder contactInfo = new StringBuilder("");
        Arrays.stream(fields).forEach(field -> contactInfo.append(field.getName().toUpperCase().equals("ROLES")
                ? field.getName().toUpperCase() + ": " + this.getString(Role.class) + "\n"
                : field.getName().toUpperCase().equals("ATTENDANCE")
                        ? field.getName().toUpperCase() + ": " + this.getString(Attendance.class) + "\n"
                        : field.getName().toUpperCase() + ": " + this.getString(field.getType()) + "\n"));
        return contactInfo.toString();
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
