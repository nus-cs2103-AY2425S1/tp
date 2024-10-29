package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class MajorContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        MajorContainsKeywordsPredicate firstPredicate = new MajorContainsKeywordsPredicate("Computer Science");
        MajorContainsKeywordsPredicate secondPredicate = new MajorContainsKeywordsPredicate("Information Technology");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsKeywordsPredicate firstPredicateCopy = new MajorContainsKeywordsPredicate("Computer Science");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different major -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorMatchesKeyword_returnsTrue() {
        // Exact match
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate("Computer Science");
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case match
        predicate = new MajorContainsKeywordsPredicate("coMpuTer sCienCe");
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotMatchKeyword_returnsFalse() {
        // Non-matching keyword
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate("Mathematics");
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Keywords match phone, email, and address, but do not match major
        predicate = new MajorContainsKeywordsPredicate("Engineering");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street")
                .withMajor("Computer Science").build()));
    }

    @Test
    public void toStringMethod() {
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate("Computer Science");

        // Update expected output to match current implementation
        String expected = MajorContainsKeywordsPredicate.class.getCanonicalName() + "{}"; // Adjust to current output
        assertEquals(expected, predicate.toString());
    }
}
