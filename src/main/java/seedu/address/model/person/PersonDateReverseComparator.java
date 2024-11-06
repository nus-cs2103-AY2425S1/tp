package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.model.person.ContractEndDate.EmptyContractEndDate;
import seedu.address.model.person.ContractEndDate.FilledContractEndDate;

/**
 * A Comparator object that compares two persons based on their name.
 */
public class PersonDateReverseComparator implements Comparator<Person> {
    /**
     * Compares two persons based on their names.
     */
    public int compare(Person p1, Person p2) {
        ContractEndDate d1 = p1.getContractEndDate();
        ContractEndDate d2 = p2.getContractEndDate();
        if (d1 instanceof EmptyContractEndDate && d2 instanceof FilledContractEndDate) {
            return 1;
        } else if (d1 instanceof FilledContractEndDate && d2 instanceof EmptyContractEndDate) {
            return -1;
        }
        return -d1.compareTo(d2);
    }
}
