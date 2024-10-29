package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EmailSearchCriteriaTest {

    @Test
    public void test_matchesEmail() {
        EmailSearchCriteria criteria = new EmailSearchCriteria(List.of("alice@example.com"));

        Person alice = new PersonBuilder().withName("Alice").withEmail("alice@example.com").build();
        Person bob = new PersonBuilder().withName("Bob").withEmail("bob@example.com").build();;

        assertTrue(criteria.test(alice));
        assertFalse(criteria.test(bob));
    }

    @Test
    public void test_matchesEmail_caseInsensitive() {
        EmailSearchCriteria criteria = new EmailSearchCriteria(List.of("ALICE@EXAMPLE.COM"));

        Person alice = new PersonBuilder().withName("Alice").withEmail("alice@example.com").build();
        assertTrue(criteria.test(alice));
    }

    @Test
    public void test_noMatchForNonMatchingEmail() {
        EmailSearchCriteria criteria = new EmailSearchCriteria(List.of("charlie@example.com"));
        Person alice = new PersonBuilder().withName("Alice").withEmail("alice@example.com").build();
        assertFalse(criteria.test(alice));
    }
}

