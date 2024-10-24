package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(
                Arrays.asList(firstPredicateKeyword));
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(
                Arrays.asList(secondPredicateKeyword));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(
                Arrays.asList(firstPredicateKeyword));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_RoleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withRoles("friends").build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("friends", "family"));
        assertTrue(predicate.test(new PersonBuilder().withRoles("friends").build()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("family").build()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("friends", "family").build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withRoles("family").build()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("family", "friends").build()));
        assertTrue(predicate.test(new PersonBuilder().withRoles("friends").build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("fRiEnDs"));
        assertTrue(predicate.test(new PersonBuilder().withRoles("friends").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList());
        assertFalse(predicate.test(new PersonBuilder().withRoles("friends").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withRoles("friends").build()));

        // Keywords match name
        // TODO keywords to match whole contact (phone, email and address), but not role
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Alice", "12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withRoles("bruh").build()));
    }

    @Test
    public void test_personHasNoTags_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList("friends"));
        assertFalse(predicate.test(new PersonBuilder().withRoles().build()));
    }

    @Test
    public void test_tagContainsSubstringOfKeyword_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withRoles("familiar").build()));
    }

    @Test
    public void personHasMixedTags_someMatching_returnsTrue() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList("family", "colleague"));
        assertTrue(predicate.test(new PersonBuilder().withRoles("family", "gym").build()));
    }

    @Test
    public void test_largeNumberOfKeywords_performanceCheck() {
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            keywords.add("keyword" + i);
        }
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withRoles("unrelated").build()));
    }

}
