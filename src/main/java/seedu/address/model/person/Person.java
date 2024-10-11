package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Telegram telegram;
    private final Email email;

    // Data fields
    private final StudentStatus studentStatus;
    private final Set<Role> roles = new HashSet<>();
    private final Nickname nickname;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Telegram telegram, Email email, StudentStatus studentStatus,
                  Set<Role> roles, Nickname nickname) {
        requireAllNonNull(name, telegram, email, studentStatus, roles, nickname);
        this.name = name;
        this.telegram = telegram;
        this.email = email;
        this.studentStatus = studentStatus;
        this.roles.addAll(roles);
        this.nickname = nickname;
    }

    public Name getName() {
        return name;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Email getEmail() {
        return email;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public Nickname getNickname() {
        return nickname;
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
                && telegram.equals(otherPerson.telegram)
                && email.equals(otherPerson.email)
                && studentStatus.equals(otherPerson.studentStatus)
                && roles.equals(otherPerson.roles)
                && nickname.equals(otherPerson.nickname);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, telegram, email, studentStatus, roles, nickname);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("telegram", telegram)
                .add("email", email)
                .add("studentStatus", studentStatus)
                .add("roles", roles)
                .add("nickname", nickname)
                .toString();
    }

}
