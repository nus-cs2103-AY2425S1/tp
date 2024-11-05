package tuteez.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class LessonContainsKeywordsPredicateTest {

    private static final Person personWithLesson =
            new PersonBuilder().withName("bob").withLessons("monday 1730-1830").build();
    @Test
    public void test_lessonMatchesDayKeyword_returnsTrue() {
        List<String> keywords = List.of("monday");
        LessonContainsKeywordsPredicate predicate = new LessonContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithLesson));
    }

    @Test
    public void test_lessonMatchesTimeRangeKeyword_returnsTrue() {
        List<String> keywordsStartBefore = List.of("1700-1800");
        LessonContainsKeywordsPredicate predicateStartBefore = new LessonContainsKeywordsPredicate(keywordsStartBefore);
        assertTrue(predicateStartBefore.test(personWithLesson));

        List<String> keywordsSame = List.of("1730-1830");
        LessonContainsKeywordsPredicate predicateSame = new LessonContainsKeywordsPredicate(keywordsSame);
        assertTrue(predicateSame.test(personWithLesson));

        List<String> keywordsEndAfter = List.of("1730-1900");
        LessonContainsKeywordsPredicate predicateEndAfter = new LessonContainsKeywordsPredicate(keywordsEndAfter);
        assertTrue(predicateEndAfter.test(personWithLesson));

        List<String> keywordsStartBeforeAndEndAfter = List.of("1700-1900");
        LessonContainsKeywordsPredicate predicateStartBeforeAndEndAfter =
                new LessonContainsKeywordsPredicate(keywordsStartBeforeAndEndAfter);
        assertTrue(predicateStartBeforeAndEndAfter.test(personWithLesson));
    }

    @Test
    public void test_lessonDoesNotMatchAnyKeyword_returnsFalse() {
        List<String> keywords = List.of("tuesday", "0800-1000");
        LessonContainsKeywordsPredicate predicate = new LessonContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(personWithLesson));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords1 = List.of("MONDAY", "0900-1000");
        List<String> keywords2 = List.of("MONDAY", "0900-1000");

        LessonContainsKeywordsPredicate predicate1 = new LessonContainsKeywordsPredicate(keywords1);
        LessonContainsKeywordsPredicate predicate2 = new LessonContainsKeywordsPredicate(keywords2);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        List<String> keywords1 = List.of("MONDAY", "0900-1000");
        List<String> keywords2 = List.of("TUESDAY", "1100-1200");

        LessonContainsKeywordsPredicate predicate1 = new LessonContainsKeywordsPredicate(keywords1);
        LessonContainsKeywordsPredicate predicate2 = new LessonContainsKeywordsPredicate(keywords2);

        assertFalse(predicate1.equals(predicate2));
    }
}
