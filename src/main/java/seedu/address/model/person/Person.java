package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final ContactType contactType;
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final TelegramHandle telegramHandle;
    private final ModuleName moduleName;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(ContactType contactType, Name name, Optional<Phone> phone, Optional<Email> email,
                  TelegramHandle telegramHandle, ModuleName moduleName, Set<Tag> tags) {
        requireAllNonNull(contactType, name, phone, email, telegramHandle, moduleName, tags);
        this.contactType = contactType;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegramHandle = telegramHandle;
        this.moduleName = moduleName;

        this.tags.addAll(tags);
    }

    public ContactType getContactType() {
        return contactType;
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return email;
    }

    public TelegramHandle getTelegramHandle() {
        return telegramHandle;
    }

    public ModuleName getModuleName() {
        return moduleName;
    }

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
        return contactType.equals(otherPerson.contactType)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && telegramHandle.equals(otherPerson.telegramHandle)
                && moduleName.equals(otherPerson.moduleName)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, telegramHandle, tags, moduleName);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contactType", contactType)
                .add("name", name)
                .add("phone", phone.map(Phone::toString).orElse(" "))
                .add("email", email.map(Email::toString).orElse(" "))
                .add("telegramHandle", telegramHandle)
                .add("moduleName", moduleName)
                .add("tags", tags)
                .toString();
    }

}
