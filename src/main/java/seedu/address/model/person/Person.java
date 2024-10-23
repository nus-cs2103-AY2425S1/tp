package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final List<String> schedules;
    private final UUID uid;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        schedules = new ArrayList<>();
        this.uid = UUID.randomUUID();
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, UUID uid) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        schedules = new ArrayList<>();
        this.uid = uid;
    }
    public Name getName() {
        return name;
    }

    /**
     * Returns true if the person's name contains the keyword.
     */
    public boolean nameContainsKeyword(String keyword) {
        return name.fullName.toLowerCase().contains(keyword.toLowerCase());
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

    public UUID getUid() {
        return uid;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the person has the specified tag.
     */
    public boolean hasTag(String tag) {
        return tags.stream().anyMatch(t -> t.tagName.toLowerCase().equals(tag.toLowerCase()));
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
     * This defines a weaker notion of equality between two persons based on UUID.
     * @param uid The UUID to compare with.
     * @return True if the UUID is the same as the person's UUID.
     */
    public boolean isSamePersonUid(UUID uid) {
        return this.uid.equals(uid);
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
        return Objects.hash(name, phone, email, address, tags);
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
     * Adds a new schedule entry to the list of schedules for the person.
     * The schedule is stored as a formatted string containing the event name,
     * date, and time.
     *
     * @param name The name or description of the schedule event.
     * @param date The date of the event in LocalDate format.
     * @param time The time of the event in LocalTime format.
     */
    public void addSchedule(String name, LocalDate date, LocalTime time) {
        String schedule = name + " on " + date + " at " + time;
        schedules.add(schedule);
    }

    public boolean hasScheduleConflict(LocalDate date, LocalTime time) {
        return schedules.stream().anyMatch(s -> s.contains(date.toString()) && s.contains(time.toString()));
    }

    // Getter for schedules, if needed
    public List<String> getSchedules() {
        return schedules;
    }

}
