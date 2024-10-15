package tutorease.address.model.lesson;

import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import tutorease.address.model.person.Person;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {
    private final Person student;

    private final LocationIndex locationIndex;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;

    /**
     * Every field must be present and not null.
     *
     * @param student       The student of the lesson.
     * @param locationIndex The location index of the lesson.
     * @param startDateTime The start date time of the lesson.
     * @param endDateTime   The end date time of the lesson.
     */
    public Lesson(Person student, LocationIndex locationIndex, StartDateTime startDateTime, EndDateTime endDateTime) {
        requireAllNonNull(student, locationIndex, startDateTime, endDateTime);
        this.student = student;
        this.locationIndex = locationIndex;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Checks if this lesson overlaps with another lesson.
     *
     * @param other The other lesson to compare with.
     * @return true if the lessons overlap, false otherwise.
     */
    public boolean isOverlapping(Lesson other) {
        return this.startDateTime.isBefore(other.endDateTime) && this.startDateTime.isAfter(other.startDateTime)
                || this.endDateTime.isAfter(other.startDateTime) && this.endDateTime.isBefore(other.endDateTime)
                || this.startDateTime.equals(other.startDateTime) || this.endDateTime.equals(other.endDateTime);
    }

    /**
     * Returns the student of the lesson.
     *
     * @return The student of the lesson.
     */
    public Person getStudent() {
        return student;
    }

    /**
     * Returns the location index of the lesson.
     *
     * @return The location index of the lesson.
     */
    public LocationIndex getLocationIndex() {
        return locationIndex;
    }

    /**
     * Returns the start date time of the lesson.
     *
     * @return The start date time of the lesson.
     */
    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Returns the end date time of the lesson.
     *
     * @return The end date time of the lesson.
     */
    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns the start date time of the lesson as a string.
     *
     * @return The start date time of the lesson as a string.
     */
    public String getStartDateTimeString() {
        return startDateTime.toString();
    }

    /**
     * Returns the end date time of the lesson as a string.
     *
     * @return The end date time of the lesson as a string.
     */
    public String getEndDateTimeString() {
        return endDateTime.toString();
    }

    /**
     * Returns the name of the student of the lesson.
     *
     * @return The name of the student of the lesson.
     */
    public String getStudentName() {
        return student.getName().fullName;
    }

    /**
     * Returns the address of the student of the lesson.
     *
     * @return The address of the student of the lesson.
     */
    public String getAddress() {
        if (this.locationIndex.getValue() == 0) {
            return student.getAddress().value;
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student: ")
                .append(getStudent().getName())
                .append(" Location: ")
                .append(getLocationIndex())
                .append(" Start: ")
                .append(getStartDateTime())
                .append(" End: ")
                .append(getEndDateTime());
        return builder.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return student.equals(otherLesson.student)
                && locationIndex.equals(otherLesson.locationIndex)
                && startDateTime.equals(otherLesson.startDateTime)
                && endDateTime.equals(otherLesson.endDateTime);
    }
}
