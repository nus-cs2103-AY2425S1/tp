package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Comparator object that compares two persons based on their department.
 */
public class PersonDepartmentComparator implements Comparator<Person> {
    /**
     * Compares two persons based on their department.
     */
    public int compare(Person p1, Person p2) {
        String p1Dept = p1.getDepartment().value.toLowerCase();
        String p2Dept = p2.getDepartment().value.toLowerCase();
        return p1Dept.compareTo(p2Dept);
    }
}
