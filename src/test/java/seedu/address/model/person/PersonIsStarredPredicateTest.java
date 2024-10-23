package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PersonIsStarredPredicateTest {
    @Test
    public void equals() {
        PersonIsStarredPredicate firstPredicate = new PersonIsStarredPredicate();
        PersonIsStarredPredicate secondPredicate = new PersonIsStarredPredicate();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void toStringMethod() {
        PersonIsStarredPredicate predicate = new PersonIsStarredPredicate();

        String expected = PersonIsStarredPredicate.class.getCanonicalName() + "{}";
        assertEquals(expected, predicate.toString());
    }
}
