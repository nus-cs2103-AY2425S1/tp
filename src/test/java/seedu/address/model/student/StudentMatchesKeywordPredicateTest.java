package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class StudentMatchesKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "friends";
        String secondPredicateKeyword = "group 4";

        StudentMatchesQueryPredicate firstPredicate = new StudentMatchesQueryPredicate(firstPredicateKeyword);
        StudentMatchesQueryPredicate secondPredicate = new StudentMatchesQueryPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentMatchesQueryPredicate firstPredicateCopy = new StudentMatchesQueryPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_studentMatchesKeyword_returnsTrue() {
        // keyword longer than student field
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate("friends");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friend").build()));

        // keyword shorter than student field
        predicate = new StudentMatchesQueryPredicate("ice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friend").build()));
    }

    @Test
    public void test_studentDoesNotMatchKeyword_returnsFalse() {
        // no keyword
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate("      ");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // non-matching keyword
        predicate = new StudentMatchesQueryPredicate("alICe");
        assertFalse(predicate.test(new PersonBuilder().withName("John").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "friends";
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate(keyword);
        String expected = StudentMatchesQueryPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
