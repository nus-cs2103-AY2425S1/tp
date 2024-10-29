package seedu.address.logic.parser.criteria;

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
}

