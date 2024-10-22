package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from recent date to distant date
 */
public class DateRecentToDistantComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o2.getLastSeen().compareTo(o1.getLastSeen());
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
