package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Comparator object that compares two persons based on their name.
 */
public class PersonDateReverseComparator implements Comparator<Person> {
    /**
     * Compares two persons based on their names.
     */
    public int compare(Person p1, Person p2) {
        // Place all potential hires at the bottom as
        // they should not have a contract end date
        boolean isEmployeeP1 = p1.isEmployee();
        boolean isEmployeeP2 = p2.isEmployee();
        if (!isEmployeeP1 && isEmployeeP2) {
            return 1;
        } else if (isEmployeeP1 && !isEmployeeP2) {
            return -1;
        } else if (!isEmployeeP1 && !isEmployeeP2) {
            return 0;
        } else {
            return -p1.getContractEndDate().compareTo(p2.getContractEndDate());
        }
    }
}
