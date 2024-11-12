package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;

public class TransactionDatePredicateTest {
    @Test
    public void equals() {
        YearMonth startMonth1 = YearMonth.parse("2020-11");
        YearMonth endMonth1 = YearMonth.parse("2022-01");
        YearMonth startMonth2 = YearMonth.parse("2020-12");
        YearMonth endMonth2 = YearMonth.parse("2022-02");

        TransactionDatePredicate firstPredicate =
                new TransactionDatePredicate(startMonth1, endMonth1);
        TransactionDatePredicate secondPredicate =
                new TransactionDatePredicate(startMonth2, endMonth2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionDatePredicate firstPredicateCopy =
                new TransactionDatePredicate(startMonth1, endMonth1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different dates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateWithinRange_returnsTrue() {
        // within range
        TransactionDatePredicate predicate =
                new TransactionDatePredicate(YearMonth.parse("2024-11"), YearMonth.parse("2024-12"));
        assertTrue(predicate.test(new Transaction(
                "invest", 1000, "ABC Company",
                LocalDate.parse("2024-12-30", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // edge date
        predicate = new TransactionDatePredicate(YearMonth.parse("2024-11"), YearMonth.parse("2024-12"));
        assertTrue(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-11-01", DateTimeUtil.DEFAULT_DATE_PARSER))));
        assertTrue(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-12-31", DateTimeUtil.DEFAULT_DATE_PARSER))));
    }

    @Test
    public void test_dateNotWithinRange_returnsFalse() {
        // not within range
        TransactionDatePredicate predicate =
                new TransactionDatePredicate(YearMonth.parse("2024-11"), YearMonth.parse("2024-12"));
        assertFalse(predicate.test(new Transaction(
                "invest", 1000, "ABC Company",
                LocalDate.parse("2024-10-29", DateTimeUtil.DEFAULT_DATE_PARSER))));

        // edge date
        predicate = new TransactionDatePredicate(YearMonth.parse("2024-11"), YearMonth.parse("2024-12"));
        assertFalse(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2024-10-31", DateTimeUtil.DEFAULT_DATE_PARSER))));
        assertFalse(predicate.test(new Transaction(
                "raw materials", 1000, "ABC Company",
                LocalDate.parse("2025-01-01", DateTimeUtil.DEFAULT_DATE_PARSER))));
    }
}

