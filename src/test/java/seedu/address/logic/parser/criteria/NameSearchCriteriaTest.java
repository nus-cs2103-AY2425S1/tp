package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class NameSearchCriteriaTest {

    @Test
    public void test_matchesName() {
        NameSearchCriteria criteria = new NameSearchCriteria(List.of("Alice"));

        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        assertTrue(criteria.test(alice));
        assertFalse(criteria.test(bob));
    }

    @Test
    public void test_matchesName_caseInsensitive() {
        NameSearchCriteria criteria = new NameSearchCriteria(List.of("alice"));

        Person alice = new PersonBuilder().withName("Alice").build();
        assertTrue(criteria.test(alice));
    }

    @Test
    public void test_noMatchForNonMatchingName() {
        NameSearchCriteria criteria = new NameSearchCriteria(List.of("Charlie"));
        Person alice = new PersonBuilder().withName("Alice").build();
        assertFalse(criteria.test(alice));
    }
}

