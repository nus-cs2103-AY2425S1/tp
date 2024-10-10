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
     * Placeholder History class while waiting for
     * Appointment implementation PR.
     */
    public class History {
        private TreeMap<LocalDateTime, Pair<Id, String>> db;
        private Id id;

        public History(Id id) {
            this.id = id;
        }

        /**
         * Adds appointment to be associated with Patient.
         *
         * @param time Time of appointment.
         * @param description Description associated with the appointment.
         */
        public void add(LocalDateTime time, Pair<Id, String> description) {
            // TODO: relevant extra code like conditional checks, dupe checking
            db.put(time, description);
            // TODO: need to do something with doctor schedule also
        }

        /**
         * Updates a specified appointment.
         *
         * @param time Time of specified appointment.
         * @param description Updated description of appointment.
         */
        public void update(LocalDateTime time, Pair<Id, String> description) {
            // TODO, separate functionality by Patient or Doctor (probably need extra param)
        }

        /**
         * Returns all the appointments of the specified patient.
         *
         * @param id Specified Id of the patient.
         * @return String representing all the appointments associated with the patient.
         */
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

    /**
     * Adds an appointment within the Patient's History.
     *
     * @param time Time of appointment.
     * @param description Description of appointment.
     */
    public void addAppointment(LocalDateTime time, Pair<Id, String> description) {
        requireNonNull(time);
        requireNonNull(description);
        history.add(time, description);
    }

    /**
     * Updates appointment associated with Patient in their History.
     *
     * @param time Time of appointment.
     * @param description Updated description to be used.
     */
    public void updateAppointment(LocalDateTime time, Pair<Id, String> description) {
        requireNonNull(time);
        requireNonNull(description);
        history.update(time, description);
    }

    /**
     * Returns all appointments of a specified Id.
     *
     * @param id Patient Id to be specified.
     * @return String representing all appointments of the Patient.
     */
    public String getAllAppointments(Id id) {
        return history.getAll(id);
    }
}
