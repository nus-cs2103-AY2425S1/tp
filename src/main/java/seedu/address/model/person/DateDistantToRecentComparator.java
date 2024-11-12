package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from distant date to recent date
 */
public class DateDistantToRecentComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        int lastSeenResult = o1.getLastSeen().compareTo(o2.getLastSeen());

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
        if (!(other instanceof DateDistantToRecentComparator)) {
            return false;
        }

        return true;
    }
}
