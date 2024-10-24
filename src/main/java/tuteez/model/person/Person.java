package tuteez.model.person;

import static tuteez.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tuteez.commons.util.ToStringBuilder;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.remark.RemarkList;
import tuteez.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final TelegramUsername telegramUsername;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Lesson> lessons = new HashSet<>();
    private final RemarkList remarkList;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, TelegramUsername teleHandle, Set<Tag> tags,
                  Set<Lesson> lessons) {
        requireAllNonNull(name, phone, email, address, teleHandle, tags, lessons);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.remarkList = new RemarkList();
        this.telegramUsername = teleHandle;
        this.lessons.addAll(lessons);
    }

    /**
     * Returns a person object.
     */
    public Person(Name name, Phone phone, Email email, Address address, TelegramUsername teleHandle, Set<Tag> tags,
                  Set<Lesson> lessons, RemarkList remarkList) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.remarkList = remarkList;
        this.telegramUsername = teleHandle;
        this.lessons.addAll(lessons);
    }

    public Name getName() {
        assert name != null : "Name field should not be null";
        return name;
    }

    public Phone getPhone() {
        assert phone != null : "Phone field should not be null";
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public RemarkList getRemarkList() {
        return this.remarkList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public TelegramUsername getTelegramUsername() {
        return telegramUsername;
    }

    /**
     * Returns an immutable lesson set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Lesson> getLessons() {
        return Collections.unmodifiableSet(lessons);
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
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && telegramUsername.equals(otherPerson.telegramUsername)
                && lessons.equals(otherPerson.lessons);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, telegramUsername, tags, lessons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("telegramUsername", telegramUsername)
                .add("tags", tags)
                .add("lessons", lessons)
                .toString();
    }

}
