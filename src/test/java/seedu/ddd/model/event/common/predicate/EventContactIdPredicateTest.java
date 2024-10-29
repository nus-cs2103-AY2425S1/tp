package seedu.ddd.model.event.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.common.Id;
import seedu.ddd.testutil.event.EventBuilder;

public class EventContactIdPredicateTest {
    @Test
    public void equals() {
        Id firstId = new Id(1);
        Id secondId = new Id(2);

        EventIdPredicate firstPredicate = new EventIdPredicate(firstId);
        EventIdPredicate secondPredicate = new EventIdPredicate(secondId);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventIdPredicate firstPredicateCopy = new EventIdPredicate(firstId);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different id -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactContainsId_returnsTrue() {
        EventIdPredicate predicate = new EventIdPredicate(new Id(1));
        assertTrue(predicate.test(new EventBuilder().withEventId(1).build()));

        predicate = new EventIdPredicate(new Id(4));
        assertTrue(predicate.test(new EventBuilder().withEventId(4).build()));

        predicate = new EventIdPredicate(new Id(100));
        assertTrue(predicate.test(new EventBuilder().withEventId(100).build()));

        predicate = new EventIdPredicate(new Id(1000));
        assertTrue(predicate.test(new EventBuilder().withEventId(1000).build()));
    }

    @Test
    public void test_contactDoesNotContainsId_returnsFalse() {
        EventIdPredicate predicate = new EventIdPredicate(new Id(1));
        assertFalse(predicate.test(new EventBuilder().withEventId(2).build()));

        predicate = new EventIdPredicate(new Id(3));
        assertFalse(predicate.test(new EventBuilder().withEventId(2).build()));
    }

    @Test
    public void toStringMethod() {
        Id id = new Id(1);
        EventIdPredicate predicate = new EventIdPredicate(id);

        String expected = EventIdPredicate.class.getCanonicalName() + "{eventId=" + id + "}";
        assertEquals(expected, predicate.toString());
    }
}
