package seedu.address.model.person;

import seedu.address.model.tag.Tag;
import seedu.address.model.person.Id;
import seedu.address.model.person.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private History history;
    private Id id;

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
        id = new Id(this.getClass());
        history = new History(id);
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
