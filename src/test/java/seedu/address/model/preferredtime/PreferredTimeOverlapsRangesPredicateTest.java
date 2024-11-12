package seedu.address.model.preferredtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;


public class PreferredTimeOverlapsRangesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateRangeList = Collections.singletonList("1200-1300");
        List<String> secondPredicateRangeList = Arrays.asList("1200-1300", "2000-2300");

        PreferredTimeOverlapsRangesPredicate firstPredicate =
                new PreferredTimeOverlapsRangesPredicate(firstPredicateRangeList);
        PreferredTimeOverlapsRangesPredicate secondPredicate =
                new PreferredTimeOverlapsRangesPredicate(secondPredicateRangeList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PreferredTimeOverlapsRangesPredicate firstPredicateCopy =
                new PreferredTimeOverlapsRangesPredicate(firstPredicateRangeList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different PreferredTimePredicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_rangeOverlapsPreferredTimes_returnsTrue() {
        // One range
        PreferredTimeOverlapsRangesPredicate predicate =
                new PreferredTimeOverlapsRangesPredicate(Collections.singletonList("1200-1300"));
        assertTrue(predicate.test(new PersonBuilder().withPreferredTimes("1200-2300").build()));

        // Multiple ranges
        predicate = new PreferredTimeOverlapsRangesPredicate(Arrays.asList("1200-1300", "2300-2330"));
        assertTrue(predicate.test(new PersonBuilder().withPreferredTimes("1200-1300", "2300-2330").build()));

        // Only one matching range
        predicate = new PreferredTimeOverlapsRangesPredicate(Arrays.asList("2000-2200", "2300-2330"));
        assertTrue(predicate.test(new PersonBuilder().withPreferredTimes("0900-1030", "2000-2200").build()));
    }

    @Test
    public void test_rangeDoesNotOverlapPreferredTimes_returnsFalse() {
        // Zero ranges
        PreferredTimeOverlapsRangesPredicate predicate =
                new PreferredTimeOverlapsRangesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPreferredTimes("1200-1300").build()));

        // Non-overlapping range
        predicate = new PreferredTimeOverlapsRangesPredicate(Arrays.asList("2000-2200"));
        assertFalse(predicate.test(new PersonBuilder().withPreferredTimes("1200-1300", "2300-2330").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> ranges = List.of("1200-1300", "2300-2330");
        PreferredTimeOverlapsRangesPredicate predicate = new PreferredTimeOverlapsRangesPredicate(ranges);

        String expected = PreferredTimeOverlapsRangesPredicate.class.getCanonicalName() + "{ranges=" + ranges + "}";
        assertEquals(expected, predicate.toString());
    }
}
