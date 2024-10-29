package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RoleSearchCriteriaTest {

    @Test
    public void test_matchesRole() {
        RoleSearchCriteria criteria = new RoleSearchCriteria(List.of("patient"));

        Person alice = new PersonBuilder().withName("Alice").withRole("patient").build();
        Person bob = new PersonBuilder().withRole("caregiver").build();

        assertTrue(criteria.test(alice));
        assertFalse(criteria.test(bob));
    }

    @Test
    public void test_noMatchForNonMatchingRole() {
        RoleSearchCriteria criteria = new RoleSearchCriteria(List.of("caregiver"));
        Person alice = new PersonBuilder().withName("Alice").withRole("patient").build();
        assertFalse(criteria.test(alice));
    }
}

