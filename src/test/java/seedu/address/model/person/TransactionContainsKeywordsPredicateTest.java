package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;

public class TransactionContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionContainsKeywordsPredicate firstPredicate =
                new TransactionContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionContainsKeywordsPredicate secondPredicate =
                new TransactionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionContainsKeywordsPredicate firstPredicateCopy =
                new TransactionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different Transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("invest"));
        assertTrue(predicate.test(new Transaction(
                "invest", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // Multiple keywords
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("materials", "raw"));
        assertTrue(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // Only one matching keyword
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("materials", "raw"));
        assertTrue(predicate.test(new Transaction(
                "new materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // Mixed-case keywords
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("mAtErIalS", "RaW"));
        assertTrue(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // Non-matching keyword
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("invest"));
        assertFalse(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // Keywords match amount, other party and date, but does not match description
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("1000", "ABC", "Company", "2024-11-11"));
        assertFalse(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-11", DateTimeUtil.DEFAULT_DATE_PARSER))));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionContainsKeywordsPredicate predicate = new TransactionContainsKeywordsPredicate(keywords);

        String expected = TransactionContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

