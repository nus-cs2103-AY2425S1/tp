package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Comparator to sort Person objects by name in alphabetical order.
 */
public class NameComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return compareNamesIgnoreCase(p1.getName(), p2.getName());
    }

    public int compareNamesIgnoreCase(Name n1, Name n2) {
        return n1.compareToIgnoreCase(n2);
    }
}
