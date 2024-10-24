package seedu.address.model.predicate;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Predicate to test if a {@code Person} has attended a tutorial within a specified date range.
 * It checks if the attendance date of the student's participation in a tutorial
 * falls between (or is equal to) the given start and end dates.
 */
public class StudentAttendedTutorialPredicate implements Predicate<Person> {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs a {@code StudentAttendedTutorialPredicate} with the given start and end dates.
     *
     * @param startDate The start date for the attendance period.
     * @param endDate The end date for the attendance period.
     */
    public StudentAttendedTutorialPredicate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Person person) {
        return person.getParticipation().stream()
                .flatMap(participation -> participation.getAttendanceList().stream()) // Flatten all attendance lists
                .anyMatch(attendance -> (
                        attendance.attendanceDate.isAfter(startDate) || attendance.attendanceDate.isEqual(startDate))
                        && (attendance.attendanceDate.isBefore(endDate) || attendance.attendanceDate.isEqual(endDate))
                );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentAttendedTutorialPredicate)) {
            return false;
        }

        StudentAttendedTutorialPredicate otherPredicate = (StudentAttendedTutorialPredicate) other;
        return this.startDate.isEqual(otherPredicate.startDate) && this.endDate.isEqual(otherPredicate.endDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startDate", this.startDate.toString())
                .add("endDate", this.endDate.toString())
                .toString();
    }
}
