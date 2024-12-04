package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CompanyBuilder;
import seedu.address.testutil.StudentBuilder;

public class CategoryContainsKeywordPredicateTest {
    private static final CategoryContainsKeywordPredicate STUDENT_PREDICATE =
            new CategoryContainsKeywordPredicate(VALID_CATEGORY_STUDENT);
    private static final CategoryContainsKeywordPredicate COMPANY_PREDICATE =
            new CategoryContainsKeywordPredicate(VALID_CATEGORY_COMPANY);
    @Test
    public void equals() {
        CategoryContainsKeywordPredicate firstPredicate = new CategoryContainsKeywordPredicate(VALID_CATEGORY_STUDENT);
        CategoryContainsKeywordPredicate secondPredicate = new CategoryContainsKeywordPredicate(VALID_CATEGORY_COMPANY);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CategoryContainsKeywordPredicate predicateCopy = new CategoryContainsKeywordPredicate(VALID_CATEGORY_STUDENT);
        assertTrue(firstPredicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categoryMatchesKeyword_returnsTrue() {
        assertTrue(STUDENT_PREDICATE.test(new StudentBuilder().build()));
        assertTrue(COMPANY_PREDICATE.test(new CompanyBuilder().build()));
    }

    @Test
    public void test_categoryDoesNotMatchKeyword_returnsFalse() {
        assertFalse(STUDENT_PREDICATE.test(new CompanyBuilder().build()));
    }
}
