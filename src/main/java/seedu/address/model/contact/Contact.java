package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    // Identity fields
    private final Name name;
    private final TelegramHandle telegramHandle;
    private final Email email;

    // Data fields
    private final StudentStatus studentStatus;

    // @@author cth06-Github-reused
    // Solution taken from https://stackoverflow.com/questions/17826854/creating-a-sorted-set
    private final Set<Role> roles = new TreeSet<>(); // allows ordered representation
    // @@author
    private final Nickname nickname;

    /**
     * Every field must be present and not null.
     */
    public Contact(Name name, TelegramHandle telegramHandle, Email email, StudentStatus studentStatus,
                   Set<Role> roles, Nickname nickname) {
        requireAllNonNull(name, telegramHandle, email, studentStatus, roles, nickname);
        this.name = name;
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.studentStatus = studentStatus;
        this.roles.addAll(roles);
        this.nickname = nickname;
    }

    public Name getName() {
        return name;
    }

    public TelegramHandle getTelegramHandle() {
        return telegramHandle;
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
     * Returns true if both contacts have the same name.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getName().equals(getName())
                && otherContact.getNickname().equals(getNickname());
    }

    //@@author somethingfishyfishy
    /**
     * Returns true if both contacts have the same fields.
     * This defines a weaker notion of equality between two contacts.
     * This is called after isSameContact, will return true if
     * - TelegramHandle field already exist
     * - Email field already exist
     * - Nickname already exist for the same Name
     */
    public boolean hasSameFields(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        if (otherContact == null) {
            return false;
        }

        //@@author cth06-Github
        Nickname otherContactNickname = otherContact.getNickname();
        Nickname currentContactNickname = this.getNickname();
        boolean hasSameTelegramHandle = otherContact.getTelegramHandle().equals(this.getTelegramHandle());
        boolean hasSameEmail = otherContact.getEmail().equals(this.getEmail());
        boolean hasSameNickname = otherContactNickname.equals(currentContactNickname);
        boolean hasSameNameAndNickname = hasSameNickname && otherContact.getName().equals(this.getName());
        boolean hasSameNonEmptyNickname =
                hasSameNickname && !otherContactNickname.isEmpty() && !currentContactNickname.isEmpty();
        boolean isBothPresident = otherContact.hasPresident() && this.hasPresident();
        return hasSameTelegramHandle || hasSameEmail || hasSameNameAndNickname
                || hasSameNonEmptyNickname || isBothPresident;
    }

    private boolean hasPresident() {
        return getRoles().stream().map(role -> role.roleName)
                .anyMatch(roleName -> roleName.equalsIgnoreCase(Role.PRESIDENT));
    }

    //@@author
    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Contact)) {
            return false;
        }

        //@@author
        Contact otherContact = (Contact) other;
        return name.equals(otherContact.name)
                && telegramHandle.equals(otherContact.telegramHandle)
                && email.equals(otherContact.email)
                && studentStatus.equals(otherContact.studentStatus)
                && roles.equals(otherContact.roles)
                && nickname.equals(otherContact.nickname);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, telegramHandle, email, studentStatus, roles, nickname);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("telegram handle", telegramHandle)
                .add("email", email)
                .add("studentStatus", studentStatus)
                .add("roles", roles)
                .add("nickname", nickname)
                .toString();
    }
}
