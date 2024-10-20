package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DepartmentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DepartmentContainsKeywordsPredicate firstPredicate =
                new DepartmentContainsKeywordsPredicate(firstPredicateKeywordList);
        DepartmentContainsKeywordsPredicate secondPredicate =
                new DepartmentContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DepartmentContainsKeywordsPredicate firstPredicateCopy =
                new DepartmentContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_departmentContainsKeywords_returnsTrue() {
        // One keyword
        DepartmentContainsKeywordsPredicate predicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("IT"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("IT").withIsEmployee(true).build()));

        // Multiple keywords
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "BIZ"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("IT").withIsEmployee(true).build()));

        // Only one matching keyword
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("BIZ", "Product"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("Product Development")
                .withIsEmployee(true).build()));

        // Mixed-case keywords
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("iT", "bIz"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("IT").withIsEmployee(true).build()));
    }

    @Test
    public void test_departmentDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DepartmentContainsKeywordsPredicate predicate =
                new DepartmentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withDepartment("IT").withIsEmployee(true).build()));

        // Non-matching keyword
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("HR"));
        assertFalse(predicate.test(new PersonBuilder().withDepartment("IT").withIsEmployee(true).build()));

        // Keywords match all fields except department
        predicate = new DepartmentContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street", "Manager"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withDepartment("IT").withRole("Manager")
                .withIsEmployee(true).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        DepartmentContainsKeywordsPredicate predicate = new DepartmentContainsKeywordsPredicate(keywords);

        String expected = DepartmentContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

