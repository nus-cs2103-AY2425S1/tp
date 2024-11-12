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
    private final WeekFields weekfields;

    /**
     * @param date of the week
     */
    public SameWeekAsDatePredicate(LocalDate date) {
        this.date = date;
        this.weekfields = WeekFields.of(Locale.getDefault());
    }

    @Override
    public boolean test(Meeting meeting) {
        return date.get(weekfields.weekOfWeekBasedYear())
                == meeting.getMeetingDate().get(weekfields.weekOfWeekBasedYear())
                && date.getYear() == meeting.getMeetingDate().getYear();
    }

    /**
     * @return Start date of the week
     */
    public LocalDate getStartDateOfWeek() {
        return date.with(weekfields.dayOfWeek(), 1);
    }

    /**
     * @return Last date of the week
     */
    public LocalDate getLastDateOfWeek() {
        return date.with(weekfields.dayOfWeek(), 7);
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
