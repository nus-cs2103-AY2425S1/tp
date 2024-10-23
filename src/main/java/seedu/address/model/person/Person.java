package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
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
    private final Age age;
    private final Sex sex;
    private final Set<Appointment> appointments = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private final Note note;
    private final StarredStatus starredStatus;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Age age, Sex sex, Set<Appointment> appointments,
                  Set<Tag> tags, Note note, StarredStatus starredStatus) {
        requireAllNonNull(name, phone, email, address, age, sex, appointments, tags, note, starredStatus);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.age = age;
        this.sex = sex;
        this.appointments.addAll(appointments);
        this.tags.addAll(tags);
        this.note = note;
        this.starredStatus = starredStatus;
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

    public Age getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Appointment> getAppointment() {
        return Collections.unmodifiableSet(appointments);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public StarredStatus getStarredStatus() {
        return starredStatus;
    }

    /**
     * Handles due appointments by checking if any appointments are before today's date at midnight.
     * If an appointment is due, it removes it from the person's appointments
     * and adds it to the note's previous appointments.
     */
    public void handleDueAppointments() {
        LocalDateTime todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

        Set<Appointment> appointmentsToRemove = new HashSet<>();
        List<Appointment> modifiableAppointments = new ArrayList<>(appointments);
        for (Appointment appointment : modifiableAppointments) {
            if (appointment.appointment.isBefore(todayMidnight)) {
                appointmentsToRemove.add(appointment);
                note.addAppointment(appointment.appointment.format(formatter));
            }
        }
        modifiableAppointments.removeAll(appointmentsToRemove);
        appointments.clear();
        appointments.addAll(modifiableAppointments);
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
     * Retrieves the earliest appointment date among this person's appointments.
     *
     * @return the earliest appointment date as a LocalDateTime, or null if there are no appointments.
     */
    public LocalDateTime getEarliestAppointmentDate() {
        return appointments.stream()
                .map(Appointment::getDate) // Assuming Appointment has a method getDate() that returns LocalDateTime
                .min(LocalDateTime::compareTo) // Finds the minimum date
                .orElse(null); // Returns null if there are no appointments
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
                && age.equals(otherPerson.age)
                && sex.equals(otherPerson.sex)
                && appointments.equals(otherPerson.appointments)
                && tags.equals(otherPerson.tags)
                && note.equals(otherPerson.note);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, age, sex, tags, appointments, starredStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("age", age)
                .add("sex", sex)
                .add("appointments", appointments)
                .add("tags", tags)
                .add("starredStatus", starredStatus)
                .toString();
    }

}
