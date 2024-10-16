package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Meeting}'s {@code date} is within the week of the {@code date} given.
 */
public class SameWeekAsDatePredicate implements Predicate<Meeting> {

    private final LocalDate date;

    public SameWeekAsDatePredicate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean test(Meeting meeting) {
        WeekFields weekfields = WeekFields.of(Locale.getDefault());
        return date.get(weekfields.weekOfWeekBasedYear())
                == meeting.getMeetingDate().get(weekfields.weekOfWeekBasedYear());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SameWeekAsDatePredicate)) {
            return false;
        }

        SameWeekAsDatePredicate otherSameWeekAsDatePredicate = (SameWeekAsDatePredicate) other;
        return date.equals(otherSameWeekAsDatePredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
