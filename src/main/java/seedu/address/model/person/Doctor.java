package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.tag.Tag;

/**
 * Represents a Doctor in the address book.
 * A Doctor is a type of Person with a schedule that stores appointment histories.
 */
public class Doctor extends Person {
    private static int currentId;
    private final int id;
    private TreeMap<LocalDateTime, History> schedule; // TreeMap to store the schedule

    /**
     * Constructs a Doctor object.
     *
     * @param name Name of the doctor.
     * @param phone Phone number of the doctor.
     * @param email Email address of the doctor.
     * @param address Residential address of the doctor.
     * @param remark Additional remarks or notes.
     * @param tags Set of tags associated with the doctor.
     */
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        this.id = currentId;;
        currentId++;
        this.schedule = new TreeMap<>(); // Initialize the TreeMap
    }

    /**
     * Adds an entry to the doctor's schedule.
     *
     * @param dateTime Date and time of the appointment or event.
     * @param history The appointment or event history to be added to the schedule.
     */
    public void addScheduleEntry(LocalDateTime dateTime, History history) {
        schedule.put(dateTime, history);
    }

    /**
     * Retrieves the full schedule of the doctor.
     *
     * @return A TreeMap containing the schedule, with the LocalDateTime as the key and History as the value.
     */
    public TreeMap<LocalDateTime, History> getSchedule() {
        return schedule;
    }

    /**
     * Retrieves a specific schedule entry based on the date and time.
     *
     * @param dateTime The date and time of the desired schedule entry.
     * @return The History object associated with the specified date and time, or null if not found.
     */
    public History getScheduleEntry(LocalDateTime dateTime) {
        return schedule.get(dateTime);
    }
}
