package seedu.address.model.person;

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

    public DateAbsentPredicate(LocalDateTime classDate) {
        this.classDate = classDate;
    }

    @Override
    public boolean test(Person person) {
        return new Attendance(false).equals(person.getAttendanceList().getMap().getOrDefault(classDate, null));
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
                .add("Absent on", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(classDate))
                .toString();
    }
}
