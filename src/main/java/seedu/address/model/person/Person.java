package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private Set<Schedule> schedules;
    private Reminder reminder;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Schedule> schedules,
                  Reminder reminder, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, schedules, reminder, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.schedules = schedules;
        this.reminder = reminder;
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

    public Address getAddress() {
        return address;
    }
    public Set<Schedule> getSchedules() {
        return Collections.unmodifiableSet(schedules);
    }
    public Reminder getReminder() {
        return reminder;
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
     * Returns true if the person has an appointment that matches the given filters.
     *
     * @param now The current datetime to compare against.
     * @param dateFilter Optional date filter.
     * @param timeFilter Optional time filter.
     * @return true if the person has a matching appointment, false otherwise.
     */
    public boolean hasAppointment(LocalDateTime now, Optional<LocalDate> dateFilter, Optional<LocalTime> timeFilter) {

        if (schedules == null || schedules.isEmpty()) {
            return false; // Return false if schedules are null or empty
        }

        for (Schedule schedule : schedules) {
            if (schedule == null || schedule.getDateTime().isEmpty()) {
                return false;
            }

            LocalDateTime appointmentDateTime = LocalDateTime.parse(schedule.getDateTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

            // Check if the appointment is in the future
            if (appointmentDateTime.isBefore(now)) {
                return false;
            }

            // Apply date filter if present
            if (dateFilter.isPresent()) {
                if (!appointmentDateTime.toLocalDate().equals(dateFilter.get())) {
                    return false;
                }
                // Only apply time filter if date filter is present
                if (timeFilter.isPresent() && !appointmentDateTime.toLocalTime().equals(timeFilter.get())) {
                    return false;
                }
            } else if (timeFilter.isPresent()) {
                // If time filter is present but date filter is not, ignore the time filter
                return true;
            }
        }
        return true;
    }

    /**
     * Removes the appointment of the given {@code appointment}.
     */
    public void removeAppointment(Schedule appointment) {
        for (Schedule schedule : schedules) {
            if (schedule.equals(appointment)) {
                schedules.remove(appointment);
                break;
            }
        }
    }

    /**
     * Removes the reminder of the person by setting the reminder to an empty string.
     */
    public void removeReminder() {
        reminder = new Reminder("");
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
                .add("schedule", schedules)
                .add("reminder", reminder)
                .add("tags", tags)
                .toString();
    }

}
