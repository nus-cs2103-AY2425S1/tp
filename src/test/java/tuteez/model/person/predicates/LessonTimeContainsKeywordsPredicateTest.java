package tuteez.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class LessonTimeContainsKeywordsPredicateTest {

    private static final Person personWithLesson =
            new PersonBuilder().withName("bob").withLessons("monday 1730-1830").build();

    @Test
    public void test_lessonMatchesTimeRangeKeyword_returnsTrue() {
        List<String> keywordsStartBefore = List.of("1700-1800");
        LessonTimeContainsKeywordsPredicate predicateStartBefore =
                new LessonTimeContainsKeywordsPredicate(keywordsStartBefore);
        assertTrue(predicateStartBefore.test(personWithLesson));

        List<String> keywordsSame = List.of("1730-1830");
        LessonTimeContainsKeywordsPredicate predicateSame = new LessonTimeContainsKeywordsPredicate(keywordsSame);
        assertTrue(predicateSame.test(personWithLesson));

        List<String> keywordsEndAfter = List.of("1730-1900");
        LessonTimeContainsKeywordsPredicate predicateEndAfter =
                new LessonTimeContainsKeywordsPredicate(keywordsEndAfter);
        assertTrue(predicateEndAfter.test(personWithLesson));

        List<String> keywordsStartBeforeAndEndAfter = List.of("1700-1900");
        LessonTimeContainsKeywordsPredicate predicateStartBeforeAndEndAfter =
                new LessonTimeContainsKeywordsPredicate(keywordsStartBeforeAndEndAfter);
        assertTrue(predicateStartBeforeAndEndAfter.test(personWithLesson));
    }

    @Test
    public void test_lessonDoesNotMatchAnyKeyword_returnsFalse() {
        List<String> keywords = List.of("0800-1000");
        LessonTimeContainsKeywordsPredicate predicate = new LessonTimeContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(personWithLesson));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords1 = List.of("0900-1000");
        List<String> keywords2 = List.of("0900-1000");

        LessonTimeContainsKeywordsPredicate predicate1 = new LessonTimeContainsKeywordsPredicate(keywords1);
        LessonTimeContainsKeywordsPredicate predicate2 = new LessonTimeContainsKeywordsPredicate(keywords2);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        List<String> keywords1 = List.of("0900-1000");
        List<String> keywords2 = List.of("1100-1200");

        LessonTimeContainsKeywordsPredicate predicate1 = new LessonTimeContainsKeywordsPredicate(keywords1);
        LessonTimeContainsKeywordsPredicate predicate2 = new LessonTimeContainsKeywordsPredicate(keywords2);

        assertFalse(predicate1.equals(predicate2));
    }
}
