package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.logic.commands.SortByPriorityCommand;

/**
 * Comparator to sort persons from high priority to low priority
 */
public class PriorityLowToHighComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o2.getPriority().compareTo(o1.getPriority());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PriorityLowToHighComparator)) {
            return false;
        }

        return true;
    }
}
