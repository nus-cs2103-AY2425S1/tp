package seedu.address.model.preferredtime;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code PreferredTime} overlaps with any of the ranges given.
 */
public class PreferredTimeOverlapsRangesPredicate implements Predicate<Person> {
    private final List<String> ranges;

    public PreferredTimeOverlapsRangesPredicate(List<String> ranges) {
        this.ranges = ranges;
    }

    @Override
    public boolean test(Person person) {
        return ranges.stream()
                .anyMatch(range -> overlapsRanges(person.getPreferredTimes(), range));
    }

    private static boolean overlapsRanges(Set<PreferredTime> preferredTimes, String range) {
        return preferredTimes.stream()
                .anyMatch(preferredTime -> preferredTime.overlaps(new PreferredTime(range)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PreferredTimeOverlapsRangesPredicate)) {
            return false;
        }

        PreferredTimeOverlapsRangesPredicate otherPreferredTimeOverlapsRangesPredicate =
                (PreferredTimeOverlapsRangesPredicate) other;
        return ranges.equals(otherPreferredTimeOverlapsRangesPredicate.ranges);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("ranges", ranges).toString();
    }
}
