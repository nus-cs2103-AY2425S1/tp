package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;
import seedu.address.model.tag.Tag;

public class Doctor extends Person {
    private static int ID;
    private final int id;
    private TreeMap<LocalDateTime, History> schedule; // TreeMap to store the schedule

    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        this.id = ID;
        ID++;
        this.schedule = new TreeMap<>(); // Initialize the TreeMap
    }

    // Method to add an entry to the schedule
    public void addScheduleEntry(LocalDateTime dateTime, History history) {
        schedule.put(dateTime, history);
    }

    // Method to retrieve the schedule
    public TreeMap<LocalDateTime, History> getSchedule() {
        return schedule;
    }

    // Example method to get a specific entry
    public History getScheduleEntry(LocalDateTime dateTime) {
        return schedule.get(dateTime);
    }
}
