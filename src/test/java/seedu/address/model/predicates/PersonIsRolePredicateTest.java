package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_SPONSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VENDOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VOLUNTEER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.role.Attendee;
import seedu.address.model.role.Role;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Vendor;
import seedu.address.model.role.Volunteer;
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
    public void test_noRoles_returnsFalse() {
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Collections.singletonList(new Sponsor()));

        Person person = new PersonBuilder().withName("Billy").build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_containsRoles_returnsTrue() {
        // One keyword
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Collections.singletonList(new Sponsor()));
        assertTrue(predicate.test(new PersonBuilder().withRoles(VALID_ROLE_SPONSOR).build()));

        // Multiple keywords
        predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Vendor()));
        assertTrue(predicate.test(new PersonBuilder().withRoles(VALID_ROLE_SPONSOR, VALID_ROLE_VENDOR).build()));

        // Only one matching keyword
        predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Volunteer()));
        assertTrue(predicate.test(new PersonBuilder().withRoles(VALID_ROLE_SPONSOR, VALID_ROLE_VENDOR).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRoles("Alice").build()));

        // Non-matching keyword
        predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor()));
        assertFalse(predicate.test(new PersonBuilder().withRoles(VALID_ROLE_VOLUNTEER).build()));
    }

    @Test
    public void getRolesAsString_oneRole() {
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor()));

        String expectedValue = new Sponsor().toString();

        assertEquals(predicate.getRolesAsString(), expectedValue);
    }

    @Test
    public void getRolesAsString_multipleRoles() {
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(
                Arrays.asList(new Sponsor(), new Vendor(), new Volunteer()));

        String expectedValue = new Sponsor().toString() + " "
                + new Vendor().toString() + " " + new Volunteer().toString();

        assertEquals(predicate.getRolesAsString(), expectedValue);
    }

    @Test
    public void toStringMethod() {
        List<Role> roles = List.of(new Attendee(), new Vendor());
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(roles);

        String expected = PersonIsRolePredicate.class.getCanonicalName() + "{roles=" + roles + "}";
        assertEquals(expected, predicate.toString());
    }

}
