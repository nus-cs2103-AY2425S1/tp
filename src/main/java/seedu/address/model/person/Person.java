package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllPositive;
import static seedu.address.logic.Messages.MESSAGE_COMPLETED_APPOINTMENT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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
    private Remark remark;
    private List<Appointment> appointments;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, String role, Phone phone, Email email,
                  Address address, Remark remark) {
        requireAllNonNull(name, role, phone, email, address);
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.id = new Id(role).getIdValue();
        this.appointments = new ArrayList<>();
    }

    /**
     * Creates a patient with a fixed ID
     */
    public Person(Name name, int id, String role, Phone phone, Email email,
                  Address address, Remark remark) {
        requireAllNonNull(name, role, phone, email, address);
        requireAllPositive(id);
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.id = id;
        this.appointments = new ArrayList<>();
    }

    /**
     * Creates a Person with the given fields. Each field must be present and not null.
     */
    public Person(Name name, int id, String role, Phone phone, Email email,
                  Address address, Remark remark, List<Appointment> appointments) {
        requireAllNonNull(name, phone, email, address);
        requireAllPositive(id);
        this.name = name;
        this.id = id;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
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

    public List<Appointment> getAllAppointments() {
        return this.appointments;
    }

    /**
     * Adds additional remarks to a person
     */
    public Remark addRemarks(String remarks) {
        remark.addRemarks(remarks);
        return remark;
    }

    public int getId() {
        return id;
    }

    public List<Appointment> getAppointments() {
        return appointments;
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
                && normalizeName(otherPerson.getName()).equals(normalizeName(getName()))
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Normalizes a name by converting it to lowercase and removing all spaces.
     */
    private String normalizeName(Name name) {
        return name.toString().toLowerCase().replaceAll("\\s+", "");
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
        // Check for duplicates before adding
        boolean appointmentExists = appointments.stream()
                .anyMatch(appointment -> appointment.equals(new Appointment(dateTime, patientId, doctorId, remarks)));

        if (appointmentExists) {
            return false; // Appointment already exists, do not add
        }
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
     * Deletes all appointments for this person based on their role.
     * If the role is "patient", deletes appointments where this person is the patient.
     * If the role is "doctor", deletes appointments where this person is the doctor.
     */
    public void deleteAllAppointments(Model model) {
        if (role.equals("patient")) {
            for (Appointment appointment : appointments) {
                if (appointment.getDoctorId() == this.id) {
                    ObservableList<Person> allPersons = model.getFilteredPersonList();
                    Person doctor = model.getFilteredPersonById(allPersons, appointment.getDoctorId());
                    doctor.deleteAppointment(appointment.getDateTime(), appointment.getPatientId(), this.id);
                }
            }
        } else {
            for (Appointment appointment : appointments) {
                if (appointment.getDoctorId() == this.id) {
                    ObservableList<Person> allPersons = model.getFilteredPersonList();
                    Person patient = model.getFilteredPersonById(allPersons, appointment.getPatientId());
                    patient.deleteAppointment(appointment.getDateTime(), appointment.getPatientId(), this.id);
                }
            }
        }
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
    public Appointment getAppointment(LocalDateTime dateTime, int patientId, int doctorId) throws CommandException {
        requireAllNonNull(dateTime, patientId, doctorId);
        List<Appointment> apts = appointments.stream()
                .filter(apt -> apt.equals(new Appointment(dateTime, patientId, doctorId, "")))
                .collect(Collectors.toList());
        if (apts.isEmpty() || apts.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_APPOINTMENTS_FOUND);
        }

        return apts.get(0);

    }

    public String getOneHistory(LocalDateTime dateTime, int personId) {
        requireAllNonNull(dateTime, personId);
        List<Appointment> apts = appointments.stream()
                .filter(apt -> apt.getDateTime().equals(dateTime))
                .filter(apt -> (this.role.equals("PATIENT") && apt.getPatientId() == personId)
                        || (this.role.equals("DOCTOR") && apt.getDoctorId() == personId))
                .collect(Collectors.toList());
        if (apts.isEmpty()) {
            return null;
        }
        return apts.get(0).toString();
    }

    public String getStringAppointments() {
        final StringBuilder builder = new StringBuilder();
        appointments.stream()
                .forEach(appointment ->
                        builder.append(appointment).append("\n")); // Add newline after each appointment
        if (builder.isEmpty()) {
            return null;
        }
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

    public void editRemark(Remark remark) {
        this.remark = remark;
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
                && otherPerson.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address);
    }

    @Override
    public void markAppointment(LocalDateTime dateTime, int patientId, int doctorId)
            throws CommandException {
        requireAllNonNull(dateTime, patientId, doctorId);
        Appointment appointment;
        appointment = getAppointment(dateTime, patientId, doctorId);
        if (appointment.isCompleted()) {
            throw new CommandException(MESSAGE_COMPLETED_APPOINTMENT);
        }
        appointment.markAsComplete();
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
        return builder.toString();
    }

}


