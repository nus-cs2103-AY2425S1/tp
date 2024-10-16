package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CourseContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer Science");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer Science", "Data Science");

        CourseContainsKeywordsPredicate firstPredicate =
                new CourseContainsKeywordsPredicate(firstPredicateKeywordList);
        CourseContainsKeywordsPredicate secondPredicate =
                new CourseContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CourseContainsKeywordsPredicate firstPredicateCopy =
                new CourseContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_courseContainsKeywords_returnsTrue() {
        CourseContainsKeywordsPredicate predicate =
                new CourseContainsKeywordsPredicate(Collections.singletonList("Computer Science"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));

        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Computer", "Science"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));

        predicate = new CourseContainsKeywordsPredicate(Collections.singletonList("Com"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));

        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("cOmPuTer", "sCiEnCe"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));
    }

    @Test
    public void test_courseDoesNotContainKeywords_returnsFalse() {
        CourseContainsKeywordsPredicate predicate =
                new CourseContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));

        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Data"));
        assertFalse(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));

        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withCourse("Computer Science").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        CourseContainsKeywordsPredicate predicate = new CourseContainsKeywordsPredicate(keywords);

        String expected = CourseContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
