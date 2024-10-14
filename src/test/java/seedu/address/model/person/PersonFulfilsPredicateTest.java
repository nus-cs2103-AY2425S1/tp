package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonFulfilsPredicateTest {

    @Test
    public void equals() {
        PersonFulfilsPredicate firstPredicate = new PersonFulfilsPredicate("buyer");
        PersonFulfilsPredicate secondPredicate = new PersonFulfilsPredicate("");
        PersonFulfilsPredicate thirdPredicate = new PersonFulfilsPredicate("filler");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonFulfilsPredicate firstPredicateCopy = new PersonFulfilsPredicate("buyer");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_personFulfilsPredicate_returnsTrue() {
        // valid person-type
        PersonFulfilsPredicate predicate = new PersonFulfilsPredicate("buyer");
        assertTrue(predicate.test(new PersonBuilder().build()));

        // no person-type
        predicate = new PersonFulfilsPredicate("");
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_personDoesNotFulfilPredicate_returnsFalse() {
        // non-matching person-type
        PersonFulfilsPredicate predicate = new PersonFulfilsPredicate("buyer");
        assertFalse(predicate.test(new PersonBuilder().withPersonType("seller").build()));
    }

    @Test
    public void toStringMethod() {
        PersonFulfilsPredicate predicate = new PersonFulfilsPredicate("buyer");

        String expected = PersonFulfilsPredicate.class.getCanonicalName() + "{personType=buyer}";
        assertEquals(expected, predicate.toString());
    }
}
