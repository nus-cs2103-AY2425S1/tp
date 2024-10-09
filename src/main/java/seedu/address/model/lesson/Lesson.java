package seedu.address.model.lesson;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {
    private final Person student;

    private final LocationIndex locationIndex;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;

    public Lesson(Person student, LocationIndex locationIndex, StartDateTime startDateTime, EndDateTime endDateTime) {
        this.student = student;
        this.locationIndex = locationIndex;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Checks if this lesson overlaps with another lesson.
     * @param other The other lesson to compare with.
     * @return true if the lessons overlap, false otherwise.
     */
    public boolean isOverlapping(Lesson other) {
        return this.startDateTime.isBefore(other.endDateTime) && other.startDateTime.isBefore(this.endDateTime);
    }

    public Person getStudent() {
        return student;
    }

    public LocationIndex getLocationIndex() {
        return locationIndex;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }
}
