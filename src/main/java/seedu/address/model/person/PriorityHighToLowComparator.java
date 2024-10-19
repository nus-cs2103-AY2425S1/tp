package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.logic.commands.SortByPriorityCommand;

/**
 * Comparator to sort persons from high priority to low priority
 */
public class PriorityHighToLowComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o1.getPriority().compareTo(o2.getPriority());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PriorityHighToLowComparator)) {
            return false;
        }

        return true;
    }
}
