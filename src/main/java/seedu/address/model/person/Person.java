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
    private final Telegram telegram;
    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Telegram telegram, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, telegram, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.tags.addAll(tags);
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
        } else if (c.equals(Tag.class)) {
            StringBuilder t = new StringBuilder("| ");
            Set<Tag> tags = this.getTags();
            for (Tag tag : tags) {
                t.append(tag + " |");
            }
            return t.toString();
            // code for this method is currently not very elegant...
        } else if (c.equals(Set.class)) {
            return getString(Tag.class);
        } else {
            return "";
        }
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
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && telegram.equals(otherPerson.telegram)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, telegram, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("telegram", telegram)
                .add("tags", tags)
                .toString();
    }

}
