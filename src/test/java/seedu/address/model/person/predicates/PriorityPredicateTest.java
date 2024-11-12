package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PriorityPredicateTest {

    @Test
    public void equals() {
        List<String> firstPriorityList = Collections.singletonList("LOW");
        List<String> secondPriorityList = Arrays.asList("LOW", "HIGH");

        PriorityPredicate firstPredicate = new PriorityPredicate(firstPriorityList);
        PriorityPredicate secondPredicate = new PriorityPredicate(secondPriorityList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PriorityPredicate firstPredicateCopy = new PriorityPredicate(firstPriorityList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different list of priorities -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_priorityMatches_returnsTrue() {
        // One keyword
        PriorityPredicate predicate = new PriorityPredicate(Collections.singletonList("LOW"));
        assertTrue(predicate.test(new PersonBuilder().withPriority("LOW").build()));

        // Multiple keywords
        predicate = new PriorityPredicate(Arrays.asList("LOW", "HIGH"));
        assertTrue(predicate.test(new PersonBuilder().withPriority("HIGH").build()));

        // Mixed-case keywords
        predicate = new PriorityPredicate(Arrays.asList("hIgH", "LOw"));
        assertTrue(predicate.test(new PersonBuilder().withPriority("HIGH").build()));
    }

    @Test
    public void test_priorityDoesNotMatch_returnsFalse() {
        // Zero keywords
        PriorityPredicate predicate = new PriorityPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPriority("LOW").build()));

        // Non-matching keyword
        predicate = new PriorityPredicate(Arrays.asList("MAXIMUM"));
        assertFalse(predicate.test(new PersonBuilder().withPriority("HIGH").build()));

    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("HIGH", "MEDIUM");
        PriorityPredicate predicate = new PriorityPredicate(keywords);

        String expected = PriorityPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
