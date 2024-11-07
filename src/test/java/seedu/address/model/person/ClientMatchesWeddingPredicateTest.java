package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class ClientMatchesWeddingPredicateTest {

    @Test
    public void equals() {
        Wedding firstWedding = new WeddingBuilder().withName("Alice Bob Wedding").build();
        Wedding secondWedding = new WeddingBuilder().withName("Charlie Dave Wedding").build();

        ClientMatchesWeddingPredicate firstPredicate = new ClientMatchesWeddingPredicate(firstWedding);
        ClientMatchesWeddingPredicate secondPredicate = new ClientMatchesWeddingPredicate(secondWedding);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClientMatchesWeddingPredicate firstPredicateCopy = new ClientMatchesWeddingPredicate(firstWedding);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different wedding -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasMatchingWedding_returnsTrue() {
        Wedding wedding = new WeddingBuilder().withName("Test Wedding").build();
        ClientMatchesWeddingPredicate predicate = new ClientMatchesWeddingPredicate(wedding);
        Person person = new PersonBuilder().withOwnWedding(wedding).build();

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personHasDifferentWedding_returnsFalse() {
        Wedding predicateWedding = new WeddingBuilder().withName("Test Wedding One").build();
        Wedding personWedding = new WeddingBuilder().withName("Test Wedding Two").build();
        ClientMatchesWeddingPredicate predicate = new ClientMatchesWeddingPredicate(predicateWedding);
        Person person = new PersonBuilder().withOwnWedding(personWedding).build();

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_personHasNoWedding_returnsFalse() {
        Wedding wedding = new WeddingBuilder().withName("Test Wedding").build();
        ClientMatchesWeddingPredicate predicate = new ClientMatchesWeddingPredicate(wedding);
        Person person = new PersonBuilder().build(); // Person with no wedding

        assertFalse(predicate.test(person));
    }

    @Test
    public void getWedding_returnsCorrectWedding() {
        Wedding wedding = new WeddingBuilder().withName("Test Wedding").build();
        ClientMatchesWeddingPredicate predicate = new ClientMatchesWeddingPredicate(wedding);

        assertEquals(wedding, predicate.getWedding());
    }
}
