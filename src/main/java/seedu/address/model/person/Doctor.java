package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

public class Doctor extends Person{
    /**
     * Placeholder Appointment class while waiting for
     * Appointment implementation PR.
     */
    public class History {
        private TreeMap<LocalDateTime, Pair<Id, String>> db;
        private Id id;

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

    // Method to add an appointment
    public void addAppointment(LocalDateTime time, Pair<Id, String> description) {
        requireNonNull(time);
        requireNonNull(description);
        history.add(time, description);
    }

    // Method to update appointment
    public void updateAppointment(LocalDateTime time, Pair<Id, String> description) {
        requireNonNull(time);
        requireNonNull(description);
        history.update(time, description);
    }

    // Method to get all appointments
    public String getAllAppointments(Id id) {
        return history.getAll(id);
    }
}
