package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
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
    private final int id;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Integer> eventIds = new HashSet<>();

    /**
     * Constructor for the {@code Person} Class. ID is initialised to -1.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.id = -1;
    }

    /**
     * Constructor for the {@code Person} Class. ID is initialised to the given ID.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Integer> eventIds, int id) {
        requireAllNonNull(name, phone, email, address, tags, eventIds);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.eventIds.addAll(eventIds);
        this.id = id;
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

    public int getId() {
        return id;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Integer> getEventIds() {
        return Collections.unmodifiableSet(eventIds);
    }

    /**
     * Returns a new {@code Person} object that has the same attributes except the ID.
     */
    public Person changeId(int newId) {
        return new Person(name, phone, email, address, tags, eventIds, newId);
    }

    public boolean checkAssignedToEvent(Event event) {
        return eventIds.contains(event.getEventId());
    }

    public void addEventId(int eventId) {
        eventIds.add(eventId);
    }

    public void removeEventId(int eventId) {
        eventIds.remove(eventId);
    }

    /**
     * Returns true if both persons have the same name (Case-insensitive).
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equalsLowerCase(getName());
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
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

    /**
     * Converts the object's fields into a CSV (Comma-Separated Values) format.
     * Each field is enclosed in double quotes and separated by commas.
     *
     * @return A string representing the object in CSV format, where fields include:
     *         name, phone, email, address, tags and ID.
     */
    public String toCsvFormat() {
        StringBuilder tagsInCsvFormat = new StringBuilder();
        for (Tag tag : tags) {
            tagsInCsvFormat.append(tag.tagName).append(",");
        }
        if (tagsInCsvFormat.length() > 0) {
            tagsInCsvFormat.deleteCharAt(tagsInCsvFormat.length() - 1);
        }

        return "\"" + name + "\","
                + "\"" + phone + "\","
                + "\"" + email + "\","
                + "\"" + address + "\","
                + "\"" + tagsInCsvFormat + "\"";
    }
}
