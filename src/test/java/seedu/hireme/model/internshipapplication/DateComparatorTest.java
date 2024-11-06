package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.BOFA;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
public class DateComparatorTest {

    @Test
    public void equals() {
        DateComparator firstComparator = new DateComparator(true);
        DateComparator secondComparator = new DateComparator(false);

        // same object -> returns true
        assertTrue(firstComparator.equals(firstComparator));

        // same values -> returns true
        DateComparator firstComparatorCopy = new DateComparator(true);
        assertTrue(firstComparator.equals(firstComparatorCopy));

        // different types -> returns false
        assertFalse(firstComparator.equals(1));

        // null -> returns false
        assertFalse(firstComparator.equals(null));

        // different comparator -> returns false
        assertFalse(firstComparator.equals(secondComparator));
    }

    @Test
    public void compare_internshipApplicationsOrder_returnsInteger() {
        // Sort by earliest
        DateComparator comparator1 = new DateComparator(true);
        assertTrue(comparator1.compare(APPLE, BOFA) < 0);
        assertTrue(comparator1.compare(BOFA, APPLE) > 0);

        // Same order
        assertEquals(0, comparator1.compare(APPLE, APPLE));
        assertEquals(0, comparator1.compare(BOFA, BOFA));

        // Sort by latest
        DateComparator comparator2 = new DateComparator(false);
        assertTrue(comparator2.compare(BOFA, APPLE) < 0);
        assertTrue(comparator2.compare(APPLE, BOFA) > 0);

        // Same order
        assertEquals(0, comparator2.compare(APPLE, APPLE));
        assertEquals(0, comparator2.compare(BOFA, BOFA));
    }

    @Test
    public void toStringMethod() {
        DateComparator comparator1 = new DateComparator(true);
        String expected1 = DateComparator.class.getCanonicalName() + "{isEarliestOrder=" + true + "}";
        assertEquals(expected1, comparator1.toString());

        DateComparator comparator2 = new DateComparator(false);
        String expected2 = DateComparator.class.getCanonicalName() + "{isEarliestOrder=" + false + "}";
        assertEquals(expected2, comparator2.toString());
    }

    @Test
    public void compare_largeNumberOfDates_correctOrder() {
        DateComparator earliestOrderComparator = new DateComparator(true);
        DateComparator latestOrderComparator = new DateComparator(false);

        // Generate a series of InternshipApplications with sequential dates
        List<InternshipApplication> applications = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            applications.add(new InternshipApplication(
                    new Company(new Email("company" + i + "@example.com"), new Name("Company " + i)),
                    new Date(String.format("%02d/01/23", i)),
                    new Role("Role " + i)
            ));
        }

        // Sort applications by earliest order
        applications.sort(earliestOrderComparator);
        for (int i = 0; i < applications.size() - 1; i++) {
            assertTrue(applications.get(i).getDateOfApplication()
                    .compareTo(applications.get(i + 1).getDateOfApplication()) <= 0);
        }

        // Sort applications by latest order
        applications.sort(latestOrderComparator);
        for (int i = 0; i < applications.size() - 1; i++) {
            assertTrue(applications.get(i).getDateOfApplication()
                    .compareTo(applications.get(i + 1).getDateOfApplication()) >= 0);
        }
    }

    @Test
    public void compare_reverseOrderConsistency() {
        DateComparator comparator = new DateComparator(true);

        assertEquals(-comparator.compare(APPLE, BOFA), comparator.compare(BOFA, APPLE));
    }

    @Test
    public void compare_sameDateApplications_consistentResults() {
        DateComparator earliestOrderComparator = new DateComparator(true);
        DateComparator latestOrderComparator = new DateComparator(false);

        InternshipApplication app1 = new InternshipApplication(
                new Company(new Email("same@example.com"), new Name("Same Date Company")),
                new Date("01/01/23"),
                new Role("Role A")
        );
        InternshipApplication app2 = new InternshipApplication(
                new Company(new Email("same2@example.com"), new Name("Same Date Company 2")),
                new Date("01/01/23"),
                new Role("Role B")
        );

        // Both comparators should treat applications with the same date as equal
        assertEquals(0, earliestOrderComparator.compare(app1, app2));
        assertEquals(0, latestOrderComparator.compare(app1, app2));
    }
}
