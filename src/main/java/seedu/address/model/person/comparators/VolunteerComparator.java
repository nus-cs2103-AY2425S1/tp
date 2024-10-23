package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;
import seedu.address.model.person.Volunteer;

/**
 * Comparator to sort Person objects, prioritizing Volunteers by hours contributed.
 * Volunteers are placed before non-volunteers, sorted by hours contributed in ascending order.
 * Non-volunteers retain their original order.
 */
public class VolunteerComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        return comparePersons(p1, p2);
    }

    private int comparePersons(Person p1, Person p2) {
        boolean isP1Volunteer = p1 instanceof Volunteer;
        boolean isP2Volunteer = p2 instanceof Volunteer;

        if (isP1Volunteer && isP2Volunteer) {
            Volunteer v1 = (Volunteer) p1;
            Volunteer v2 = (Volunteer) p2;
            return v1.compareTo(v2);
        } else if (isP1Volunteer) {
            // p1 is a volunteer, p2 is not; volunteers come before non-volunteers
            return -1;
        } else if (isP2Volunteer) {
            // p2 is a volunteer, p1 is not; volunteers come before non-volunteers
            return 1;
        } else {
            // Neither are volunteers; maintain original order
            return 0; // Return 0 to keep insertion order for non-volunteers
        }
    }
}
