package seedu.address.model.person.sorters;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Sorts persons such that VIPs appear first.
 */
public class ByVipSorter implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        int res = 0;
        if (o1.isVip()) {
            --res;
        }
        if (o2.isVip()) {
            ++res;
        }
        return res;
    }
}
