package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PhoneSearchCriteriaTest {

    @Test
    public void test_matchesPhone() {
        PhoneSearchCriteria criteria = new PhoneSearchCriteria(List.of("12345678"));

        Person alice = new PersonBuilder().withName("Alice").withPhone("12345678").build();
        Person bob = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        assertTrue(criteria.test(alice));
        assertFalse(criteria.test(bob));
    }

    @Test
    public void test_noMatchForNonMatchingPhone() {
        PhoneSearchCriteria criteria = new PhoneSearchCriteria(List.of("11112222"));
        Person alice = new PersonBuilder().withName("Alice").withPhone("12345678").build();
        assertFalse(criteria.test(alice));
    }
}

