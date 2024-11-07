package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;


public class SelectPredicateTest {

    @Test
    public void equals() {
        SelectPredicate firstPredicate = new SelectPredicate(getTypicalPersons());
        SelectPredicate secondPredicate = new SelectPredicate(getTypicalPersons().subList(0, 3));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        assertTrue(firstPredicate.equals(new SelectPredicate(getTypicalPersons())));

        // different types -> returns false
        assertFalse(firstPredicate.equals("DUMMY"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different conditions, in this case different persons -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personListContainsPerson_returnsTrue() {
        Person alice = new PersonBuilder().withName("Alice").withPhone("10000000").build();
        Person bob = new PersonBuilder().withName("Bob").withPhone("10000001").build();

        // the predicate has multiple selected persons and there is one match
        List<Person> persons = new ArrayList<>();
        persons.add(alice);
        persons.add(bob);
        SelectPredicate predicateWithMultiplePersons = new SelectPredicate(persons);
        assertTrue(predicateWithMultiplePersons.test(alice));

        // the predicate has only one person and there is one exact match
        persons = new ArrayList<>();
        persons.add(bob);
        SelectPredicate predicateWithOnePerson = new SelectPredicate(persons);
        assertTrue(predicateWithOnePerson.test(bob));
    }

    @Test
    public void test_personListContainsPerson_returnsFalse() {
        Person alice = new PersonBuilder().withName("Alice").withPhone("10000000").build();
        Person bob = new PersonBuilder().withName("Bob").withPhone("10000001").build();
        Person charlie = new PersonBuilder().withName("Charlie").withPhone("10000002").build();

        // the predicate has multiple selected persons and there is no match
        List<Person> persons = new ArrayList<>();
        persons.add(alice);
        persons.add(bob);
        SelectPredicate predicateWithMultiplePersons = new SelectPredicate(persons);
        assertFalse(predicateWithMultiplePersons.test(charlie));

        // the predicate has only one person and there is no match
        persons = new ArrayList<>();
        persons.add(bob);
        SelectPredicate predicateWithOnePerson = new SelectPredicate(persons);
        assertFalse(predicateWithOnePerson.test(charlie));
    }

    @Test
    public void toStringMethod() {
        List<Person> persons = new ArrayList<>();
        persons.add(new PersonBuilder().withName("Alice").withPhone("10000000").build());
        SelectPredicate predicate = new SelectPredicate(persons);
        String expected = SelectPredicate.class.getCanonicalName() + "{Persons=" + persons + "}";
        assertEquals(expected, predicate.toString());
    }
}
