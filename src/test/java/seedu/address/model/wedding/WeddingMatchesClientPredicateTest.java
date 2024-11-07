package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class WeddingMatchesClientPredicateTest {

    @Test
    public void equals() {
        Person firstPerson = new PersonBuilder().withName("firstPerson").build();
        Person secondPerson = new PersonBuilder().withName("secondPerson").build();

        WeddingMatchesClientPredicate firstPredicate = new WeddingMatchesClientPredicate(firstPerson);
        WeddingMatchesClientPredicate secondPredicate = new WeddingMatchesClientPredicate(secondPerson);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        WeddingMatchesClientPredicate firstPredicateCopy = new WeddingMatchesClientPredicate(firstPerson);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different wedding -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_clientHasMatchingOwnWedding_returnsTrue() {
        Wedding wedding = new WeddingBuilder().withName("Test Wedding").build();
        Person testPerson = new PersonBuilder().withName("testPerson").withOwnWedding(wedding).build();
        WeddingMatchesClientPredicate predicate = new WeddingMatchesClientPredicate(testPerson);

        assertTrue(predicate.test(wedding));
    }

    @Test
    public void test_personNotOwnWedding_returnsFalse() {
        Wedding predicateWedding = new WeddingBuilder().withName("Test Wedding One").build();
        Wedding personWedding = new WeddingBuilder().withName("Test Wedding Two").build();
        Person person = new PersonBuilder().withOwnWedding(personWedding).build();
        WeddingMatchesClientPredicate predicate = new WeddingMatchesClientPredicate(person);


        assertFalse(predicate.test(predicateWedding));
    }

    @Test
    public void test_personHasNoWedding_returnsFalse() {
        Wedding wedding = new WeddingBuilder().withName("Test Wedding").build();
        Person person = new PersonBuilder().build(); // Person with no wedding
        WeddingMatchesClientPredicate predicate = new WeddingMatchesClientPredicate(person);


        assertFalse(predicate.test(wedding));
    }

    @Test
    public void getWedding_returnsCorrectWedding() {
        Person person = new PersonBuilder().build();
        WeddingMatchesClientPredicate predicate = new WeddingMatchesClientPredicate(person);

        assertEquals(person, predicate.getPerson());
    }
}