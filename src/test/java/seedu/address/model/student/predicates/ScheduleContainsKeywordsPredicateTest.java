package seedu.address.model.student.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Days;
import seedu.address.testutil.StudentBuilder;

public class ScheduleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Collection<Days> firstPredicateKeywordList = Collections.singletonList(Days.MONDAY);
        Collection<Days> secondPredicateKeywordList = Arrays.asList(Days.MONDAY, Days.TUESDAY);

        ScheduleContainsKeywordsPredicate
                firstPredicate = new ScheduleContainsKeywordsPredicate(firstPredicateKeywordList);
        ScheduleContainsKeywordsPredicate
                secondPredicate = new ScheduleContainsKeywordsPredicate(secondPredicateKeywordList);

        // EP: same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // EP: same values -> returns true
        ScheduleContainsKeywordsPredicate
                firstPredicateCopy = new ScheduleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // EP: different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // EP: null -> returns false
        assertFalse(firstPredicate.equals(null));

        // EP: different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_scheduleContainsKeywords_returnsTrue() {
        // EP: One keyword
        Collection<Days> firstPredicateKeywordList = Collections.singletonList(Days.MONDAY);

        ScheduleContainsKeywordsPredicate predicate = new ScheduleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(predicate.test(new StudentBuilder().withSchedule("Monday-1000-2000").build()));

        // EP: Multiple keywords
        Collection<Days> secondPredicateKeywordList = Arrays.asList(Days.MONDAY, Days.TUESDAY);
        predicate = new ScheduleContainsKeywordsPredicate(secondPredicateKeywordList);
        assertTrue(predicate.test(new StudentBuilder().withSchedule("Monday-1000-2000").build()));
    }

    @Test
    public void test_scheduleDoesNotContainKeywords_returnsFalse() {
        // EP: Zero keywords
        ScheduleContainsKeywordsPredicate predicate = new ScheduleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().build()));

        // EP: Non-matching keyword
        predicate = new ScheduleContainsKeywordsPredicate(Collections.singletonList(Days.MONDAY));
        assertFalse(predicate.test(new StudentBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        Collection<Days> keywords = Collections.singletonList(Days.MONDAY);
        ScheduleContainsKeywordsPredicate predicate = new ScheduleContainsKeywordsPredicate(keywords);

        String expected = ScheduleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
