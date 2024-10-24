package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.*;
import seedu.address.testutil.PersonBuilder;

public class PersonIsRolePredicateTest {

    @Test
    public void equals() {
        List<Role> firstPredicateRoleList = Collections.singletonList(new Sponsor());
        List<Role> secondPredicateRoleList = Arrays.asList(new Sponsor(), new Vendor());

        PersonIsRolePredicate firstPredicate = new PersonIsRolePredicate(firstPredicateRoleList);
        PersonIsRolePredicate secondPredicate = new PersonIsRolePredicate(secondPredicateRoleList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonIsRolePredicate firstPredicateCopy = new PersonIsRolePredicate(firstPredicateRoleList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsRoles_returnsTrue() {
        // One keyword
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Collections.singletonList(new Sponsor()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("sponsor").build()));

        // Multiple keywords
        predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Vendor()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("sponsor", "vendor").build()));

        // Only one matching keyword
        predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Volunteer()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("sponsor", "vendor").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRoles("Alice").build()));

        // Non-matching keyword
        predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor()));
        assertFalse(predicate.test(new PersonBuilder().withRoles("volunteer").build()));
    }

    @Test
    public void toStringMethod() {
        List<Role> roles = List.of(new Attendee(), new Vendor());
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(roles);

        String expected = PersonIsRolePredicate.class.getCanonicalName() + "{roles=" + roles + "}";
        assertEquals(expected, predicate.toString());
    }
}
