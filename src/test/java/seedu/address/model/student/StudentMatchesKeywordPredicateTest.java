package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

//@@author gho7sie

public class StudentMatchesKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyword = List.of("friends");
        List<String> secondPredicateKeyword = List.of("group 4");

        StudentMatchesQueryPredicate firstPredicate = new StudentMatchesQueryPredicate(firstPredicateKeyword);
        StudentMatchesQueryPredicate secondPredicate = new StudentMatchesQueryPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        StudentMatchesQueryPredicate firstPredicateCopy = new StudentMatchesQueryPredicate(firstPredicateKeyword);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different student -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_studentMatchesKeyword_returnsTrue() {
        // keyword longer than student field
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate(List.of("friend"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friend").build()));

        // keyword shorter than student field
        predicate = new StudentMatchesQueryPredicate(List.of("friend"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friend").build()));
    }

    @Test
    public void test_studentDoesNotMatchKeyword_returnsFalse() {
        // non-matching keyword
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate(List.of("alICe"));
        assertFalse(predicate.test(new PersonBuilder().withName("John").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keyword = List.of("friends");
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate(keyword);
        String expected = StudentMatchesQueryPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
