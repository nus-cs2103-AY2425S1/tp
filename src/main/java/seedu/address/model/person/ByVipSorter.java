package seedu.address.model.person;

import java.util.Comparator;

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
