package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from recent date to distant date
 */
public class DateRecentToDistantComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        int lastSeenResult = o2.getLastSeen().compareTo(o1.getLastSeen());

        if (lastSeenResult != 0) {
            return lastSeenResult;
        }

        // If same priority, compare by email for tie-breaker

        return o1.getEmail().compareTo(o2.getEmail());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof DateRecentToDistantComparator)) {
            return false;
        }

        return true;
    }
}
