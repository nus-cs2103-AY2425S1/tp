package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static tutorease.address.testutil.TypicalLessons.MATH_LESSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LessonContainsNamesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LessonContainsNamesPredicate firstPredicate = new LessonContainsNamesPredicate(firstPredicateKeywordList);
        LessonContainsNamesPredicate secondPredicate = new LessonContainsNamesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LessonContainsNamesPredicate firstPredicateCopy = new LessonContainsNamesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lessonContainsKeywords_returnsTrue() {
        // One keyword
        LessonContainsNamesPredicate predicate =
                new LessonContainsNamesPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(MATH_LESSON));

        // Multiple keywords
        predicate = new LessonContainsNamesPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(MATH_LESSON));
        assertTrue(predicate.test(ENGLISH_LESSON));

        // Only one matching keyword
        predicate = new LessonContainsNamesPredicate(Arrays.asList("Alice", "Carol"));
        assertTrue(predicate.test(MATH_LESSON));
        assertFalse(predicate.test(ENGLISH_LESSON));

        // Mixed-case keywords
        predicate = new LessonContainsNamesPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(MATH_LESSON));
        assertTrue(predicate.test(ENGLISH_LESSON));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LessonContainsNamesPredicate predicate = new LessonContainsNamesPredicate(Collections.emptyList());
        assertFalse(predicate.test(MATH_LESSON));

        // Non-matching keyword
        predicate = new LessonContainsNamesPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(MATH_LESSON));
        assertFalse(predicate.test(ENGLISH_LESSON));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        LessonContainsNamesPredicate predicate = new LessonContainsNamesPredicate(keywords);

        String expected =
                LessonContainsNamesPredicate.class.getCanonicalName() + "{keywords in lessons=" + keywords
                        + "}";
        assertEquals(expected, predicate.toString());
    }
}
