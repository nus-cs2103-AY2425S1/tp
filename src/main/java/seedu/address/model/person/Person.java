package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.AppNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Appointmentable {
    // Identity fields
    private final Name name;
    private final String role;
    private final Phone phone;
    private final Email email;
    private final Id id;
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final History history;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, String role, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.id = new Id(this.getClass());
        this.history = new History();
    }

    public Name getName() {
        return name;
    }
    public String getRole() {
        return role;
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

    public Remark getRemark() {
        return remark;
    }

    public void addNotes(String notes) {
        this.remark.addNotes(notes);
    }

    public Id getId() {
        return id;
    }

    public History getHistory() {
        return history;
    }

    public String getHistoryBase() {
        return History.getHistoryDataBase();
    }

    // Method in the class (e.g., Patient or Doctor) to retrieve the appointment details
    public String getOneHistory(LocalDateTime dateTime, Id patientId) {
        try {
            Appointment appointment = history.getOneAppointmentDetail(dateTime, patientId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String time = "DateTime: " + dateTime.format(formatter);
            return time + " " + appointment.toString();
        } catch (AppNotFoundException e) {
            return "No appointment found for the given date, patient, and doctor.";
        }
    }

    public String getPatientHistory(Id patientId) {
        String appointments = history.getAllPatientsAppointments(patientId);
        return appointments; // Assuming the Appointment class has a toString() method for formatting
    }

    public String getOneDayDoctorAppointment(LocalDate date, Id doctorId) {
        try {
            String appointments = history.getDoctorAppointmentsForDay(date, doctorId);
            return appointments; // Assuming the Appointment class has a toString() method for formatting
        } catch (AppNotFoundException e) {
            return "No appointment found for the given date and doctor.";
        }
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Adds a new appointment at the specified time, for the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    @Override
    public boolean addAppointment(LocalDateTime dateTime, Id patientId, Id doctorId, String remarks) {
        requireNonNull(dateTime);
        requireNonNull(patientId);
        requireNonNull(doctorId);
        requireNonNull(remarks);

        return history.addAppointment(dateTime, patientId, doctorId, remarks);
    }

    /**
     * Delete an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    @Override
    public boolean deleteAppointment(LocalDateTime dateTime, Id patientId, Id doctorId) {
        requireNonNull(dateTime);
        requireNonNull(patientId);
        requireNonNull(doctorId);

        return History.deleteAppointment(dateTime, patientId, doctorId);
    }

    /**
     * Gets an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    @Override
    public Appointment getAppointment(LocalDateTime dateTime, Id patientId, Id doctorId) {
        // TODO AFTER v1.3
        return null;
    }

    @Override
    public String getAllAppointments() {
        return History.getAllAppointments(this.getId());
    }

    @Override
    public boolean editAppointment(LocalDateTime dateTime, Id patientId, Id doctorId) {
        // TODO AFTER v1.3
        return false;
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ID: ")
                .append(getRole())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
