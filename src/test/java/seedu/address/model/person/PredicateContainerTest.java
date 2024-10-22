package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;

public class PredicateContainerTest {
    @Test
    public void equals() {
        // same object -> true
        PredicateContainer predicateContainer1 = getPredicateContainer1();
        assertTrue(predicateContainer1.equals(predicateContainer1));

        // same values -> returns true
        PredicateContainer predicateContainer3 = getPredicateContainer1();
        assertTrue(predicateContainer1.equals(predicateContainer3));

        // different types -> returns false
        assertFalse(predicateContainer1.equals(1));

        // null -> returns false
        assertFalse(predicateContainer1.equals(null));

        // different PredicateContainer -> returns false
        PredicateContainer predicateContainer2 = getPredicateContainer2();
        assertFalse(predicateContainer1.equals(predicateContainer2));

        // same values (null Predicates) -> true
        PredicateContainer emptyPredicateContainer1 = new PredicateContainer();
        PredicateContainer emptyPredicateContainer2 = new PredicateContainer();
        assertTrue(emptyPredicateContainer1.equals(emptyPredicateContainer2));

        // different values (one null Predicates) -> false
        assertFalse(predicateContainer1.equals(emptyPredicateContainer1));
    }

    @Test
    public void test_getCombinedPredicates_returnTrue() {
        PredicateContainer predicateContainer = getPredicateContainer1();
        Predicate<Person> predicate = predicateContainer.getCombinedPredicates();
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_getCombinedPredicates_returnFalse() {
        PredicateContainer predicateContainer = getPredicateContainer2();
        Predicate<Person> predicate = predicateContainer.getCombinedPredicates();
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void test_toString() {
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "94351253"));
        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "HR"));
        RoleContainsKeywordsPredicate roleContainsKeywordsPredicate =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "SWE"));
        PredicateContainer predicateContainer = getPredicateContainer1();
        String actual = predicateContainer.toString();
        ToStringBuilder toStringBuilder = new ToStringBuilder(predicateContainer);
        toStringBuilder.add("names", nameContainsKeywordsPredicate + "\n")
                .add("phones", phoneContainsKeywordsPredicate + "\n")
                .add("emails", emailContainsKeywordsPredicate + "\n")
                .add("departments", departmentContainsKeywordsPredicate + "\n")
                .add("roles", roleContainsKeywordsPredicate);
        String expected = toStringBuilder.toString();
        assertEquals(expected, actual);
    }

    public PredicateContainer getPredicateContainer1() {
        NameContainsKeywordsPredicate namePredicate1 =
                new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        PhoneContainsKeywordsPredicate phonePredicate1 =
                new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "94351253"));
        EmailContainsKeywordsPredicate emailPredicate1 =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentPredicate1 =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "HR"));
        RoleContainsKeywordsPredicate rolePredicate1 =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "SWE"));
        PredicateContainer predicateContainer1 = new PredicateContainer()
                .addNameContainsKeywordsPredicate(namePredicate1)
                .addPhoneContainsKeywordsPredicate(phonePredicate1)
                .addEmailContainsKeywordsPredicate(emailPredicate1)
                .addDepartmentContainsKeywordsPredicate(departmentPredicate1)
                .addRoleContainsKeywordsPredicate(rolePredicate1);
        return predicateContainer1;
    }

    public PredicateContainer getPredicateContainer2() {
        NameContainsKeywordsPredicate namePredicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("bob"));
        PhoneContainsKeywordsPredicate phonePredicate2 =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("94351253"));
        EmailContainsKeywordsPredicate emailPredicate2 =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        DepartmentContainsKeywordsPredicate departmentPredicate2 =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("IT"));
        RoleContainsKeywordsPredicate rolePredicate2 =
                new RoleContainsKeywordsPredicate(Collections.singletonList("SWE"));
        PredicateContainer predicateContainer2 = new PredicateContainer()
                .addNameContainsKeywordsPredicate(namePredicate2)
                .addPhoneContainsKeywordsPredicate(phonePredicate2)
                .addEmailContainsKeywordsPredicate(emailPredicate2)
                .addDepartmentContainsKeywordsPredicate(departmentPredicate2)
                .addRoleContainsKeywordsPredicate(rolePredicate2);
        return predicateContainer2;
    }

    @Test
    public void test_nullPredicates() {
        PredicateContainer emptyPredicateContainer = new PredicateContainer();
        Predicate<Person> emptyPredicate = emptyPredicateContainer.getCombinedPredicates();
        assertEquals(emptyPredicate, PREDICATE_SHOW_ALL_PERSONS);
    }

}
