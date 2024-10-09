package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.tag.Tag;

public class Doctor extends Person{
    /**
     * Placeholder Appointment class while waiting for
     * Appointment implementation PR.
     */
    public class History {
        private TreeMap<LocalDateTime, Pair<Id, String>> db;
        private final Id id;

        public History(Id id) {
            this.id = id;
        }

        public void add(LocalDateTime time, Pair<Id, String> description) {
            // TODO: relevant extra code like conditional checks, dupe checking
            db.put(time, description);
            // TODO: need to do something with doctor schedule also
        }

        public void update(LocalDateTime time, Pair<Id, String> description) {
            // TODO, separate functionality by Patient or Doctor (probably need extra param)
        }

        public String getAll(Id id) {
            // TODO, separate functionality by Patient or Doctor
            return "";
        }

        public void get(LocalDateTime dateTime) {
        }
    }

    private History history;
    private Id id;
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
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        id = new Id(this.getClass());
        history = new Doctor.History(id);
    }

      /**
     * Adds an entry to the doctor's schedule.
     *
     * @param dateTime Date and time of the appointment or event.
     * TODO
     */
    public void addAppointment(LocalDateTime dateTime, Pair<Id, String> description) {
        requireNonNull(dateTime);
        requireNonNull(description);
        history.add(dateTime, description);
    }

    // Method to update appointment
    public void updateAppointment(LocalDateTime time, Pair<Id, String> description) {
        requireNonNull(time);
        requireNonNull(description);
        history.update(time, description);
    }

    /**
     * Retrieves the full schedule of the doctor.
     *
     * @return A TODO representing the TreeMap containing the doctor's schedule,
     * with the LocalDateTime as the key and History as the value.
     */
    public String getAllAppointments(Id id) {
        return history.getAll(id);
    }
    /**
     * Retrieves a specific schedule entry based on the date and time. TODO
     *
     * @param dateTime The date and time of the desired schedule entry.
     * @return Pair<Id, String> The History object associated with the specified date and time, or null if not found.
     */
    public void getScheduleEntry(LocalDateTime dateTime) {
        // return history.get(dateTime);
    }
}
