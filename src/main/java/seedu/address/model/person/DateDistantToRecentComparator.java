package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from distant date to recent date
 */
public class DateDistantToRecentComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o1.getLastSeen().compareTo(o2.getLastSeen());
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
