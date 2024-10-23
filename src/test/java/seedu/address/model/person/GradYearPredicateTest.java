package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GradYearPredicateTest {

    @Test
    public void equals() {
        GradYear firstGradYear = new GradYear("2020");
        GradYear secondGradYear = new GradYear("2024");

        GradYearPredicate firstPredicate = new GradYearPredicate(firstGradYear);
        GradYearPredicate secondPredicate = new GradYearPredicate(secondGradYear);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GradYearPredicate firstPredicateCopy = new GradYearPredicate(firstGradYear);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different gradYear -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_gradYearPast_returnsTrue() {
        // Grad year is already past
        GradYear presentYear = new GradYear(String.valueOf(Year.now().getValue()));
        String gradYear = "2020";
        GradYearPredicate predicate = new GradYearPredicate(presentYear);
        assertTrue(predicate.test(new PersonBuilder().withGradYear(gradYear).build()));
    }

    @Test
    public void test_gradYearNotPast_returnsFalse() {
        GradYear presentYear = new GradYear(String.valueOf(Year.now().getValue()));
        GradYearPredicate predicate = new GradYearPredicate(presentYear);

        // Grad year is in the future
        String gradYear = "2080";
        assertFalse(predicate.test(new PersonBuilder().withGradYear(gradYear).build()));

        // Grad year is the current year
        gradYear = presentYear.toString();
        assertFalse(predicate.test(new PersonBuilder().withGradYear(gradYear).build()));
    }

    @Test
    public void toStringMethod() {
        GradYear gradYear = new GradYear("2080");
        GradYearPredicate predicate = new GradYearPredicate(gradYear);

        String expected = GradYearPredicate.class.getCanonicalName() + "{graduationYear=" + gradYear + "}";
        assertEquals(expected, predicate.toString());
    }
}
