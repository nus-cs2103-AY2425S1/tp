package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void test_toString() {
        RoleSearchCriteria criteria = new RoleSearchCriteria(List.of("patient", "caregiver"));
        assertEquals("RoleSearchCriteria{roles=[PATIENT, CAREGIVER]}", criteria.toString());
    }

    @Test
    public void test_equals_sameObject() {
        RoleSearchCriteria criteria = new RoleSearchCriteria(List.of("patient"));
        assertTrue(criteria.equals(criteria)); // Same object
    }

    @Test
    public void test_equals_identicalObject() {
        RoleSearchCriteria criteria1 = new RoleSearchCriteria(List.of("patient"));
        RoleSearchCriteria criteria2 = new RoleSearchCriteria(List.of("patient"));
        assertTrue(criteria1.equals(criteria2)); // Identical roles
    }

    @Test
    public void test_equals_differentRoles() {
        RoleSearchCriteria criteria1 = new RoleSearchCriteria(List.of("patient"));
        RoleSearchCriteria criteria2 = new RoleSearchCriteria(List.of("caregiver"));
        assertFalse(criteria1.equals(criteria2)); // Different roles
    }

    @Test
    public void test_equals_differentType() {
        RoleSearchCriteria criteria = new RoleSearchCriteria(List.of("patient"));
        String other = "Not a criteria";
        assertFalse(criteria.equals(other)); // Different type
    }
}

