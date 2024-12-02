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
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<Address> address;
    private final Set<Tag> tags = new HashSet<>();
    private final ModuleRoleMap moduleRoleMap;
    private final Optional<Description> description;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Optional<Phone> phone, Optional<Email> email, Optional<Address> address, Set<Tag> tags,
                  ModuleRoleMap moduleRoleMap, Optional<Description> description) {
        requireAllNonNull(name, phone, email, tags, moduleRoleMap, description);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.moduleRoleMap = moduleRoleMap;
        this.description = description;
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

    public Optional<Address> getAddress() {
        return address;
    }

    public ModuleRoleMap getModuleRoleMap() {
        return moduleRoleMap;
    }

    public Optional<Description> getDescription() {
        return description;
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

        if (otherPerson == null) {
            return false;
        }

        boolean isEmailPresentAndSame = hasEmail() && otherPerson.getEmail().equals(getEmail());
        boolean isPhonePresentAndSame = hasPhone() && otherPerson.getPhone().equals(getPhone());

        return isEmailPresentAndSame || isPhonePresentAndSame;
    }

    /**
     * Returns true if both persons have the same phone number.
     */
    public boolean isPhonePresentAndSame(Person otherPerson) {
        if (otherPerson == null) {
            return false;
        }

        return hasPhone() && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same email.
     */
    public boolean isEmailPresentAndSame(Person otherPerson) {
        if (otherPerson == null) {
            return false;
        }

        return hasEmail() && otherPerson.getEmail().equals(getEmail());
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
                && tags.equals(otherPerson.tags)
                && moduleRoleMap.equals(otherPerson.moduleRoleMap)
                && description.equals(otherPerson.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, moduleRoleMap, description);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("name", name);

        phone.ifPresent(phone -> builder.add("phone", phone));
        email.ifPresent(email -> builder.add("email", email));
        address.ifPresent(addr -> builder.add("address", addr));
        builder.add("tags", tags)
                .add("roles", moduleRoleMap);
        description.filter(value -> !value.isBlank())
            .ifPresent(description -> builder.add("description", description));

        return builder.toString();
    }

    /**
     * Returns true if this person has a non-null phone.
     */
    public boolean hasPhone() {
        return this.phone.isPresent();
    }

    /**
     * Returns true if this person has a non-null email.
     */
    public boolean hasEmail() {
        return this.email.isPresent();
    }

    /**
     * Returns true if this person has a non-null address.
     */
    public boolean hasAddress() {
        return this.address.isPresent();
    }

    /**
     * Returns true if this person has an empty {@code ModuleRoleMap}.
     */
    public boolean hasEmptyModuleRoleMap() {
        return this.moduleRoleMap.getRoles().isEmpty();
    }

    /**
     * Returns true if this person has a non-null description.
     */
    public boolean hasNonEmptyDescription() {
        return description.filter(value -> !value.isBlank()).isPresent();
    }

    /**
     * Returns the description of the person as a string. If the description is blank, returns a default string.
     */
    public String getDescriptionString() {
        return description
                .filter(value -> !value.isBlank())
                .map(Objects::toString)
                .orElse("<no description>");
    }
}
