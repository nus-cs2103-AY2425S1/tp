package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
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
    private final int id;
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private List<Appointment> appointments;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, String role, Phone phone, Email email,
                  Address address, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, role, phone, email, address, tags);
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.id = new Id(role).getIdValue();
        this.appointments = new ArrayList<>();
    }

    /**
     * Creates a Person with the given  fields. Each field must be present and not null.
     */
    public Person(Name name, int id, String role, Phone phone, Email email,
                  Address address, Remark remark, List<Appointment> appointments, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.id = id;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.appointments = appointments;
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

    public int getId() {
        return id;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }

    //    // Method in the class (e.g., Patient or Doctor) to retrieve the appointment details
    //    public String getOneHistory(LocalDateTime dateTime, Id patientId) {
    //        try {
    //            Appointment appointment = history.getOneAppointmentDetail(dateTime, patientId);
    //            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //            String time = "DateTime: " + dateTime.format(formatter);
    //            return time + " " + appointment.toString();
    //        } catch (AppNotFoundException e) {
    //            return "No appointment found for the given date, patient, and doctor.";
    //        }
    //    }
    //
    //    public String getOneDayDoctorAppointment(LocalDate date, Id doctorId) {
    //        try {
    //            String appointments = history.getDoctorAppointmentsForDay(date, doctorId);
    //            return appointments; // Assuming the Appointment class has a toString() method for formatting
    //        } catch (AppNotFoundException e) {
    //            return "No appointment found for the given date and doctor.";
    //        }
    //    }


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
    public boolean addAppointment(LocalDateTime dateTime, int patientId, int doctorId, String remarks) {
        requireAllNonNull(dateTime, patientId, doctorId, remarks);

        return appointments.add(new Appointment(dateTime, patientId, doctorId, remarks));
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
    public boolean deleteAppointment(LocalDateTime dateTime, int patientId, int doctorId) {
        requireAllNonNull(dateTime, patientId, doctorId);
        return appointments.remove(new Appointment(dateTime, patientId, doctorId, ""));
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
    public Appointment getAppointment(LocalDateTime dateTime, int patientId, int doctorId) {
        requireAllNonNull(dateTime, patientId, doctorId);
        return appointments.stream()
                .filter(apt -> apt.equals(new Appointment(dateTime, patientId, doctorId, "")))
                .collect(Collectors.toList()).get(0);
    }

    public Appointment getAppointment(LocalDateTime dateTime, int patientId) throws CommandException {
        requireAllNonNull(dateTime, patientId);
        List<Appointment> apts = appointments.stream()
                .filter(apt -> apt.getDateTime().toString().equals(dateTime.toString()))
                .filter(apt -> apt.getPatientId() == patientId)
                .collect(Collectors.toList());
        if (apts.isEmpty() || apts.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_APPOINTMENTS_FOUND);
        }

        return apts.get(0);
    }

    public String getStringAppointments() {
        final StringBuilder builder = new StringBuilder();
        appointments.stream()
                .forEach(builder::append);
        return builder.toString();
    }

    public String getStringAppointmentsForDay(LocalDate date) {
        requireAllNonNull(date);

        // Filter appointments by the given date
        List<Appointment> appointmentsForDay = appointments.stream()
                .filter(appointment -> appointment.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());

        // Check if there are no appointments for the day
        if (appointmentsForDay.isEmpty()) {
            return null;
        }

        // Build a string for the appointments on the given day
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Appointments on %s:\n", date.toString()));
        appointmentsForDay.forEach(appointment ->
                builder.append(appointment.toString()).append("\n")
        );

        return builder.toString();

    }


    @Override
    public boolean editAppointment(LocalDateTime dateTime, int patientId, int doctorId) {
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
                .append(getId())
                .append(" Role: ")
                .append(getRole())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Appointments: ");
        if (getAppointments() != null) {
            getAppointments().forEach(builder::append);
        }
        builder.append(getAppointments())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}


