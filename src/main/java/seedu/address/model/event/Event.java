package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.exceptions.ChronologicalOrderException;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    private static int nextId = 0;

    // Identity fields
    private final int id;
    private final EventName eventName;
    private final Location location;
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private final Description description;
    private final ObservableList<String> volunteers;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName eventName, Location location, Date date,
                 Time startTime, Time endTime, Description description, List<String> volunteers)
                    throws ChronologicalOrderException {
        requireAllNonNull(eventName, location, date, startTime, endTime, description, volunteers);
        if (!startTime.isBefore(endTime)) {
            throw new ChronologicalOrderException();
        }

        this.id = nextId;
        nextId++;

        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.volunteers = FXCollections.observableArrayList(volunteers);
    }

    /**
     * Constructs an {@code Event} object with the specified event name, location, date, start time, and end time.
     * Uses an empty description and volunteer list by default.
     */
    public Event(EventName eventName, Location location, Date date, Time startTime, Time endTime,
                 Description description) throws ChronologicalOrderException {
        this(eventName, location, date, startTime, endTime, description, FXCollections.observableArrayList());
    }

    /**
     * Constructs an {@code Event} object with the specified event name, location, date, start time, and end time.
     * Uses an empty description and volunteer list by default.
     */
    public Event(EventName eventName, Location location, Date date, Time startTime, Time endTime)
            throws ChronologicalOrderException {
        this(eventName, location, date, startTime, endTime, new Description(), FXCollections.observableArrayList());
    }

    public int getId() {
        return id;
    }

    public EventName getName() {
        return eventName;
    }

    public Location getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Description getDescription() {
        return description;
    }
    public ObservableList<String> getVolunteers() {
        return volunteers;
    }

    /**
     * Adds a volunteer to the event's list of participants.
     *
     * @param newVolunteer The volunteer to add.
     */
    public void assignVolunteer(String newVolunteer) {
        volunteers.add(newVolunteer);
    }

    /**
     * Removes a volunteer from the event.
     *
     * @param unassignedVolunteer The name of the volunteer to unassign.
     */
    public void unassignVolunteer(String unassignedVolunteer) {
        volunteers.remove(unassignedVolunteer);
    }

    /**
     * Returns true if the event has the specified volunteer.
     *
     * @param volunteerName The name of the volunteer to check for.
     * @return True if the volunteer is in the event's list of participants.
     */
    public boolean hasVolunteer(String volunteerName) {
        return this.volunteers.contains(volunteerName);
    }

    /**
     * Returns true if both events have the same name.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null
                && otherEvent.getName().equals(getName());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventName.equals(otherEvent.eventName)
                && location.equals(otherEvent.location)
                && date.equals(otherEvent.date)
                && startTime.equals(otherEvent.startTime)
                && endTime.equals(otherEvent.endTime)
                && description.equals(otherEvent.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, location, date, startTime, endTime, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", eventName)
                .add("location", location)
                .add("date", date)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("description", description)
                .toString();
    }
}
