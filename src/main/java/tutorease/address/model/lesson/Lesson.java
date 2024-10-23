package tutorease.address.model.lesson;

import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import tutorease.address.model.person.Person;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson implements Comparable<Lesson> {
    private final Person student;
    private final Fee fee;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;

    /**
     * Every field must be present and not null.
     *
     * @param student       The student of the lesson.
     * @param fee           The fee of the lesson.
     * @param startDateTime The start date time of the lesson.
     * @param endDateTime   The end date time of the lesson.
     */
    public Lesson(Person student, Fee fee, StartDateTime startDateTime, EndDateTime endDateTime) {
        requireAllNonNull(student, fee, startDateTime, endDateTime);
        this.student = student;
        this.fee = fee;
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
        return student.getAddressString();
    }
    /**
     * Returns the fee of the lesson.
     *
     * @return The fee of the lesson.
     */
    public Fee getFee() {
        return fee;
    }
    /**
     * Returns the fee per hour of the lesson.
     *
     * @return The fee per hour string of the lesson.
     */
    public String getFeeString() {
        return fee.getValueString();
    }
    /**
     * Returns the amount per hour for the lesson.
     *
     * @return The amount per hour of the lesson.
     */
    public String getAmountPerHour() {
        return fee + "/hr";
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student: ")
                .append(getStudent().getName())
                .append(" Fee: ")
                .append(fee.toString())
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
                && fee.equals(otherLesson.fee)
                && startDateTime.equals(otherLesson.startDateTime)
                && endDateTime.equals(otherLesson.endDateTime);
    }
    /**
     * Compares this lesson with another lesson.
     *
     * @param lesson The other lesson to compare with.
     * @return A negative integer, zero, or a positive integer as this lesson is before, same time, or after
     *         the specified lesson.
     */
    @Override
    public int compareTo(Lesson lesson) {
        if (this.startDateTime.equals(lesson.startDateTime)) {
            return this.endDateTime.compareTo(lesson.endDateTime);
        }
        return this.startDateTime.compareTo(lesson.startDateTime);
    }
}
