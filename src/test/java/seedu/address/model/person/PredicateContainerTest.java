package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;

public class PredicateContainerTest {
    @Test
    public void equals() {
        NameContainsKeywordsPredicate namePredicate1 =
                new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        PhoneContainsKeywordsPredicate phonePredicate1 =
                new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "87654321"));
        EmailContainsKeywordsPredicate emailPredicate1 =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@gmail.com", "bob@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentPredicate1 =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "HR"));
        RoleContainsKeywordsPredicate rolePredicate1 =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "President"));

        NameContainsKeywordsPredicate namePredicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("alice"));
        PhoneContainsKeywordsPredicate phonePredicate2 =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("12345678"));
        EmailContainsKeywordsPredicate emailPredicate2 =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentPredicate2 =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("IT"));
        RoleContainsKeywordsPredicate rolePredicate2 =
                new RoleContainsKeywordsPredicate(Collections.singletonList("Manager"));

        PredicateContainer predicateContainer1 = new PredicateContainer()
                .addNameContainsKeywordsPredicate(namePredicate1)
                .addPhoneContainsKeywordsPredicate(phonePredicate1)
                .addEmailContainsKeywordsPredicate(emailPredicate1)
                .addDepartmentContainsKeywordsPredicate(departmentPredicate1)
                .addRoleContainsKeywordsPredicate(rolePredicate1);
        PredicateContainer predicateContainer2 = new PredicateContainer()
                .addNameContainsKeywordsPredicate(namePredicate2)
                .addPhoneContainsKeywordsPredicate(phonePredicate2)
                .addEmailContainsKeywordsPredicate(emailPredicate2)
                .addDepartmentContainsKeywordsPredicate(departmentPredicate2)
                .addRoleContainsKeywordsPredicate(rolePredicate2);

        // same object -> true
        assertTrue(predicateContainer1.equals(predicateContainer1));

        // same values -> returns true
        PredicateContainer predicateContainer3 = new PredicateContainer()
                .addNameContainsKeywordsPredicate(namePredicate1)
                .addPhoneContainsKeywordsPredicate(phonePredicate1)
                .addEmailContainsKeywordsPredicate(emailPredicate1)
                .addDepartmentContainsKeywordsPredicate(departmentPredicate1)
                .addRoleContainsKeywordsPredicate(rolePredicate1);
        assertTrue(predicateContainer1.equals(predicateContainer3));

        // different types -> returns false
        assertFalse(predicateContainer1.equals(1));

        // null -> returns false
        assertFalse(predicateContainer1.equals(null));

        // different PredicateContainer -> returns false
        assertFalse(predicateContainer1.equals(predicateContainer2));
    }

    @Test
    public void test_getCombinedPredicates_returnTrue() {
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("94351253", "87654321"));
        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "SWE"));
        RoleContainsKeywordsPredicate roleContainsKeywordsPredicate =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "SWE"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate)
                .addPhoneContainsKeywordsPredicate(phoneContainsKeywordsPredicate)
                .addEmailContainsKeywordsPredicate(emailContainsKeywordsPredicate)
                .addDepartmentContainsKeywordsPredicate(departmentContainsKeywordsPredicate)
                .addRoleContainsKeywordsPredicate(roleContainsKeywordsPredicate);
        Predicate<Person> predicate = predicateContainer.getCombinedPredicates();
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_getCombinedPredicates_returnFalse() {
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        // changed phone number
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "87654321"));
        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "BIZ"));
        RoleContainsKeywordsPredicate roleContainsKeywordsPredicate =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "SWE"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate)
                .addPhoneContainsKeywordsPredicate(phoneContainsKeywordsPredicate)
                .addEmailContainsKeywordsPredicate(emailContainsKeywordsPredicate)
                .addDepartmentContainsKeywordsPredicate(departmentContainsKeywordsPredicate)
                .addRoleContainsKeywordsPredicate(roleContainsKeywordsPredicate);
        Predicate<Person> predicate = predicateContainer.getCombinedPredicates();
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void test_toString() {
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("94351253", "87654321"));
        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com", "bob@gmail.com"));
        DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "SWE"));
        RoleContainsKeywordsPredicate roleContainsKeywordsPredicate =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "SWE"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate)
                .addPhoneContainsKeywordsPredicate(phoneContainsKeywordsPredicate)
                .addEmailContainsKeywordsPredicate(emailContainsKeywordsPredicate)
                .addDepartmentContainsKeywordsPredicate(departmentContainsKeywordsPredicate)
                .addRoleContainsKeywordsPredicate(roleContainsKeywordsPredicate);
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


}
