package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class EventIdsContainsIdsPredicateTest {
    @Test
    public void equals() {
        List<Integer> firstPredicateIdList = Collections.singletonList(1);
        List<Integer> secondPredicateIdList = Arrays.asList(1, 2);

        EventIdsContainsIdsPredicate firstPredicate = new EventIdsContainsIdsPredicate(firstPredicateIdList);
        EventIdsContainsIdsPredicate secondPredicate = new EventIdsContainsIdsPredicate(secondPredicateIdList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventIdsContainsIdsPredicate firstPredicateCopy = new EventIdsContainsIdsPredicate(firstPredicateIdList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventIdsContainIds_returnsTrue() {
        // One ID
        EventIdsContainsIdsPredicate predicate = new EventIdsContainsIdsPredicate(Collections.singletonList(1));
        assertTrue(predicate.test(new PersonBuilder().withEventIds(1).build()));

        // Multiple IDs
        predicate = new EventIdsContainsIdsPredicate(Arrays.asList(1, 2, 3));
        assertTrue(predicate.test(new PersonBuilder().withEventIds(1, 2).build()));

        // Only one matching ID
        predicate = new EventIdsContainsIdsPredicate(Arrays.asList(1, 2, 3));
        assertTrue(predicate.test(new PersonBuilder().withEventIds(1).build()));
    }

    @Test
    public void test_eventIdsDoNotContainIds_returnsFalse() {
        // Zero IDs
        EventIdsContainsIdsPredicate predicate = new EventIdsContainsIdsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEventIds(1).build()));

        // Non-matching ID
        predicate = new EventIdsContainsIdsPredicate(Arrays.asList(2, 3, 4));
        assertFalse(predicate.test(new PersonBuilder().withEventIds(1, 5).build()));
    }

    @Test
    public void toStringMethod() {
        List<Integer> ids = List.of(1, 2);
        EventIdsContainsIdsPredicate predicate = new EventIdsContainsIdsPredicate(ids);

        String expected = EventIdsContainsIdsPredicate.class.getCanonicalName() + "{ids=" + ids + "}";
        assertEquals(expected, predicate.toString());
    }
}
