package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.PersonBuilder.DEFAULT_NRIC;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class NricSearchCriteriaTest {

    @Test
    public void test_matchesNric() {
        NricSearchCriteria criteria = new NricSearchCriteria(List.of("S1234567D"));

        Person alice = new PersonBuilder().withName("Alice").withNric("S1234567D").build();
        Person bob = new PersonBuilder().withName("Bob").withNric(DEFAULT_NRIC).build();

        assertTrue(criteria.test(alice));
        assertFalse(criteria.test(bob));
    }
    @Test
    public void test_toString() {
        NricSearchCriteria criteria = new NricSearchCriteria(List.of("S1234567D", "S6482983A"));
        assertEquals("NricCriteria{ages=[S6482983A, S1234567D]}", criteria.toString());
    }

    @Test
    public void test_equals_sameObject() {
        NricSearchCriteria criteria = new NricSearchCriteria(List.of("S1234567D"));
        assertTrue(criteria.equals(criteria)); // Same object
    }

    @Test
    public void test_equals_identicalObject() {
        NricSearchCriteria criteria1 = new NricSearchCriteria(List.of("S1234567D"));
        NricSearchCriteria criteria2 = new NricSearchCriteria(List.of("S1234567D"));
        assertTrue(criteria1.equals(criteria2)); // Identical fields
    }

    @Test
    public void test_equals_differentValues() {
        NricSearchCriteria criteria1 = new NricSearchCriteria(List.of("S1234567D"));
        NricSearchCriteria criteria2 = new NricSearchCriteria(List.of("S6482983A"));
        assertFalse(criteria1.equals(criteria2)); // Different NRIC
    }

    @Test
    public void test_equals_differentType() {
        NricSearchCriteria criteria = new NricSearchCriteria(List.of("S1234567D"));
        String other = "Not a criteria";
        assertFalse(criteria.equals(other)); // Different type
    }
}

