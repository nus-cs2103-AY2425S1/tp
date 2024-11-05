package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_WEDDING;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class PersonHasWeddingPredicateTest {
    @Test
    public void equals() {
        Person firstPredicatePerson = ALICE;
        Person secondPredicatePerson = BOB;

        PersonHasWeddingPredicate firstPredicate = new PersonHasWeddingPredicate(firstPredicatePerson);
        PersonHasWeddingPredicate secondPredicate = new PersonHasWeddingPredicate(secondPredicatePerson);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same persons -> returns true
        PersonHasWeddingPredicate firstPredicateCopy = new PersonHasWeddingPredicate(firstPredicatePerson);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

    @Test
    public void test_nameMatchesWedding_returnsTrue() {
        PersonHasWeddingPredicate predicate = new PersonHasWeddingPredicate(ALICE);
        // under wedding job
        BOB.addWeddingJob(ALICE_WEDDING);
        predicate = new PersonHasWeddingPredicate(BOB);
        assertTrue(predicate.test(ALICE_WEDDING));
    }

    @Test
    public void test_nameDoesNotMatchWedding_returnFalse() {
        // Person does not have the wedding
        PersonHasWeddingPredicate predicate = new PersonHasWeddingPredicate(CARL);
        assertFalse(predicate.test(ALICE_WEDDING));
    }

    @Test
    public void toStringMethod() {
        Person person = ALICE;
        PersonHasWeddingPredicate predicate = new PersonHasWeddingPredicate(person);

        String expected = PersonHasWeddingPredicate.class.getCanonicalName() + "{person=" + person + "}";
        assertEquals(expected, predicate.toString());
    }

}
