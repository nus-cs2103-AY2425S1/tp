package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {

    /**
     * Placeholder Appointment class while waiting for
     * Appointment implementation PR.
     */
    public class Appointment {
        // Person patient;
        // Person doctor;
        LocalDateTime date;
        // String treatment;
        // String condition;
        String remark;

        public Appointment(LocalDateTime date, String remark) {
            this.date = date;
            this.remark = remark;
        }

        // Getters
        public LocalDateTime getDate() {
            return date;
        }

        public String getDescription() {
            return remark;
        }

        @Override
        public String toString() {
            return "Appointment on " + date + ": " + remark;
        }
    }

    private TreeMap<LocalDateTime, ArrayList<Appointment>>;
    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param remark
     * @param tags
     */
    public Patient(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
    }

    // TreeMap to store appointments, sorted by date
    private TreeMap<LocalDateTime, ArrayList<Appointment>> appointments = new TreeMap<>();

    // Method to add an appointment
    public void addAppointment(Appointment appointment) {
        LocalDateTime date = appointment.getDate();
        appointments.computeIfAbsent(date, k -> new ArrayList<>()).add(appointment);
    }

    // Method to get appointments by date
    public ArrayList<Appointment> getAppointmentsOn(LocalDateTime date) {
        return appointments.getOrDefault(date, new ArrayList<>());
    }

    // Method to print all appointments
    public void printAllAppointments() {
        for (LocalDateTime date : appointments.keySet()) {
            System.out.println("Appointments on " + date + ": " + appointments.get(date));
        }
    }
}
