package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from distant date to recent date
 */
public class DateDistantToRecentComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        int comparisonResult = o1.getLastSeen().compareTo(o2.getLastSeen());

        if (comparisonResult != 0) {
            return comparisonResult;
        }

        // If same priority, compare by name for tie-breaker
        return o1.getName().compareTo(o2.getName());
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
