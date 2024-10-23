package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;



public class PriorityMatchesPredicateTest {
    private final Priority highPriority = new Priority("HIGH");
    private final Priority mediumPriority = new Priority("MEDIUM");
    private final Priority lowPriority = new Priority("LOW");

    @Test
    public void equals() {
        PriorityMatchesPredicate firstPredicate = new PriorityMatchesPredicate(highPriority);
        PriorityMatchesPredicate secondPredicate = new PriorityMatchesPredicate(highPriority);
        PriorityMatchesPredicate thirdPredicate = new PriorityMatchesPredicate(lowPriority);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different priority -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_priorityMatchesPredicate_returnsTrue() {
        // same Priority
        PriorityMatchesPredicate priorityPredicate = new PriorityMatchesPredicate(lowPriority);
        assertTrue(priorityPredicate.test(new PersonBuilder().withPriority("LOW").build()));
    }

    @Test
    public void test_priorityDoesNotMatchPredicate_returnsFalse() {
        // different Priority
        PriorityMatchesPredicate priorityPredicate = new PriorityMatchesPredicate(highPriority);
        assertFalse(priorityPredicate.test(new PersonBuilder().withPriority("LOW").build()));
    }

    @Test
    public void toStringMethod() {
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(mediumPriority);
        String expected = PriorityMatchesPredicate.class.getCanonicalName() + "{priority=" + mediumPriority + "}";
        assertEquals(expected, predicate.toString());
    }
}
