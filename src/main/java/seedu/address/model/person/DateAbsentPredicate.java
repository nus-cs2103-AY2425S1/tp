package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code attendance} does not contain the date
 * provided.
 */
public class DateAbsentPredicate implements Predicate<Person> {
    private final LocalDateTime classDate;

    /**
     * Constructs a {@code DateAbsentPredicate} with the specified class date.
     *
     * @param classDate The date and time of the class to be used for the predicate.
     * @throws NullPointerException if {@code classDate} is null.
     */
    public DateAbsentPredicate(LocalDateTime classDate) {
        requireNonNull(classDate);
        this.classDate = classDate;
    }

    @Override
    public boolean test(Person person) {
        return person.isAbsentOn(classDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DateAbsentPredicate other)) {
            return false;
        }

        return classDate.equals(other.classDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Absent on", DateTimeFormatter.ofPattern(AttendanceList.DATE_TIME_FORMAT).format(classDate))
                .toString();
    }
}
